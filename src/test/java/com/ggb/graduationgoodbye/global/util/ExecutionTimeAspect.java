package com.ggb.graduationgoodbye.global.util;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Around;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExecutionTimeAspect {

  @Around("execution(* com.ggb.graduationgoodbye.global.util..*(..))")
  public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
    long startTime = System.nanoTime();
    Object proceed = joinPoint.proceed();
    long endTime = System.nanoTime();

    String methodName = joinPoint.getSignature().getName();
    long time = endTime - startTime;

    printExecutionTime(methodName, time);
    return proceed;
  }

  private void printExecutionTime(String methodName, long time) {
    long seconds = time / 1_000_000_000;
    long milliseconds = (time % 1_000_000_000) / 1_000_000;
    long nanoseconds = (time % 1_000_000);

    System.out.println("[" + methodName + "]"
        + " : "+ seconds + "s "+ milliseconds + "ms "+ nanoseconds + "ns");
  }
}
