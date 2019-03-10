import { login, logout, loginInfo } from '@/api/login'
import { getToken, setToken, removeToken } from '@/utils/auth'
import { routerMap } from '@/router'

function dealRouters(routers) {
  const menus = []
  for (const v of routers) {
    for (const vo of routerMap) {
      if (v === vo.name) {
        console.info(vo.name)
        menus.push(vo)
      }
    }
  }
  return menus
}

const user = {
  state: {
    token: getToken(),
    routers: [],
    level: ''
  },

  mutations: {
    SET_TOKEN: (state, token) => {
      state.token = token
    },
    SET_LEVEL: (state, level) => {
      state.level = level
    },
    SET_ROUTERS: (state, routers) => {
      state.routers = routers
    }
  },

  actions: {
    // 用户名登录
    LoginByUsername({ commit }, userInfo) {
      const username = userInfo.username.trim()
      return new Promise((resolve, reject) => {
        login(username, userInfo.password).then(data => {
          commit('SET_TOKEN', data) // 缓存本地，非cookies
          setToken(data) // 储存到cookies
          console.debug('储存 token 到 cookies完成')
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 获取用户信息
    GetUserInfo({ commit, state }) {
      return new Promise((resolve, reject) => {
        loginInfo().then(data => {
          commit('SET_LEVEL', data.level)
          commit('SET_ROUTERS', dealRouters(data.routers))
          console.debug('set info完成')
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 登出
    LogOut({ commit, state }) {
      return new Promise((resolve, reject) => {
        logout(state.token).then(() => {
          commit('SET_TOKEN', '')
          commit('SET_ROUTERS', [])
          commit('SET_LEVEL', '')
          removeToken()
          console.debug('移除 token完成')
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 前端 登出
    FedLogOut({ commit }) {
      return new Promise(resolve => {
        commit('SET_TOKEN', '')
        commit('SET_ROUTERS', [])
        commit('SET_LEVEL', '')
        removeToken()
        console.debug('移除 token完成')
        resolve()
      })
    },

    // 动态修改权限
    ChangeRoles({ commit }, role) {
      return new Promise(resolve => {
        commit('SET_TOKEN', role)
        setToken(role)
        loginInfo(role).then(response => {
          const data = response.data
          commit('SET_ROLES', data.roles)
          commit('SET_NAME', data.name)
          resolve()
        })
      })
    }
  }
}

export default user
