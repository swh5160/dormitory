package linc.fun.dormitory.util;

import cn.hutool.core.util.IdUtil;
import org.apache.commons.io.FilenameUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Objects;

/**
 * @author yqlin
 * @date 2022/4/14 23:22
 * @description 文件工具
 */
public class FileUtils {

    /**
     * 获取文件名的后缀
     */
    public static String getExtension(MultipartFile file) {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (StringUtils.isEmpty(extension)) {
            extension = MimeTypeUtils.getExtension(Objects.requireNonNull(file.getContentType()));
        }
        return extension;
    }

    /**
     * 编码文件名
     */
    public static String extractFilename(MultipartFile file) {
        String extension = getExtension(file);
        return DateUtils.datePath() + "/" + IdUtil.fastUUID() + "." + extension;
    }

    private static File getAbsoluteFile(String uploadDir, String fileName) {
        File desc = new File(uploadDir + File.separator + fileName);
        if (!desc.exists()) {
            if (!desc.getParentFile().exists()) {
                desc.getParentFile().mkdirs();
            }
        }
        return desc;
    }

}
