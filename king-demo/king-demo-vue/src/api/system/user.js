import request from '@/utils/request'

const url = '/system/user'
export function getList(query) {
  return request({
    url: url + '/list',
    method: 'get',
    params: query
  })
}

export function get(id) {
  return request({
    url: url + '/get',
    method: 'get',
    params: {
      id
    }
  })
}

export function add(data) {
  return request({
    url: url + '/add',
    method: 'post',
    data
  })
}

export function update(data) {
  return request({
    url: url + '/update',
    method: 'post',
    data
  })
}

export function del(id) {
  return request({
    url: url + '/del',
    method: 'post',
    params: {
      id
    }
  })
}
