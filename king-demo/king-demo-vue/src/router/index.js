import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/views/layout/Layout'

/** note: submenu only apppear when children.length>=1
*   detail see  https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
**/

/**
* hidden: true                   if `hidden:true` will not show in the sidebar(default is false)
* alwaysShow: true               if set true, will always show the root menu, whatever its child routes length
*                                if not set alwaysShow, only more than one route under the children
*                                it will becomes nested mode, otherwise not show the root menu
* redirect: noredirect           if `redirect:noredirect` will no redirct in the breadcrumb
* name:'router-name'             the name is used by <keep-alive> (must set!!!)
* meta : {
    roles: ['admin','editor']     will control the page roles (you can set multiple roles)
    title: 'title'               the name show in submenu and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar,
    noCache: true                if true ,the page will no be cached(default is false)
  }
**/
export const constantRouterMap = [
  { path: '/login', component: () => import('@/views/login/index'), hidden: true },
  { path: '/authredirect', component: () => import('@/views/login/authredirect'), hidden: true },
  { path: '/404', component: () => import('@/views/errorPage/404'), hidden: true },
  { path: '/401', component: () => import('@/views/errorPage/401'), hidden: true },
  {
    path: '',
    component: Layout,
    redirect: 'dashboard',
    children: [{
      path: 'dashboard',
      component: () => import('@/views/dashboard/index'),
      name: 'dashboard',
      meta: { title: 'dashboard', icon: 'dashboard', noCache: true }
    }]
  }
]

export default new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRouterMap
})

export const superRouterMap = [
  {
    path: '/funShow',
    component: Layout,
    redirect: 'noredirect',
    name: 'FunShow',
    meta: {
      title: 'FunShow',
      icon: 'example'
    },
    children: [
      {
        path: '/components',
        component: () => import('@/views/Blank'),
        redirect: 'noredirect',
        name: 'component-demo',
        meta: {
          title: 'components',
          icon: 'component'
        },
        children: [
          { path: 'drag-dialog', component: () => import('@/views/components-demo/dragDialog'), name: 'dragDialog-demo', meta: { title: 'dragDialog' }},
          { path: 'dnd-list', component: () => import('@/views/components-demo/dndList'), name: 'dndList-demo', meta: { title: 'dndList' }},
          { path: 'drag-kanban', component: () => import('@/views/components-demo/dragKanban'), name: 'dragKanban-demo', meta: { title: 'dragKanban' }}
        ]
      },

      {
        path: '/charts',
        component: () => import('@/views/Blank'),
        redirect: 'noredirect',
        name: 'charts',
        meta: {
          title: 'charts',
          icon: 'chart'
        },
        children: [
          { path: 'keyboard', component: () => import('@/views/charts/keyboard'), name: 'keyboardChart', meta: { title: 'keyboardChart', noCache: true }},
          { path: 'line', component: () => import('@/views/charts/line'), name: 'lineChart', meta: { title: 'lineChart', noCache: true }},
          { path: 'mixchart', component: () => import('@/views/charts/mixChart'), name: 'mixChart', meta: { title: 'mixChart', noCache: true }}
        ]
      },

      {
        path: '/tab',
        component: () => import('@/views/Blank'),
        children: [{
          path: 'index',
          component: () => import('@/views/tab/index'),
          name: 'tab',
          meta: { title: 'tab', icon: 'tab' }
        }]
      },

      {
        path: '/table',
        component: () => import('@/views/Blank'),
        redirect: '/table/complex-table',
        name: 'table',
        meta: {
          title: 'Table',
          icon: 'table'
        },
        children: [
          { path: 'inline-edit-table', component: () => import('@/views/table/inlineEditTable'), name: 'inlineEditTable', meta: { title: 'inlineEditTable' }},
          { path: 'complex-table', component: () => import('@/views/table/complexTable'), name: 'complexTable', meta: { title: 'complexTable' }}
        ]
      }
    ]
  },
  {
    path: '/system',
    component: Layout,
    redirect: 'noredirect',
    name: 'System',
    meta: {
      title: 'System',
      icon: 'password'
    },
    children: [
      {
        path: '/system/icon',
        component: () => import('@/views/Blank'),
        children: [{
          path: 'index',
          component: () => import('@/views/svg-icons/index'),
          name: 'icons',
          meta: { title: 'icons', icon: 'icon', noCache: true }
        }]
      },
      {
        path: '/system/error',
        component: () => import('@/views/Blank'),
        redirect: 'noredirect',
        name: 'errorPages',
        meta: {
          title: 'errorPages',
          icon: '404'
        },
        children: [
          { path: '401', component: () => import('@/views/errorPage/401'), name: 'page401', meta: { title: 'page401', noCache: true }},
          { path: '404', component: () => import('@/views/errorPage/404'), name: 'page404', meta: { title: 'page404', noCache: true }}
        ]
      },
      {
        path: '/system/theme',
        component: () => import('@/views/Blank'),
        redirect: 'noredirect',
        children: [{ path: 'index', component: () => import('@/views/theme/index'), name: 'theme', meta: { title: 'theme', icon: 'theme' }}]
      },
      {
        path: '/system/i18n',
        component: () => import('@/views/Blank'),
        children: [{ path: 'index', component: () => import('@/views/i18n-demo/index'), name: 'i18n', meta: { title: 'i18n', icon: 'international' }}]
      }
    ]
  },

  { path: '*', redirect: '/404', hidden: true }
]
