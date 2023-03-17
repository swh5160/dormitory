import request from '@/utils/request'
import { HttpMethod } from '@/utils/http'

const group_name = '/api/student'

/**
 * 根据分页查询列表
 */
export function getPage(query) {
  return request({
    url: `${group_name}/get/page`,
    method: HttpMethod.GET,
    params: query
  })
}

/**
 * 添加/修改
 */
export function saveOrUpdate(data) {
  return request({
    url: `${group_name}`,
    method: HttpMethod.POST,
    data: data
  })
}

/**
 * 根据id删除
 */
export function deleteById(id) {
  return request({
    url: `${group_name}/${id}`,
    method: HttpMethod.DELETE
  })
}

/**
 * 根据id查询
 */
export function getStudentById(id) {
  return request({
    url: `${group_name}/${id}`,
    method: HttpMethod.GET
  })
}

/**
 * 根据id查询个人信息
 */
export function getSelfInfo() {
  return request({
    url: `${group_name}/self/info`,
    method: HttpMethod.GET
  })
}

/**
 * 申请床位
 */
export function applyBed(data) {
  return request({
    url: `${group_name}/apply/bed`,
    method: HttpMethod.POST,
    data
  })
}

/**
 * 学生打卡
 */
export function clockIn() {
  return request({
    url: `${group_name}/clock/in`,
    method: HttpMethod.POST
  })
}

/**
 * 获取学生打卡信息
 */
export function getClockInfo() {
  return request({
    url: `${group_name}/get/clock/info`,
    method: HttpMethod.GET
  })
}

/**
 * 判断当天学生是否打卡
 */
export function checkClockIn() {
  return request({
    url: `${group_name}/check/clock`,
    method: HttpMethod.GET
  })
}

/**
 * 修改个人信息
 */
export function updateSelf(data) {
  return request({
    url: `${group_name}/update/self`,
    method: HttpMethod.PUT,
    data
  })
}
/**
 * 修改个人信息
 */
export function updatePassword(data) {
  return request({
    url: `${group_name}/update/password`,
    method: HttpMethod.PUT,
    data
  })
}
