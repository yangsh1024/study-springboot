package ysh.com.demo.biz.service.impl;

import org.springframework.stereotype.Service;

import ysh.com.db.base.biz.service.impl.BaseServiceImpl;
import ysh.com.demo.biz.entity.TestUser;
import ysh.com.demo.biz.service.TestUserService;

/**
 * @author yangsh
 * @since 2021/4/15 4:36 下午
 */
@Service
public class TestUserServiceImpl extends BaseServiceImpl<TestUser> implements TestUserService {
    @Override
    public boolean saveUser(TestUser user) {
        return save(user);
    }
}
