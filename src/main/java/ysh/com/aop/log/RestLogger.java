package ysh.com.aop.log;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.ValueFilter;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * GET 只打印请求参数
 * <p>
 * POST 打印请求和返回参数
 * <p>
 * 注：文件参数不做打印
 *
 * @author yangsh
 */
@Component
@Aspect
@Slf4j
@Order(1)
public class RestLogger {

    private static final String GET = "GET";
    private static final String POST = "POST";

    @Resource
    private HttpServletRequest request;

    public RestLogger() {
        log.info("init RestLogger");
    }

    @Pointcut("@within(org.springframework.stereotype.Controller) || @within(org.springframework.web.bind.annotation.RestController)")
    public void log() {}

    @Pointcut("execution(* ysh.com..*(..)) ")
    public void logPackage() {}

    /**
     * 只针对Controller注解或者RestController注解<br>
     * <p>
     * 并且是该项目定义的包中（jd.com中）
     */
    @Around("log() && logPackage()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        String requestMethod = request.getMethod();
        String requestUri = request.getRequestURI();
        StringBuilder sb = new StringBuilder();
        sb.append("请求-");
        sb.append(requestMethod);
        sb.append("||uri:").append(requestUri);

        Object[] args = joinPoint.getArgs();
        String[] parameterNames = ((CodeSignature)joinPoint.getSignature()).getParameterNames();
        // 拼接参数
        String paramStr = argsArrayToString(parameterNames, args);
        sb.append("||params:").append(paramStr);

        log.info(sb.toString());

        Object response = joinPoint.proceed();

        if (!StrUtil.equalsIgnoreCase(GET, requestMethod)) {
            // 不是GET请求，打印返回参数
            log.info("响应-{}", objectToString(response));
        }

        return response;

    }

    /**
     * 参数转为日志字符
     */
    private String argsArrayToString(String[] parameterNames, Object[] paramsArray) {
        StringBuilder params = new StringBuilder();
        if (parameterNames != null && parameterNames.length > 0) {

            for (int index = 0; index < parameterNames.length; index++) {
                String paramName = parameterNames[index];
                Object paramObject = paramsArray[index];
                // 参数名
                params.append(paramName).append(":");
                // 参数值

                String paramString = objectToString(paramObject);
                params.append(paramString);
                // 参数间分割符
                params.append(";");
            }
        }
        return params.toString();
    }

    /**
     * 参数转为日志字符
     */
    private String objectToString(Object object) {
        StringBuilder params = new StringBuilder();
        // 单字符串最大长度
        int maxParamLength = 3999;
        // 参数值
        if (object == null) {
            return null;
        } else if (object instanceof HttpServletRequest) {
            return "httpServletRequest/";
        } else if (object instanceof HttpServletResponse) {
            return "httpServletResponse/";
        } else if (object instanceof MultipartFile || object instanceof byte[]) {
            return "file/";
        } else if (object instanceof String) {
            String str = (String)object;
            if (str.length() > maxParamLength) {
                // 字符串长度超过3999
                return "String length exceeds 3999";
            }
        }
        // 其他情况默认转json
        try {
            ValueFilter valueFilter = (o, s, o1) -> {
                if (o1 == null) {
                    return null;
                }
                if (o1 instanceof MultipartFile || o1 instanceof byte[]) {
                    // 值是文件
                    return "file/";
                } else if (o1 instanceof String) {
                    String str = (String)o1;
                    // 字符长度超过3999，认为是文件，不做打印
                    if (str.length() > maxParamLength) {
                        return "String length exceeds 3999";
                    }
                }
                return o1;
            };
            params.append(JSON.toJSONString(object, SerializeConfig.globalInstance, new SerializeFilter[] {valueFilter},
                "yyyy-MM-dd HH:mm:ss", JSON.DEFAULT_GENERATE_FEATURE)).append(" ");
        } catch (Exception e) {
            log.warn("json 转化失败", e);
            params.append(object.toString()).append(" ");
        }
        return params.toString();
    }

}
