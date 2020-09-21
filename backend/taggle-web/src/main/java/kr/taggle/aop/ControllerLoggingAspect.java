package kr.taggle.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Slf4j
@Component
public class ControllerLoggingAspect {

    private static final String LOG_MESSAGE_FORMAT = "{ controller: {}, method: {}, execution_time: {}ms} ";

    @Pointcut("execution(* kr.taggle..controller..*Controller.*(..))")
    public void logPointCut() {
    }

    @Around("logPointCut()")
    public Object logAround(final ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        final long start = System.currentTimeMillis();
        final Object result = proceedingJoinPoint.proceed();
        final long end = System.currentTimeMillis();

        try {
            final String controllerName = proceedingJoinPoint.getSignature().getDeclaringType().getSimpleName();
            final String methodName = proceedingJoinPoint.getSignature().getName();
            final long executionTime = end - start;
            log.info(LOG_MESSAGE_FORMAT, controllerName, methodName, executionTime);
        } catch (final Exception e) {
            log.error("LoggingAspect error" + e.getMessage());
        }
        return result;
    }
}
