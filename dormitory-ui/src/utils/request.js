import axios from 'axios'
import { MessageBox, Message } from 'element-ui'
import store from '@/store'
import { getToken } from '@/utils/auth'

// 创建axios实例
const service = axios.create({
  // axios中请求配置有baseURL选项，表示请求URL公共部分
  baseURL: process.env.VUE_APP_BASE_API,
  // 超时
  timeout: 5000
})

// request拦截器
service.interceptors.request.use(
  config => {
    if (store.getters.token) {
      // 需要的token
      config.headers['token'] = getToken()
    }
    return config
  },
  error => {
    // do something with request error
    return Promise.reject(error)
  }
)

// request拦截器
service.interceptors.response.use(
  response => {
    const res = response.data
    // console.log(res)
    /**
     * 后端的返回值
     * {
     *   "data": {},
     *   "success": true,
     *   "errCode": "fake_data",
     *   "errMessage": "fake_data"
     * }
     */
    if (!res.success) {
      Message({
        message: res.errMessage || 'Error',
        type: 'error',
        duration: 5 * 1000
      })
      return Promise.reject(new Error(res.errMessage || 'Error'))
    } else {
      return res
    }
  },
  error => {
    Message({
      message: '操作失败',
      type: 'error',
      duration: 5 * 1000
    })
    return Promise.reject(error)
  }
)

export default service
