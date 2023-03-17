import request from '@/utils/request'
import { HttpMethod } from '@/utils/http'

const group_name = '/api/auth'

/**
 * 用户登录
 */
export function login(data) {
  return request({
    url: `${group_name}/login`,
    method: HttpMethod.POST,
    data
  })
}

/**
 * 获取用户信息
 */
export function getInfo() {
  return request({
    url: `${group_name}/get/info`,
    method: HttpMethod.GET
  })
}

// 获取验证码
export function getCaptcha() {
  return request({
    url: `${group_name}/get/captcha`,
    method: HttpMethod.GET
  })
}
