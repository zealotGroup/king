import axios from 'axios'
import { Message, MessageBox } from 'element-ui'
import store from '@/store'
import { getToken } from '@/utils/auth'
import qs from 'qs'

const $qs = qs
// 创建axios实例
const service = axios.create({
  baseURL: process.env.BASE_API, // api 的 base_url
  timeout: 30000, // 请求超时时间
  transformRequest: [function(data) {
    return $qs.stringify({ ...data }, { indices: false })
  }],
  datatype: 'jsonp'
})
const auth_session_id = 'auth_session_id'

// request拦截器
service.interceptors.request.use(
  config => {
    if (getToken()) {
      config.headers[auth_session_id] = getToken().auth_session_id // 让每个请求携带自定义token 请根据实际情况自行修改
    }
    // config.headers['Access-Control-Allow-Origin'] = '*' // 跨域
    config.headers['Content-Type'] = 'application/x-www-form-urlencoded' // 跨域
    return config
  },
  error => {
    // Do something with request error
    console.log(error) // for debug
    Promise.reject(error)
  }
)

// response 拦截器
service.interceptors.response.use(
  response => {
    /**
     * code为非200是抛错 可结合自己业务进行修改
     */
    const res = response.data
    console.debug(res)
    if (res.code !== 200) {
      console.error('请求失败 code = ' + res.code + ' url = ' + response.config.url)
      console.error('res.msg = ' + res.msg)
      Message({
        message: '操作失败' + res.msg,
        type: 'error',
        duration: 2000
      })

      // 202:Token 需要认证;
      if (res.code === 202) {
        MessageBox.confirm(
          '你已被登出，可以取消继续留在该页面，或者重新登录',
          '确定登出',
          {
            confirmButtonText: '重新登录',
            cancelButtonText: '取消',
            type: 'warning'
          }
        ).then(() => {
          store.dispatch('FedLogOut').then(() => {
            location.reload() // 为了重新实例化vue-router对象 避免bug
          })
        })
      }
      return Promise.reject(response)
    } else {
      return response.data.data
    }
  },
  error => {
    console.log('err' + error) // for debug
    Message({
      message: '操作错误',
      type: 'error',
      duration: 1000
    })
    return Promise.reject(error)
  }
)

export default service
