package com.adventofcode;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.jetbrains.annotations.NotNull;
import org.springframework.aop.interceptor.PerformanceMonitorInterceptor;


public class ControllerMonitoringInterceptor extends PerformanceMonitorInterceptor
{
    @Override
    protected void writeToLog(@NotNull Log logger, @NotNull String message, Throwable ex)
    {
        if (ex != null)
        {
            logger.info(message, ex);
        }
        else
        {
            logger.info(message);
        }
    }

    protected boolean isInterceptorEnabled(@NotNull MethodInvocation invocation, @NotNull Log logger)
    {
        return true;
    }
}