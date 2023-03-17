import request from '@/utils/request'
import { HttpMethod } from '@/utils/http'

const group_name = '/api/admin'

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
 * 添加/修改
 */
export function deleteById(id) {
  return request({
    url: `${group_name}/${id}`,
    method: HttpMethod.DELETE
  })
}
