import { param2Obj } from '@/utils'

const routers = [
  {
    id: 1, disabled: true, name: 'index',
    children: [
      { id: 11, disabled: true, name: 'dashboard' }
    ]
  },
  {
    id: 2, name: 'supermarket',
    children: [
      { id: 21, name: 'supplier' },
      { id: 22, name: 'customer' },
      { id: 23, name: 'product' },
      { id: 24, name: 'reportForm' }
    ]
  },
  {
    id: 3, name: 'Admin',
    children: [
      { id: 31, name: 'role' },
      { id: 32, name: 'user' }
    ]
  },
  {
    id: 4, name: 'System',
    children: [
      {
        id: 41, name: 'icons'
      },
      {
        id: 42, name: 'errorPages',
        children: [
          { id: 421, name: 'page401' },
          { id: 422, name: 'page404' }
        ]
      },
      {
        id: 43, name: 'theme'
      },
      {
        id: 44, name: 'i18n'
      }
    ]
  },
  {
    id: 5, name: 'FunShow',
    children: [
      {
        id: 51, name: 'component-demo',
        children: [
          { id: 511, name: 'dragDialog-demo' },
          { id: 512, name: 'dndList-demo' },
          { id: 513, name: 'dragKanban-demo' }
        ]
      },
      {
        id: 52, name: 'charts',
        children: [
          { id: 521, name: 'keyboardChart' },
          { id: 522, name: 'lineChart' },
          { id: 523, name: 'mixChart' }
        ]
      },
      {
        id: 53, name: 'tab'
      },

      {
        id: 54, name: 'table',
        children: [
          { id: 541, name: 'inlineEditTable' },
          { id: 542, name: 'complexTable' }
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
