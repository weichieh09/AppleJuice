package tw.com.lyls.AppleJuice.aspect;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Aspect
@Component
@Slf4j
public class TraceLogAspect {

    @Around("within(@org.springframework.web.bind.annotation.RestController *)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        // 取得請求物件
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = (attributes != null) ? attributes.getRequest() : null;

        // 取得 URI
        String uri = (request != null) ? request.getRequestURI() : "UNKNOWN_URI";
        // 取得 Query 參數
        Map<String, String[]> queryParams = (request != null) ? request.getParameterMap() : Map.of();
        // 取得 Method 參數
        Object[] methodArgs = joinPoint.getArgs();

        log.info("進入：{}，Query參數：{}，Method參數：{}。"
                , uri, JSONUtil.toJsonStr(queryParams), JSONUtil.toJsonStr(methodArgs));

        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long duration = System.currentTimeMillis() - startTime;

        log.info("離開：{}，耗時：{}，結果：{}。", uri, duration, JSONUtil.toJsonStr(result));
        return result;
    }
}