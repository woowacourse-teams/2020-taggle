package kr.taggle.aop;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import net.minidev.json.JSONObject;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Slf4j
@Component
public class ControllerLoggingAspect {

    private static JSONObject getParams(final HttpServletRequest request) {
        final JSONObject jsonObject = new JSONObject();
        final Enumeration<String> params = request.getParameterNames();
        while (params.hasMoreElements()) {
            final String param = params.nextElement();
            final String replaceParam = param.replaceAll("\\.", "-");
            jsonObject.put(replaceParam, request.getParameter(param));
        }
        return jsonObject;
    }

    @Pointcut("execution(* kr.taggle..controller..*Controller.*(..))")
    public void logPointCut() {
    }

    @Around("logPointCut()")
    public Object logAround(final ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object result = null;

        try {
            final long start = System.currentTimeMillis();
            result = proceedingJoinPoint.proceed();
            final long end = System.currentTimeMillis();

            final HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
            final String controllerName = proceedingJoinPoint.getSignature().getDeclaringType().getSimpleName();
            final String methodName = proceedingJoinPoint.getSignature().getName();

            final Map<String, Object> params = new HashMap<>();
            params.put("controller", controllerName);
            params.put("method", methodName);
            params.put("params", getParams(request));
            params.put("request_uri", request.getRequestURI());
            params.put("http_method", request.getMethod());
            params.put("execution_time", end - start + "ms");
            log.info("params : {}", params);
        } catch (final Exception e) {
            log.error("LoggingAspect error" + e.getMessage());
        }
        return result;
    }
}
