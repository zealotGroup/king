import { getToken } from '@/utils/auth'
import qs from 'qs'

const $qs = qs
const loginMap = {
  admin: {
    code: 200,
    data: {
      sessionId: '123456789',
      timeout: '1800',
      unit: 'SECONDS'
    }
  },
  editor: {
    code: 200,
    data: {
      sessionId: '987654321',
      timeout: '1800',
      unit: 'SECONDS'
    }
  }
}

const userInfoMap = {
  admin: {
    code: 200,
    data: {
      principals: {
        roles: ['admin']
      },
      avatar: '/static/9fa5ee6873171273e12d79d0dda20d5e05f9bb60.gif',
      level: 'super'
    }
  },
  editor: {
    code: 200,
    data: {
      principals: {
        roles: ['editor']
      },
      avatar: '/static/9fa5ee6873171273e12d79d0dda20d5e05f9bb60.gif',
      level: 'admin'
    }
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
    const obj = $qs.parse(config.body)
    const { username } = obj // username 就是所谓的 登录名
    return loginMap[username]// 输入的是admin 就选择admin对象
  },
  getUserInfo: config => {
    const token = getToken()

    if (token && token.sessionId === loginMap.admin.data.sessionId) {
      return userInfoMap.admin
    } else if (token && token.sessionId === loginMap.editor.data.sessionId) {
      return userInfoMap.editor
    } else {
      return false
    }
  },
  logout: () => {
    return logoutMap.success
  }
}
