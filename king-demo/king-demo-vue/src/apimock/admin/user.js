import Mock from 'mockjs'
import { param2Obj } from '@/utils'

const List = []
const count = 100

for (let i = 0; i < count; i++) {
  List.push(Mock.mock({
    No: '@increment',
    id: '@string("number", 5)',
    username: '@cname',
    password: '@word',
    routeRole: 'admin',
    dataRole: 'super',
    status: 'able',
    loginTimes: '@integer(0, 1000)',
    level: 'svip',
    createTime: '@date("yyyy-MM-dd")',
    createUser: '@cname',
    lastUpdateTime: '@date("yyyy-MM-dd")',
    lastUpdateUser: '@cname',
    lastLoginTime: '@date("yyyy-MM-dd")',
    remark: '@ctitle'
  }))
  List.push(Mock.mock({
    No: '@increment',
    id: '@string("number", 5)',
    username: '@cname',
    password: '@word',
    routeRole: 'admin',
    dataRole: 'super',
    status: 'disable',
    loginTimes: '@integer(0, 1000)',
    level: 'svip',
    createTime: '@date("yyyy-MM-dd")',
    createUser: '@cname',
    lastUpdateTime: '@date("yyyy-MM-dd")',
    lastUpdateUser: '@cname',
    lastLoginTime: '@date("yyyy-MM-dd")',
    remark: '@ctitle'
  }))
}

export default {
  getList: config => {
    const { username, status, level, page, limit } = param2Obj(config.url)

    console.debug(status)
    console.debug(level)
    const mockList = List.filter(item => {
      if (username && item.username.indexOf(username) === -1) return false
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
