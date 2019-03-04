import Mock from 'mockjs'
import { param2Obj } from '@/utils'

const List = []
const count = 100

for (let i = 0; i < count; i++) {
  List.push(Mock.mock({
    No: '@increment',
    id: '@increment',
    name: '@cname',
    phone: '@email',
    region: '@county',
    remarks: '@ctitle'
  }))
}

export default {

  myIndexOf(like, v) {
    if (like && v && v.indexOf(like) === -1) {
      return false
    } else {
      return true
    }
  },
  getList: config => {
    const { like, page, limit } = param2Obj(config.url)

    console.debug(like)
    const mockList = List.filter(item => {
      if (this.myIndexOf(like, item.name) ||
        this.myIndexOf(like, item.phone) ||
        this.myIndexOf(like, item.region) ||
        this.myIndexOf(like, item.remarks)) {
        return false
      } else {
        return true
      }
    })

    const pageList = mockList.filter((item, index) => index < limit * page && index >= limit * (page - 1))

    return {
      code: 200,
      data: {
        total: mockList.length,
        items: pageList
      }
    }
  },
  get: (config) => {
    const { id } = param2Obj(config.url)
    for (const vo of List) {
      if (vo.id === +id) {
        return {
          code: 200,
          data: {
            vo
          }
        }
      }
    }
  },
  add: () => ({
    code: 200,
    data: 'success'
  }),
  update: () => ({
    code: 200,
    data: 'success'
  }),
  del: () => ({
    code: 200,
    data: 'success'
  })
}
