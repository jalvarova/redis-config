package org.redis.manager.rediscache.configuration;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

@Aspect
@Component
@Slf4j
public class ScheduleAspect {


    @Value("${schedule.cron}")
    private String cronExpression;

    @Around(value = "execution(* *.*.*.*.schedule.Schedule*.*(..))")
    public void executionJob(ProceedingJoinPoint point) throws Throwable {

        long start = System.currentTimeMillis();
        SimpleDateFormat formatTemps = new SimpleDateFormat("HH:mm:ss");

        log.info("Java cron job expression:: " + cronExpression);
        log.info("El metodo " + point.getSignature().getName() + " tiempo " + formatTemps.format(start) + " esta iniciando...");
        point.proceed();

        long end = System.currentTimeMillis();
        long betweenTime = end - start;
        long seconds = TimeUnit.MILLISECONDS.toSeconds(betweenTime);

        log.info(point.getTarget().getClass() + "." + point.getSignature().getName() + " " + formatTemps.format(end) + " totalTime");

        log.info("El metodo " + point.getSignature().getName() + " esta finalizando la ejecución in  " + seconds + " seconds");

    }

    @AfterThrowing(value = "execution(* *.*.*.*.schedule.Schedule*.*(..))", throwing = "e")
    public void loggingThrowing(JoinPoint jp, Throwable e) {
        log.info("Message exception: " + e.getMessage());
    }
}
