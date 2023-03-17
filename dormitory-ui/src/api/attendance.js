import request from '@/utils/request'
import { HttpMethod } from '@/utils/http'

const group_name = '/api/attendance'

/**
 * 根据分页查询列表
 */
export function getAttendancePage(query) {
  return request({
    url: `${group_name}/get/page`,
    method: HttpMethod.GET,
    params: query
  })
}

