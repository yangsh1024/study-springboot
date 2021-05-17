package ysh.com.sys.config.mybatis.plus;

import java.time.LocalDateTime;

import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;

/**
 * 默认
 * 
 * insert，自动更新createTime和lastModifyTime
 * 
 * update，自动更新lastModifyTime
 * 
 * @author yangsh
 * @since 2021/4/16 2:52 下午
 */
@Component
public class MybatisPlusMetaHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("createTime", LocalDateTime.now(), metaObject);
        this.setFieldValByName("lastModifyTime", LocalDateTime.now(), metaObject);

    }

    @Override
    public void updateFill(MetaObject metaObject) {
        // 更新时，不修改创建时间（手动操作除外）
        this.setFieldValByName("lastModifyTime", LocalDateTime.now(), metaObject);
    }
}
