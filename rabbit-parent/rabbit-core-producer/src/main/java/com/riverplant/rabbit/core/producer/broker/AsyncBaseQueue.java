package com.riverplant.rabbit.core.producer.broker;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 异步队列线程池
 * 
 * @author riverplant
 *
 */
public class AsyncBaseQueue {
	private static final Logger log = LoggerFactory.getLogger(AsyncBaseQueue.class);

	private static final int THREAD_SIZE = Runtime.getRuntime().availableProcessors();

	private static final int QUEUE_SIZE = 1000;

	private static ExecutorService senderAsync = 
			new ThreadPoolExecutor(
					THREAD_SIZE, 
					THREAD_SIZE * 2, 
					60L,
			        TimeUnit.SECONDS, 
			        new ArrayBlockingQueue<>(QUEUE_SIZE), 
			        new ThreadFactory() {
				@Override
				public Thread newThread(Runnable r) {
					Thread t = new Thread(r);
					t.setName("rabbitmq_client_async_sender");
					return t;
				}
			}, new RejectedExecutionHandler() {// 拒绝策略
				@Override
				public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
					log.error("async send is error rejected, runnable: {}, executor: {}", r, executor);
				}
			});

	public static void submit(Runnable runnable) {
		senderAsync.submit(runnable);
	}
}
