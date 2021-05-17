package ysh.com.sys.config.mybatis.plus;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;

/**
 * @author yangsh
 * @since 2021/4/15 4:29 下午
 */
@Configuration
public class MybatisPlusConfig {

    /**
     * 分页插件
     */
    @Bean
    public PaginationInnerInterceptor paginationInnerInterceptor() {
        return new PaginationInnerInterceptor();
    }

    /**
     * mybatis-plus自动填充配置</br>
     * 设置insert </br>
     * 添加创建和修改时间 </br>
     * 设置update </br>
     * 更新修改时间
     */
    @Bean
    public GlobalConfig globalConfig() {
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setMetaObjectHandler(new MybatisPlusMetaHandler());
        return globalConfig;
    }

}
