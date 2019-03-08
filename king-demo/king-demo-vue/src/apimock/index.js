import Mock from 'mockjs'
import loginAPI from './login'
import articleAPI from './article'
import userAPI from './admin/user'
import dataRoleAPI from './admin/role/dataRole'
import routeRoleAPI from './admin/role/routeRole'
import remoteSearchAPI from './remoteSearch'
import transactionAPI from './transaction'
import supplierAPI from './supermarket/supplier'

Mock.setup({
  timeout: '350-600'
})
Mock.mock(/\/article\/list/, 'get', articleAPI.getList)
Mock.mock(/\/article\/detail/, 'get', articleAPI.getArticle)
Mock.mock(/\/article\/pv/, 'get', articleAPI.getPv)
Mock.mock(/\/article\/create/, 'post', articleAPI.createArticle)
Mock.mock(/\/article\/update/, 'post', articleAPI.updateArticle)

// 登录相关
Mock.mock(/\/login\/login/, 'post', loginAPI.loginByUsername)
Mock.mock(/\/login\/logout/, 'post', loginAPI.logout)
Mock.mock(/\/login\/info\.*/, 'get', loginAPI.loginInfo)

Mock.mock(/\/user\/list/, 'get', userAPI.getList)
Mock.mock(/\/user\/get/, 'get', userAPI.get)
Mock.mock(/\/user\/add/, 'post', userAPI.add)
Mock.mock(/\/user\/update/, 'post', userAPI.update)
Mock.mock(/\/user\/del/, 'post', userAPI.del)
Mock.mock(/\/user\/recover/, 'post', userAPI.recover)
Mock.mock(/\/user\/realDel/, 'post', userAPI.realDel)

Mock.mock(/\/role\/dataRole\/list/, 'get', dataRoleAPI.getList)
Mock.mock(/\/role\/dataRole\/get/, 'get', dataRoleAPI.get)
Mock.mock(/\/role\/dataRole\/add/, 'post', dataRoleAPI.add)
Mock.mock(/\/role\/dataRole\/update/, 'post', dataRoleAPI.update)
Mock.mock(/\/role\/dataRole\/del/, 'post', dataRoleAPI.del)
Mock.mock(/\/role\/dataRole\/recover/, 'post', dataRoleAPI.recover)
Mock.mock(/\/role\/dataRole\/realDel/, 'post', dataRoleAPI.realDel)

Mock.mock(/\/role\/routeRole\/list/, 'get', routeRoleAPI.getList)
Mock.mock(/\/role\/routeRole\/get/, 'get', routeRoleAPI.get)
Mock.mock(/\/role\/routeRole\/add/, 'post', routeRoleAPI.add)
Mock.mock(/\/role\/routeRole\/update/, 'post', routeRoleAPI.update)
Mock.mock(/\/role\/routeRole\/del/, 'post', routeRoleAPI.del)
Mock.mock(/\/role\/routeRole\/recover/, 'post', routeRoleAPI.recover)
Mock.mock(/\/role\/routeRole\/realDel/, 'post', routeRoleAPI.realDel)

Mock.mock(/\/supermarket\/supplier\/list/, 'get', supplierAPI.getList)
Mock.mock(/\/supermarket\/supplier\/get/, 'get', supplierAPI.get)
Mock.mock(/\/supermarket\/supplier\/add/, 'post', supplierAPI.add)
Mock.mock(/\/supermarket\/supplier\/update/, 'post', supplierAPI.update)
Mock.mock(/\/supermarket\/supplier\/del/, 'post', supplierAPI.del)
Mock.mock(/\/supermarket\/supplier\/recover/, 'post', supplierAPI.recover)
Mock.mock(/\/supermarket\/supplier\/realDel/, 'post', supplierAPI.realDel)

// 搜索相关
Mock.mock(/\/search\/user/, 'get', remoteSearchAPI.searchUser)

// 账单相关
Mock.mock(/\/transaction\/list/, 'get', transactionAPI.getList)

export default Mock
