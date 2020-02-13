import { login, logout, loginInfo } from '@/api/login'
import { getToken, setToken, removeToken } from '@/utils/auth'
import { routerMap } from '@/router'

function dealRoutes(routes) {
  const auth = []
  const menu = dealRouter(routes, routerMap, auth)
  console.info(menu)
  console.info(auth)
  const route = { menu: menu, auth: auth }
  return route
}

function dealRouter(vchildren, vochildren, auth) {
  const children = []
  for (const v of vchildren) {
    for (const vo of vochildren) {
      if (v.type === 1) {
        auth.push(v.name)
      } else if (v.name === vo.name && v.type === 'MENU') {
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
    auth: [],
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
      state.routes = routes.menu
      state.auth = routes.auth
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
          commit('SET_ROUTES', { menu: [], auth: [] })
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
        commit('SET_ROUTES', { menu: [], auth: [] })
        commit('SET_LEVEL', '')
        removeToken()
        resolve()
      })
    }
  }
}

export default user
