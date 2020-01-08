package org.nina.commons.async.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 
 * @author riverplant
 *
 */
public class FutureDemo {

	public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {

		ExecutorService executorService = Executors.newCachedThreadPool();
		/**
		 * 在future中调用耗时服务，将main线程解放
		 */
		Future<String> future = (Future<String>) executorService.submit(FutureDemo::doSomeLongOperations);
		System.out.println(System.currentTimeMillis()+"xxx");
		String result = future.get(1,TimeUnit.SECONDS);
		/**
		 * 此处可以进行其它业务
		 */
		System.out.println(result);
		/**
		 * 1.8新引入的CompletableFuture
		 */
		
		
	}

	public static String doSomeLongOperations() throws InterruptedException {
       /**
        * 模拟调用远程服务一秒
        */
		Thread.sleep(1000);
		return "123";
	}
}
