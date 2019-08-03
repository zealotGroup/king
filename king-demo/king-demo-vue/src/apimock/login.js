const routes = [
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
    name: 'admin',
    children: [
      { name: 'role' },
      { name: 'user' }
    ]
  },
  {
    name: 'system',
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
    name: 'funShow',
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

const loginResult = {
  code: 200,
  data: {
    token: '123456789',
    timeout: '1800',
    unit: 'SECONDS'
  }
}

const userInfoResult = {
  code: 200,
  data: {
    // avatar: '/static/9fa5ee6873171273e12d79d0dda20d5e05f9bb60.gif',
    level: 'super',
    routes: routes
  }
}

const logoutMap = {
  success: {
    code: 200
  },
  error: {
    code: 500,
    msg: '退出登录出错'
  }
}

export default {
  loginByUsername: config => {
    return loginResult
  },
  loginInfo: config => {
    return userInfoResult
  },
  logout: () => {
    return logoutMap.success
  }
}
