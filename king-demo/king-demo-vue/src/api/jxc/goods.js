import request from '@/utils/request'
import { copy } from '@/utils/myUtil'

const url = '/jxc/goods'

export function getList(query) {
  const params = copy(query)
  params.lableIds = undefined
  const l = []
  if (query.lableIds) {
    for (const index in query.lableIds) {
      l.push(query.lableIds[index])
    }
    if (l.length > 0) {
      params.lableId = JSON.stringify(l)
      params.lableId = params.lableId.substr(1, params.lableId.length - 2)
    }
  }
  return request({
    url: url + '/list',
    method: 'get',
    params: params
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

export function addLable(data) {
  return request({
    url: url + '/addLable',
    method: 'post',
    data
  })
}
export function delLable(data) {
  return request({
    url: url + '/delLable',
    method: 'post',
    data
  })
}
export function getGoodsLableList() {
  return request({
    url: url + '/getGoodsLableList',
    method: 'get'
  })
}
export function delPicture(data) {
  console.log(data)
  return request({
    url: url + '/delPicture',
    method: 'post',
    data
  })
}
