import Mock from 'mockjs'
import { param2Obj } from '@/utils'

const List = []
const count = 100

for (let i = 0; i < count; i++) {
  List.push(Mock.mock({
    No: '@increment',
    id: '1-100',
    username: 'abc',
    password: 'def',
    routeRole: 'admin',
    dataRole: 'super',
    status: 'able',
    loginTimes: '132',
    level: 'svip',
    createTime: new Date(),
    createUser: '12',
    lastUpdateTime: new Date(),
    lastUpdateUser: '123',
    lastLoginTime: new Date(),
    remark: '1'
  }))
  List.push(Mock.mock({
    No: '@increment',
    id: '1-100',
    username: 'abc',
    password: 'def',
    routeRole: 'admin',
    dataRole: 'super',
    status: 'disable',
    loginTimes: '132',
    level: 'super',
    createTime: new Date(),
    createUser: '12',
    lastUpdateTime: new Date(),
    lastUpdateUser: '123',
    lastLoginTime: new Date(),
    remark: '1'
  }))
  List.push(Mock.mock({
    No: '@increment',
    id: '1-100',
    username: 'abc',
    password: 'def',
    routeRole: 'admin',
    dataRole: 'super',
    status: 'deleted',
    loginTimes: '132',
    level: 'admin',
    createTime: new Date(),
    createUser: '12',
    lastUpdateTime: new Date(),
    lastUpdateUser: '123',
    lastLoginTime: new Date(),
    remark: '1'
  }))
  List.push(Mock.mock({
    No: '@increment',
    id: '1-100',
    username: 'abc',
    password: 'def',
    routeRole: 'admin',
    dataRole: 'super',
    status: '',
    loginTimes: '132',
    level: 'admin',
    createTime: new Date(),
    createUser: '12',
    lastUpdateTime: new Date(),
    lastUpdateUser: '123',
    lastLoginTime: new Date(),
    remark: '1'
  }))
}

export default {
  getList: config => {
    const { importance, type, title, page = 1, limit = 20, sort } = param2Obj(config.url)

    let mockList = List.filter(item => {
      if (importance && item.importance !== +importance) return false
      if (type && item.type !== type) return false
      if (title && item.title.indexOf(title) < 0) return false
      return true
    })

    if (sort === '-id') {
      mockList = mockList.reverse()
    }

    const pageList = mockList.filter((item, index) => index < limit * page && index >= limit * (page - 1))

    return {
      code: 200,
      data: {
        total: mockList.length,
        items: pageList
      }
    }
  },
  getPv: () => ({
    pvData: [{ key: 'PC', pv: 1024 }, { key: 'mobile', pv: 1024 }, { key: 'ios', pv: 1024 }, { key: 'android', pv: 1024 }]
  }),
  getArticle: (config) => {
    const { id } = param2Obj(config.url)
    for (const article of List) {
      if (article.id === +id) {
        return article
      }
    }
  },
  createArticle: () => ({
    data: 'success'
  }),
  updateArticle: () => ({
    data: 'success'
  })
}
