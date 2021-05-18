package ysh.com.db.base.biz.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;


import lombok.Data;
import ysh.com.db.base.annotation.Column;

/**
 * @author yangsh
 * @since 2021/4/16 10:54 上午
 */
@Data
public class BaseEntity implements Serializable {

    /**
     * 主键，默认用配置设置自增
     */
    @TableId(type = IdType.AUTO)
    @Column(length = 18)
    protected Long id;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @Column
    protected LocalDateTime createTime;

    @TableField(value = "last_modify_time", fill = FieldFill.INSERT_UPDATE)
    @Column
    protected LocalDateTime lastModifyTime;
}
