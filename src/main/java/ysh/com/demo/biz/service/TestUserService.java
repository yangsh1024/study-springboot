package ysh.com.demo.biz.service;

import ysh.com.db.base.biz.service.BaseService;
import ysh.com.demo.biz.entity.TestUser;

/**
 * @author yangsh
 * @since 2021/4/15 4:35 下午
 */
public interface TestUserService extends BaseService<TestUser> {

    /**
     * 保存
     *
     * @param user
     *            参数
     * @return 是否成功
     */
    boolean saveUser(TestUser user);
}
