import request from '@/utils/request'
import { HttpMethod } from '@/utils/http'

const group_name = '/api/statistical'

/**
 * 获取统计信息
 */
export function getStatistical() {
  return request({
    url: `${group_name}/get`,
    method: HttpMethod.GET
  })
}
