package org.nina.commons.async.shop;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 模拟商品
 * 
 * @author riverplant
 *
 */
public class Shop {
	// 商店名称
	private String name;

	public Shop(String name) {
		this.name = name;
	}

	static Random random = new Random();

	/**
	 * 模拟远程调用
	 */
	public static void delay() {
		int delay = 500 + random.nextInt(2000);
		try {
			Thread.sleep(delay);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 模拟同步返回商品价格的服务
	 * 
	 * @param product
	 * @return
	 */
	public double getPrice(String product) {
		delay();
		// 返回0~100的随机数
		return random.nextDouble() * 100;
	}
	
	/**
	 * 模拟同步返回商品价格的服务
	 * 
	 * @param product
	 * @return
	 */
	public Quote getQuote(String product) {
		delay();
		double price = random.nextDouble() * 100;
		Discount discount = Discount.values()[random.nextInt(Discount.values().length)];
		return new Quote(getName(), price, discount);
	}
	
	/**
	 * 模拟同步返回商品价格的服务
	 * 
	 * @param product
	 * @return
	 */
	public String getPriceWithDiscount(String product) {
		delay();
		// 返回0~100的随机数
		double price = random.nextDouble() * 100;
		//随机返回一个折扣码(1~5)
		Discount discount =  Discount.values()[random.nextInt(Discount.values().length)];
		//返回店的名字:商品的价格:商品的折扣率
		return String.format("%s:%.2f:%s", getName(),price,discount);
	}

	/**
	 * 模拟异步返回商品价格的服务
	 * 
	 * @param product
	 * @return
	 */
	public Future<Double> getPriceAsync(String product) {
//		CompletableFuture<Double> futurePrice = new CompletableFuture<>();
//		new Thread(() -> futurePrice.complete(getPrice(product))).start();
//		return futurePrice;
		return CompletableFuture.supplyAsync(()->getPrice(product));
	}

	public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
		Shop shop = new Shop("bestShop");
		long start = System.currentTimeMillis();
		// 异步获取商店里某个商品的价格
		Future<Double> futurePrice = shop.getPriceAsync("some product");
		System.out.println("调用返回，耗时:" + (System.currentTimeMillis() - start));
		Double price = futurePrice.get(1, TimeUnit.SECONDS);
		System.out.println("价格返回，价格为:"+price+"耗时:" + (System.currentTimeMillis() - start));
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
