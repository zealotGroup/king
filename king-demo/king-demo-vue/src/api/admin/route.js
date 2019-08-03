import request from '@/utils/request'

const url = '/route'

export function getTree() {
  return request({
    url: url + '/tree',
    method: 'get'
  })
}

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

export function recover(id) {
  return request({
    url: url + '/recover',
    method: 'post',
    params: {
      id
    }
  })
}

export function realDel(id) {
  return request({
    url: url + '/realDelete',
    method: 'post',
    params: {
      id
    }
  })
}
