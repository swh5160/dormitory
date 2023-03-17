import request from '@/utils/request'
import { HttpMethod } from '@/utils/http'

const group_name = '/api/problem'

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
 * 删除
 */
export function deleteById(id) {
  return request({
    url: `${group_name}/${id}`,
    method: HttpMethod.DELETE
  })
}

/**
 * 根据id通过问题
 */
export function audit(data) {
  return request({
    url: `${group_name}/audit`,
    method: HttpMethod.PUT,
    data
  })
}

/**
 * 根据分页查询我的问题列表
 */
export function getMyPage(query) {
  return request({
    url: `${group_name}/get/my/page`,
    method: HttpMethod.GET,
    params: query
  })
}

/**
 * 获取最近的问题信息
 */
export function getRecentProblems(nums) {
  return request({
    url: `${group_name}/get/recent/${nums}`,
    method: HttpMethod.GET
  })
}
