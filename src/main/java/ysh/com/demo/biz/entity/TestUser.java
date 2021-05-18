package ysh.com.demo.biz.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ysh.com.db.base.biz.entity.BaseEntity;

/**
 * @author yangsh
 * @since 2021/4/15 4:13 下午
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("test_user")
public class TestUser extends BaseEntity {

    @TableField
    private String userName;

    private String password;

}
