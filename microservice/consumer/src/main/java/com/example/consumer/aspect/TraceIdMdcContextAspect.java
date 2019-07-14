package com.example.consumer.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Description: it's purpose...
 *
 * @author linxd 2019-7-3
 * @version 1.0
 */
@Aspect
@Component
public class TraceIdMdcContextAspect {

    private static final String ID = "Trace-Id";

    private static AtomicInteger atomic = new AtomicInteger(0);


    @Pointcut("execution(* com.example.consumer.controller.*.*(..))")
    public void traceIdDefined(){ }

    @Around("traceIdDefined()")
    public Object process(ProceedingJoinPoint joinPoint) throws Throwable {

        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        String traceId = request.getHeader(ID);
        if (traceId == null){
            traceId = generateTraceId();
        }
        MDC.put(ID, traceId);
        try{
            return joinPoint.proceed();
        }
        finally {
            MDC.clear();
        }
    }


    private String generateTraceId(){
        return " Trace-" + atomic.getAndIncrement();
    }


}
