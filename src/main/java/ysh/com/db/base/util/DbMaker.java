package ysh.com.db.base.util;

import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import ysh.com.db.base.annotation.Column;
import ysh.com.db.base.annotation.Desc;
import ysh.com.demo.biz.entity.TestUser;

/**
 * @author yangsh
 */
public class DbMaker {
    public DbMaker() {}

    public static void main(String[] args) {
        DbMaker dbmaker = new DbMaker() {};
        String mysqlSql = dbmaker.getMysqlSql(TestUser.class);
        System.out.println(mysqlSql);

    }

    public void printClasses(String projectPath) {
        List<String> classNameList = new ArrayList<String>();
        scan(new File(projectPath), classNameList);
        String classNames = "";
        for (String className : classNameList) {
            classNames += "," + className + ".class";
        }
        classNames = "Class<?>[] classes = {" + classNames.substring(1) + "};";
        System.out.println(classNames);
    }

    private void scan(File folder, List<String> classNames) {
        if (folder != null && folder.exists() && folder.isDirectory()) {
            File[] children = folder.listFiles();
            if (children != null && children.length > 0) {
                for (File file : children) {
                    if (file.isDirectory()) {
                        scan(file, classNames);
                    } else {
                        String path = file.getPath();
                        String packageName = path.substring(0, path.lastIndexOf(File.separator));
                        String fileName = file.getName();
                        if (StrUtil.endWith(fileName, ".java") && StrUtil.endWith(packageName, "entity")) {
                            String entityName = fileName.split("\\.")[0];
                            classNames.add(entityName);
                        }
                    }
                }
            }
        }
    }

    public void printMysql(boolean needHis, Class<?>[] classes) {
        for (Class<?> clazz : classes) {
            // 获取表名
            String tableName = getTableNameByMybatisPlusAnnotation(clazz);
            String sql = getMysqlSql(clazz);
            System.out.println(needHis ? sql + "\r\n" + sql.replaceAll(tableName, tableName + "_his") : sql);
        }
    }

    private void write(String path, String content) {
        File file = new File(path);
        FileWriter writer = null;
        try {
            if (!file.exists()) {
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                file.createNewFile();
            }
            writer = new FileWriter(file);
            writer.write(content);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getMysqlSql(Class<?> clazz) {
        String tableComment = clazz.getAnnotation(Desc.class).value();
        // 获取表名
        String tableName = getTableNameByMybatisPlusAnnotation(clazz);

        StringBuilder mysql = new StringBuilder();
        mysql.append("drop table if exists ").append(tableName).append(";\r\n");
        mysql.append("create table ").append(tableName).append("(\r\n");
        mysql.append("    id int(18) not null auto_increment,\r\n");

        // 获取所有字段
        Field[] fields = ReflectUtil.getFields(clazz);

        StringBuilder uniqueSql = new StringBuilder();
        for (Field field : fields) {
            // 获取字段名
            String columnName = getTableFieldNameByMybatisPlusAnnotation(field);
            if (StrUtil.equals(columnName, "id")) {
                // id 跳过
                continue;
            }
            // 描述
            Desc aDesc = field.getAnnotation(Desc.class);
            String comment = "";
            if (aDesc != null) {
                comment = aDesc.value();
            }

            TableField tableField = AnnotationUtil.getAnnotation(field, TableField.class);
            if (tableField != null && !tableField.exist()) {
                continue;
            }
            // 字段属性
            Column aColumn = field.getAnnotation(Column.class);
            if (aColumn == null) {
                continue;
            }
            // 是否可为null
            String nullable = "";
            if (!aColumn.nullable()) {
                nullable = " not null ";
            }
            if (aColumn.unique()) {
                uniqueSql.append("    unique key uk").append(columnName).append("(").append(columnName)
                    .append("), \r\n");
            }
            // 类型
            String fieldTypeClassName = field.getType().getName();
            // 字段长度
            int columnLength = aColumn.length();
            if (StrUtil.equals(fieldTypeClassName, "java.lang.String")) {
                if (aColumn.length() > 4000) {
                    mysql.append("    ").append(columnName).append(" text ");
                } else {
                    mysql.append("    ").append(columnName).append(" varchar(").append(columnLength).append(") ");
                }
            } else if (StrUtil.equals(fieldTypeClassName, "java.lang.Long")
                || StrUtil.equals(fieldTypeClassName, "long")) {
                mysql.append("    ").append(columnName).append(" int(").append(columnLength).append(") ");
            } else if (StrUtil.equals(fieldTypeClassName, "java.lang.Integer")
                || StrUtil.equals(fieldTypeClassName, "int")) {
                mysql.append("    ").append(columnName).append(" int(").append(columnLength).append(") ");
            } else if (StrUtil.equals(fieldTypeClassName, "java.util.Date")
                || StrUtil.equals(fieldTypeClassName, "java.time.LocalDateTime")
                || StrUtil.equals(fieldTypeClassName, "java.time.LocalDate")) {
                mysql.append("    ").append(columnName).append(" datetime  ");
            } else if (StrUtil.equals(fieldTypeClassName, "java.math.BigDecimal")) {
                // 小数
                int precision = aColumn.precision();
                mysql.append("    ").append(columnName).append(" decimal(").append(columnLength).append(",")
                    .append(precision).append(") ");
            }

            mysql.append(nullable);
            if (aDesc != null && StrUtil.isNotBlank(aDesc.defaultVal())) {
                mysql.append(" default '").append(aDesc.defaultVal()).append("'");
            }
            mysql.append(" comment '").append(comment).append("',\r\n");
        }
        mysql.append(uniqueSql);
        mysql.append("    ").append("primary key (id)\r\n");
        mysql.append(") comment '").append(tableComment).append("';\r\n\r\n");
        return mysql.toString();

    }

    /**
     * 从entity获取表名 <br>
     * 根据Mybatis-Plus的TableName注解
     *
     * @param clazz
     *            类
     * @return 表名
     */
    private String getTableNameByMybatisPlusAnnotation(Class<?> clazz) {
        // 获取mybatis-plus注解表名
        Object annotationValue = AnnotationUtil.getAnnotationValue(clazz, TableName.class);
        return annotationValue.toString();
    }

    /**
     * 获取字段名
     * 
     * 未定义字段名的，默认驼峰转下划线
     */
    private String getTableFieldNameByMybatisPlusAnnotation(Field field) {
        Object annotationValue = AnnotationUtil.getAnnotationValue(field, TableField.class);
        if (annotationValue != null) {
            // 写了字段名
            return annotationValue.toString();
        } else {
            // 未定义字段名
            String fieldName = field.getName();
            return camelToUnderline(fieldName);

        }

    }

    /**
     * 驼峰转下划线命名
     */
    private String camelToUnderline(String param) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (Character.isUpperCase(c)) {
                sb.append("_");
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
