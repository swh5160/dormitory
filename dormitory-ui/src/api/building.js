import request from '@/utils/request'
import { HttpMethod } from '@/utils/http'

const group_name = '/api/building'

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
 * 查出所有楼宇信息
 */
export function getAll() {
  return request({
    url: `${group_name}/get/all`,
    method: HttpMethod.GET
  })
}
