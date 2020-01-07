package org.nina.commons.async.shop;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * 
 * @author riverplant
 * 1.8新引入的CompletableFuture
 */
public class CompetableFutureDemo {

	public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {

		
		
	}

	public static String doSomeLongOperations() throws InterruptedException {
       /**
        * 模拟调用远程服务一秒
        */
		Thread.sleep(1000);
		return "123";
	}
}
