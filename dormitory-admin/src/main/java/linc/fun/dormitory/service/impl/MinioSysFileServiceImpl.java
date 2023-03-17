package linc.fun.dormitory.service.impl;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import linc.fun.dormitory.config.minio.MinioProperties;
import linc.fun.dormitory.service.FileService;
import linc.fun.dormitory.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class MinioSysFileServiceImpl implements FileService {
    @Autowired
    private MinioClient minioClient;

    @Override
    public String upload(MultipartFile file) throws Exception {
        String fileName = FileUtils.extractFilename(file);
        PutObjectArgs args = PutObjectArgs.builder()
                .bucket(MinioProperties.MINIO_BUCKET_NAME)
                .object(fileName)
                .stream(file.getInputStream(), file.getSize(), -1)
                .contentType(file.getContentType())
                .build();
        minioClient.putObject(args);
        return MinioProperties.PREFIX_URL + fileName;
    }
}