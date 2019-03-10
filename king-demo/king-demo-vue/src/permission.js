import router from './router'
import store from './store'
import { Message } from 'element-ui'
import NProgress from 'nprogress' // progress bar
import 'nprogress/nprogress.css'// progress bar style
import { getToken, frushToken } from '@/utils/auth' // getToken from cookie

NProgress.configure({ showSpinner: false })// NProgress Configuration

const whiteList = ['/login', '/authredirect']// no redirect whitelist

router.beforeEach((to, from, next) => {
  NProgress.start() // start progress bar
  if (getToken()) { // determine if there has token
    frushToken() // 刷新token
    /* has token*/
    if (to.path === '/login') {
      next({ path: '/' })
      NProgress.done() // if current page is dashboard will not trigger	afterEach hook, so manually handle it
    } else {
      if (store.getters.routers.length !== 0) { // 判断当前用户有无路由信息
        next()//
      } else {
        store.dispatch('GetUserInfo').then(() => { // 拉取user_info（包括路由信息）到缓存
          router.addRoutes(store.getters.routers) // 添加路由信息
          router.addRoutes([{ path: '*', redirect: '/404', hidden: true }])
          next({ ...to, replace: true }) // hack方法 确保addRoutes已完成 ,set the replace: true so the navigation will not leave a history record
        }).catch((err) => {
          store.dispatch('FedLogOut').then(() => {
            Message.error(err || this.$t('error'))
            next({ path: '/' })
          })
        })
      }
    }
  } else {
    /* has no token*/
    if (whiteList.indexOf(to.path) !== -1) { // 在免登录白名单，直接进入
      next()
    } else {
      next('/login') // 否则全部重定向到登录页
      NProgress.done() // if current page is login will not trigger afterEach hook, so manually handle it
    }
  }
})

router.afterEach(() => {
  NProgress.done() // finish progress bar
})
