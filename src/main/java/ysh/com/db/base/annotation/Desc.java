
package ysh.com.db.base.annotation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 描述注解用于自动生成建表语句
 *
 * @author yangsh
 */
@Target({METHOD, FIELD, TYPE})
@Retention(RUNTIME)
public @interface Desc {
    /**
     * 描述
     */
    public String value() default "";

    /**
     * 默认值
     */
    public String defaultVal() default "";
}
