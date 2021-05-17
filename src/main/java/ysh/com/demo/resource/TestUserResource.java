package ysh.com.demo.resource;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import ysh.com.demo.biz.entity.TestUser;
import ysh.com.demo.biz.service.TestUserService;

/**
 * @author yangsh
 * @since 2021/4/15 4:38 下午
 */
@Api("测试用户接口")
@RestController
@RequestMapping("TestUser")
public class TestUserResource {

    @Resource
    private TestUserService testUserService;

    @ApiOperation("保存测试用户")
    @PostMapping("/saveUser")
    public boolean saveUser(@RequestBody TestUser u) {
        return testUserService.saveUser(u);

    }

    @ApiOperation("根据id获取")
    @GetMapping("/user")
    public TestUser user(Long id) {
        return testUserService.getById(id);

    }

    @ApiOperation("获取全部")
    @GetMapping("/listAll")
    public List<TestUser> listAll() {
        return testUserService.list();

    }

    @ApiOperation("更新")
    @PutMapping("testUser")
    public boolean update(@RequestBody TestUser t) {
        return testUserService.updateById(t);
    }

    @ApiOperation("test")
    @PostMapping("/test")
    public List<TestUser> test(MultipartFile file) {
        return testUserService.list();

    }

}
