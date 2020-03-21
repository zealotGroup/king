import request from '@/utils/request'

const url = '/oauth'
export function login(username, password) {
  return request({
    url: url + '/login',
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

export function loginInfo() {
  return request({
    url: url + '/loginUserInfo',
    method: 'get'
  })
}

