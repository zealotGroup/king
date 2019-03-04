import Mock from 'mockjs'
import { param2Obj } from '@/utils'

const List = []
const count = 100

for (let i = 0; i < count; i++) {
  List.push(Mock.mock({
    No: '@increment',
    id: '@increment',
    name: 'abc',
    createTime: new Date(),
    createUser: '12'
  }))
  List.push(Mock.mock({
    No: '@increment',
    id: '@increment',
    name: 'abc',
    createTime: new Date(),
    createUser: '12'
  }))
}

export default {
  getList: config => {
    const { name, status, level, page, limit } = param2Obj(config.url)

    console.debug(status)
    console.debug(level)
    const mockList = List.filter(item => {
      if (name && item.name.indexOf(name) === -1) return false
      if (status && item.status !== status) return false
      if (level && item.level !== level) return false
      return true
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
