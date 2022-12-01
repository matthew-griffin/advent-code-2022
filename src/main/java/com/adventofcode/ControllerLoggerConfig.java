package com.adventofcode;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
@Aspect
public class ControllerLoggerConfig
{
    @Pointcut("execution(* com.adventofcode.*Controller+.*(..))")
    public void monitor()
    {

    }

    @Bean
    public ControllerMonitoringInterceptor controllerMonitoringInterceptor()
    {
        return new ControllerMonitoringInterceptor();
    }

    @Bean
    public Advisor advisorPerformance()
    {
        AspectJExpressionPointcut aspectJExpressionPointcut = new AspectJExpressionPointcut();
        aspectJExpressionPointcut.setExpression("com.adventofcode" +
                ".ControllerLoggerConfig.monitor()");
        return new DefaultPointcutAdvisor(aspectJExpressionPointcut,
                controllerMonitoringInterceptor());
    }
}