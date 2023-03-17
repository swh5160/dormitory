package linc.fun.dormitory.config.minio;

import linc.fun.dormitory.constants.CommonConstants;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * MiniIO文件服务
 * @author linc
 */
@Component
public class MinioProperties implements InitializingBean {

    /**
     * 定义公开静态常量
     */
    public static String MINIO_END_POINT;
    public static Integer MINIO_PORT;
    public static String MINIO_ACCESS_KEY;
    public static String MINIO_SECRET_KEY;
    public static Boolean MINIO_SECURE;
    public static String MINIO_BUCKET_NAME;
    public static String PREFIX_URL;
    /**
     * endPoint是一个URL，域名，IPv4或者IPv6地址
     */
    @Value("${minio.endpoint}")
    private String endpoint;
    /**
     * TCP/IP端口号
     */
    @Value("${minio.port}")
    private Integer port;
    /**
     * accessKey类似于用户ID，用于唯一标识你的账户
     */
    @Value("${minio.access-key}")
    private String accessKey;
    /**
     * secretKey是你账户的密码
     */
    @Value("${minio.secret-key}")
    private String secretKey;
    /**
     * 如果是true，则用的是https而不是http,默认值是true
     */
    @Value("${minio.secure}")
    private Boolean secure;
    /**
     * 存储桶名称
     */
    @Value("${minio.bucket-name}")
    private String bucketName;

    @Override
    public void afterPropertiesSet() throws Exception {
        MINIO_END_POINT = endpoint;
        MINIO_PORT = port;
        MINIO_ACCESS_KEY = accessKey;
        MINIO_SECRET_KEY = secretKey;
        MINIO_SECURE = secure;
        MINIO_BUCKET_NAME = bucketName;
        PREFIX_URL = CommonConstants.HTTP + MINIO_END_POINT + ":" + MINIO_PORT + "/" + MINIO_BUCKET_NAME + "/";
    }
}