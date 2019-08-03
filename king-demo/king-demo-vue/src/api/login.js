import request from '@/utils/request'

const url = ''
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

export function logout(token) {
  return request({
    url: url + '/logout',
    method: 'post'
  })
}

export function loginInfo(token) {
  return request({
    url: url + '/loginUserInfo',
    method: 'get'
  })
}

