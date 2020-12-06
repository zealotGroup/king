import request from '@/utils/request'

const url = '/file'

export function picture(id) {
  return request({
    url: url + '/picture',
    method: 'get',
    params: {
      id
    }
  })
}
