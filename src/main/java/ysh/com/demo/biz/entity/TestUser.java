package ysh.com.demo.biz.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ysh.com.db.base.biz.entity.BaseEntity;

/**
 * @author yangsh
 * @since 2021/4/15 4:13 下午
 */
@ApiModel(value = "TestUser", description = "测试用户对象")
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("test_user")
public class TestUser extends BaseEntity {

    private String userName;

    private String password;

}
