package com.reloadly.devops.configs;

import java.util.concurrent.Executor;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.reloadly.devops.utilities.AppProperties;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableAsync
@Configuration
@RequiredArgsConstructor
public class AsyncConfig implements AsyncConfigurer {
	@Autowired
	private AppProperties prop;

	@Override
	public Executor getAsyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(prop.getCorePoolSize());
		executor.setMaxPoolSize(prop.getMaxPoolSize());
		executor.setWaitForTasksToCompleteOnShutdown(true);
		executor.setThreadNamePrefix(prop.getThreadPrefix());
		executor.initialize();
		return executor;
	}

	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		return (exception, method, params) -> log.error(
				"---->>>> Exception of TYPE: {} occured in method: {}, MESSAGE: {}, CAUSED BY: {}", exception.getCause(),
				method.getName(), exception.getMessage(), exception.getCause());
	}
}