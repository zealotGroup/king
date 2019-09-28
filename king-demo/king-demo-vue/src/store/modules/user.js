import { login, logout, loginInfo } from '@/api/login'
import { getToken, setToken, removeToken } from '@/utils/auth'
import { routerMap } from '@/router'

function dealRoutes(routes) {
  const menu = dealRouter(routes, routerMap)
  return menu
}

function dealRouter(vchildren, vochildren) {
  const children = []
  for (const v of vchildren) {
    for (const vo of vochildren) {
      if (v.name === vo.name) {
        if (v.children && vo.children && v.children.length > 0 && vo.children.length > 0) {
          vo.children = dealRouter(v.children, vo.children)
        }
        children.push(vo)
        break
      }
    }
  }
  return children
}

const user = {
  state: {
    token: getToken(),
    routes: [],
    level: ''
  },

  mutations: {
    SET_TOKEN: (state, token) => {
      state.token = token
    },
    SET_LEVEL: (state, level) => {
      state.level = level
    },
    SET_ROUTES: (state, routes) => {
      state.routes = routes
    }
  },

  actions: {
    // 用户名登录
    LoginByUsername({ commit }, userInfo) {
      const username = userInfo.username.trim()
      return new Promise((resolve, reject) => {
        login(username, userInfo.password).then(data => {
          commit('SET_TOKEN', data.token) // 缓存本地，非cookies
          setToken(data) // 储存到cookies
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 获取用户信息
    GetUserInfo({ commit }) {
      return new Promise((resolve, reject) => {
        loginInfo().then(data => {
          commit('SET_LEVEL', data.level)
          commit('SET_ROUTES', dealRoutes(data.routes))
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 登出
    LogOut({ commit }) {
      return new Promise((resolve, reject) => {
        logout().then(() => {
          commit('SET_TOKEN', '')
          commit('SET_ROUTES', [])
          commit('SET_LEVEL', '')
          removeToken()
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
        commit('SET_ROUTES', [])
        commit('SET_LEVEL', '')
        removeToken()
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
