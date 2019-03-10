const routers = [
  {
    name: 'index',
    children: [
      {
        name: 'dashboard'
      }
    ]
  },
  {
    name: 'supermarket',
    children: [
      {
        name: 'supplier'
      }
    ]
  }
]

const loginResult = {
  code: 200,
  data: {
    sessionId: '123456789',
    timeout: '1800',
    unit: 'SECONDS'
  }
}

const userInfoResult = {
  code: 200,
  data: {
    // avatar: '/static/9fa5ee6873171273e12d79d0dda20d5e05f9bb60.gif',
    level: 'super',
    routers: routers
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
