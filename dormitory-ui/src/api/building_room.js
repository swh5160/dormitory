import request from '@/utils/request'
import { HttpMethod } from '@/utils/http'

const group_name = '/api/building_room'

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
 * 添加
 */
export function save(data) {
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
export function getByById(id) {
  return request({
    url: `${group_name}/${id}`,
    method: HttpMethod.GET
  })
}

/**
 * 床位占位
 */
export function placeholderBed(data) {
  return request({
    url: `${group_name}/bed/placeholder`,
    method: HttpMethod.POST,
    data: data
  })
}

/**
 * 床位清空
 */
export function clearPlaceholderBed(roomBedId) {
  return request({
    url: `${group_name}/bed/placeholder/clear/${roomBedId}`,
    method: HttpMethod.DELETE
  })
}
