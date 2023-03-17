package linc.fun.dormitory.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 填充公共字段使用
 *
 * @author linc
 */
@Slf4j
@Component
public class MybatisPlusMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill ....");
        this.strictInsertFill(metaObject, "gmtCreate", LocalDateTime::now, LocalDateTime.class);
        this.strictInsertFill(metaObject, "gmtModified", LocalDateTime::now, LocalDateTime.class);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill start....");
        this.setFieldValByName("gmtModified", LocalDateTime.now(), metaObject);
    }
}