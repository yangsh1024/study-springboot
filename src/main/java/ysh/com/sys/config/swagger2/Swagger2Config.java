package ysh.com.sys.config.swagger2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * ysh.com下
 * <p>
 * dev和test环境开启swagger2
 *
 * @author yangsh
 * @since 2021/4/15 3:30 下午
 */
@Configuration
@EnableSwagger2
@Slf4j
public class Swagger2Config {

    @Value("${spring.profiles.active}")
    private String profileActive;

    /**
     * 可以根据自定义注解去生成文档
     *
     */
    @Bean
    public Docket docket() {
        // 判断是否生成文档
        boolean enableSwagger2 = enableSwagger2();
        log.info("当前环境：【{}】，Swagger2：【{}】", profileActive, enableSwagger2 ? "开启" : "关闭");

        String groupName = "ysh文档";
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(initSwagger2ApiInfo())
            // 是否开启swagger2
            .enable(enableSwagger2).groupName(groupName).select()
            // 为当前包下的controller生成文档
            .apis(RequestHandlerSelectors.basePackage("ysh.com"))
            // 为有@Api注解的Controller生成API文档
            // .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
            // 为有@ApiOperation注解的方法生成API文档 ；
            .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
            // 路径扫描
            .paths(PathSelectors.any()).build();
    }

    private ApiInfo initSwagger2ApiInfo() {
        return new ApiInfoBuilder().title("接口文档").description("对接使用").version("1.0.0").build();
    }

    /**
     * 是否开启swagger2 只有dev和test环境开启 其他环境关闭
     */
    private boolean enableSwagger2() {

        return StrUtil.equalsAny(profileActive, "dev", "test");
    }

}
