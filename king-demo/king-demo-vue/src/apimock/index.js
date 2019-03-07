import Mock from 'mockjs'
import loginAPI from './login'
import articleAPI from './article'
import userAPI from './admin/user'
import roleAPI from './admin/role'
import remoteSearchAPI from './remoteSearch'
import transactionAPI from './transaction'
import supermarketSupplierAPI from './supermarket/supplier'

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

Mock.mock(/\/role\/list/, 'get', roleAPI.getList)
Mock.mock(/\/role\/get/, 'get', roleAPI.get)
Mock.mock(/\/role\/add/, 'post', roleAPI.add)
Mock.mock(/\/role\/update/, 'post', roleAPI.update)
Mock.mock(/\/role\/del/, 'get', roleAPI.del)

Mock.mock(/\/supermarket\/supplier\/list/, 'get', supermarketSupplierAPI.getList)
Mock.mock(/\/supermarket\/supplier\/get/, 'get', supermarketSupplierAPI.get)
Mock.mock(/\/supermarket\/supplier\/add/, 'post', supermarketSupplierAPI.add)
Mock.mock(/\/supermarket\/supplier\/update/, 'post', supermarketSupplierAPI.update)
Mock.mock(/\/supermarket\/supplier\/del/, 'post', supermarketSupplierAPI.del)
Mock.mock(/\/supermarket\/supplier\/recover/, 'post', supermarketSupplierAPI.recover)
Mock.mock(/\/supermarket\/supplier\/realDel/, 'post', supermarketSupplierAPI.realDel)

// 搜索相关
Mock.mock(/\/search\/user/, 'get', remoteSearchAPI.searchUser)

// 账单相关
Mock.mock(/\/transaction\/list/, 'get', transactionAPI.getList)

export default Mock
