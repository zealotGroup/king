import request from '@/utils/request'

const url = '/login'
export function login(username, password) {
  return request({
    url: url + '/login/login',
    method: 'post',
    data: {
      username,
      password
    }
  })
}

export function logout() {
  return request({
    url: url + '/logout',
    method: 'post'
  })
}

export function loginInfo(token) {
  return request({
    url: url + '/info',
    method: 'get'
  })
}

