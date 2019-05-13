import { param2Obj } from '@/utils'

const routers = [
  {
    name: 'index',
    children: [
      { name: 'dashboard' }
    ]
  },
  {
    name: 'supermarket',
    children: [
      { name: 'supplier' },
      { name: 'customer' },
      { name: 'product' },
      { name: 'reportForm' }
    ]
  },
  {
    name: 'Admin',
    children: [
      { name: 'role' },
      { name: 'user' }
    ]
  },
  {
    name: 'System',
    children: [
      {
        name: 'icons'
      },
      {
        name: 'errorPages',
        children: [
          { name: 'page401' },
          { name: 'page404' }
        ]
      },
      {
        name: 'theme'
      },
      {
        name: 'i18n'
      }
    ]
  },
  {
    name: 'FunShow',
    children: [
      {
        name: 'component-demo',
        children: [
          { name: 'dragDialog-demo' },
          { name: 'dndList-demo' },
          { name: 'dragKanban-demo' }
        ]
      },
      {
        name: 'charts',
        children: [
          { name: 'keyboardChart' },
          { name: 'lineChart' },
          { name: 'mixChart' }
        ]
      },
      {
        name: 'tab'
      },

      {
        name: 'table',
        children: [
          { name: 'inlineEditTable' },
          { name: 'complexTable' }
        ]
      }
    ]
  }
]

export default {
  getList: config => {
    const { page, limit } = param2Obj(config.url)

    const mockList = routers

    const pageList = mockList.filter((item, index) => page == -1 || (index < limit * page && index >= limit * (page - 1)))

    return {
      code: 200,
      data: {
        total: mockList.length,
        list: pageList
      }
    }
  },
  get: (config) => {
    const { id } = param2Obj(config.url)
    for (const vo of routers) {
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
  }),
  recover: () => ({
    code: 200,
    data: 'success'
  }),
  realDel: () => ({
    code: 200,
    data: 'success'
  })
}
