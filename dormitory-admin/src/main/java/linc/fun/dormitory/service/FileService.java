package linc.fun.dormitory.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author yqlin
 * @date 2022/4/15 21:05
 * @description
 */
public interface FileService {
    /**
     * 文件上传接口
     */
    String upload(MultipartFile file) throws Exception;
}
