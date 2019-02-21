import request from '@/utils/request'

export function login(username, password) {
  return request({
    url: '/login/login',
    method: 'post',
    datatype: 'jsonp',
    data: {
      username,
      password
    }
  })
}

export function getInfo(token) {
  return request({
    url: '/user/info',
    method: 'get',
    params: { token }
  })
}

export function logout() {
  return request({
    url: '/login/logout',
    method: 'post'
  })
}
