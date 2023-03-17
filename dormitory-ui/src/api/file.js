import request from '@/utils/request'
import { HttpMethod } from '@/utils/http'

const group_name = '/api/file'

/**
 * 头像上传
 */
export function upload(data) {
  return request({
    url: `${group_name}/upload`,
    method: HttpMethod.POST,
    data: data
  })
}
