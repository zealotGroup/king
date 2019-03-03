import request from '@/utils/request'

export function getList(query) {
  return request({
    url: '/user/list',
    method: 'get',
    params: query
  })
}

export function get(id) {
  return request({
    url: '/user/get',
    method: 'get',
    params: {
      id
    }
  })
}

export function add(data) {
  return request({
    url: '/user/add',
    method: 'post',
    data
  })
}

export function update(data) {
  return request({
    url: '/user/update',
    method: 'post',
    data
  })
}

export function del(id) {
  return request({
    url: '/user/del',
    method: 'get',
    params: {
      id
    }
  })
}

export function realDel(id) {
  return request({
    url: '/user/realDelete',
    method: 'get',
    params: {
      id
    }
  })
}
