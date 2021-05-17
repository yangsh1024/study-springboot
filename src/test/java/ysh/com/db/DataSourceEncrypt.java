package ysh.com.db;

import javax.annotation.Resource;

import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 
 * 数据库链接配置加密
 * 
 * @author yangsh
 * @since 2021/5/17 12:58 下午
 */
@SpringBootTest
public class DataSourceEncrypt {

    @Resource
    private StringEncryptor stringEncryptor;

    @Test
    public void encrypt() {

        String en = stringEncryptor.encrypt("study");
        System.out.println(en);

    }
}
