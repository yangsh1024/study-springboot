package ysh.com.db.base.annotation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 
 * 不想因为一个注解，引入一个javaee-api-8.0.jar
 * 
 * 描述注解用于自动生成建表语句
 * 
 * 主要标注字段长度,描述
 * 
 * @author yangsh
 * @since 2021/4/26 10:30 上午
 */
@Target({METHOD, FIELD, TYPE})
@Retention(RUNTIME)
public @interface Column {

    /**
     * (Optional) Whether the column is a unique key. This is a shortcut for the <code>UniqueConstraint</code>
     * annotation at the table level and is useful for when the unique key constraint corresponds to only a single
     * column. This constraint applies in addition to any constraint entailed by primary key mapping and to constraints
     * specified at the table level.
     */
    boolean unique() default false;

    /**
     * (Optional) Whether the database column is nullable.
     */
    boolean nullable() default true;

    /**
     * (Optional) The column length. (Applies only if a string-valued column is used.)
     */
    int length() default 255;

    /**
     * (Optional) The precision for a decimal (exact numeric) column. (Applies only if a decimal column is used.) Value
     * must be set by developer if used when generating the DDL for the column.
     */
    int precision() default 0;

}
