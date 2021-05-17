package ysh.com;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 启动类 <br>
 * MapperScan 对应dao包 <br>
 * EnableTransactionManagement 开启事务<br>
 * EnableAsync 开启异步注解功能 <br>
 * EnableScheduling 开启定时任务功能 <br>
 * EnableSwagger2 swagger2 <br>
 * EnableEncryptableProperties 开启秘钥加密
 *
 * @author yangsh
 */
@MapperScan("ysh.com.**.dao")
@EnableTransactionManagement
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableEncryptableProperties
@SpringBootApplication
public class StudySpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudySpringBootApplication.class, args);
    }

}
