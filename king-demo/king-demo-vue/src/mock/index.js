import Mock from 'mockjs'
import loginAPI from './login'
import userAPI from './admin/user'
import roleAPI from './admin/role'
import remoteSearchAPI from './remoteSearch'
import transactionAPI from './transaction'

Mock.setup({
  timeout: '350-600'
})

// 登录相关
Mock.mock(/\/login\/login/, 'post', loginAPI.loginByUsername)
Mock.mock(/\/login\/logout/, 'post', loginAPI.logout)
Mock.mock(/\/login\/info\.*/, 'get', loginAPI.loginInfo)

Mock.mock(/\/user\/list/, 'get', userAPI.getList)
Mock.mock(/\/user\/get/, 'get', userAPI.get)
Mock.mock(/\/user\/add/, 'get', userAPI.add)
Mock.mock(/\/user\/update/, 'post', userAPI.update)
Mock.mock(/\/user\/del/, 'get', userAPI.del)

Mock.mock(/\/role\/list/, 'get', roleAPI.getList)
Mock.mock(/\/role\/get/, 'get', roleAPI.get)
Mock.mock(/\/role\/add/, 'get', roleAPI.add)
Mock.mock(/\/role\/update/, 'post', roleAPI.update)
Mock.mock(/\/role\/del/, 'get', roleAPI.del)

// 搜索相关
Mock.mock(/\/search\/user/, 'get', remoteSearchAPI.searchUser)

// 账单相关
Mock.mock(/\/transaction\/list/, 'get', transactionAPI.getList)

export default Mock
