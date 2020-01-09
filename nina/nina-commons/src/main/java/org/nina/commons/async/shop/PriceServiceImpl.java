package org.nina.commons.async.shop;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

/**
 * 当shop只提供同步的价格方法，
 * 需要使用PriceService来获取价格
 * @author riverplant
 *
 */
public class PriceServiceImpl implements PriceService{
	/**
	 * 模拟8个店
	 */
	private List<Shop> shops = Arrays.asList(
			new Shop("shop1"),
			new Shop("shop2"),
			new Shop("shop3"),
			new Shop("shop4"),
			new Shop("shop5"),
			new Shop("shop6"),
			new Shop("shop7"),
			new Shop("shop8")
			);
	/**
     * 调用shop提供的getPrice的同步方法
     * @param product：接收用户传递的商品名称
     * @return: 返回给用户一组该商品相关的店铺信息及店铺中该商品的价格信息
     */
	@Override
	public List<String> findPrices(String product) {	
		/**
		 * 直接使用同步方法。需要8秒
		 */
		/** 
		 * shops.stream()
				.map(shop->String.format("%s price is %.2f",shop.getName(),shop.getPrice(product) ))
				.collect(Collectors.toList());
				*/
		
		/**
		 * 第一次改进:使用并行流，时间1秒
		 * 效率大小与CPU核数相关，因为该方法没有办法配置线程池，所以
		 * 只适用于计算数量的任务需求
		 */
		return shops.stream().parallel()
				.map(shop->String.format("%s price is %.2f",shop.getName(),shop.getPrice(product) ))
				.collect(Collectors.toList());	
	}
    
	public static void main(String[] args) {
		PriceServiceImpl pservice = new PriceServiceImpl();
		long start = System.currentTimeMillis();
		System.out.println(pservice.findPrices("iPhone11"));
		System.out.println("服务耗时:"+(System.currentTimeMillis() - start));
	}
    /**
     * 耗时2秒，使用CompletableFuture可以自行配置线程池
     * 所以比较适合多线程调取远程服务任务
     */
	@Override
	public List<String> AsyncfindPrices(String product) {
		ExecutorService threadPool = Executors.newFixedThreadPool(Math.min(shops.size(),100));
		List<CompletableFuture<String>> futurePrices =shops.stream()
				.map(shop -> CompletableFuture
						.supplyAsync(()->String.format("%s price is %.2f",shop.getName(),shop.getPrice(product)),threadPool))
				.collect(Collectors.toList());
		 
		return  futurePrices.stream().map(CompletableFuture::join).collect(Collectors.toList());
	}

	@Override
	public List<String> AsyncfindPricesAndDiscount(String product) {
		ExecutorService threadPool = Executors.newFixedThreadPool(Math.min(shops.size(),100));
		List<CompletableFuture<String>> futurePrices =shops.stream()
				//先通过CompletableFuture.supplyAsync异步调用获得价格的方法,获得字符串
				.map(shop -> CompletableFuture.supplyAsync(()->shop.getQuote(product),threadPool))
				//使用future.thenApply连接一个同步方法,将字符串转化成Quote
				//.map(future -> future.thenApply(Quote::parse))
				//thenCompose:连接另外一个异步操作,当第一个完成返回后执行第二个异步
				.map(future->future.thenCompose(quote->CompletableFuture.supplyAsync(()->DiscountServiceImpl.applyDiscountStatic(quote),threadPool)))
				.collect(Collectors.toList());
		return futurePrices.stream().map(CompletableFuture::join).collect(Collectors.toList());
	}
	/**
	 * 添加价格单位转化，在查询价格的同时将欧元转成人民币
	 * 同时发出两个请求，一个异步查汇率，一个异步查价格，
	 * 当请求返回价格和折扣进行计算后，再与汇率进行计算最终价格返回给用户
	 * @param product
	 * @return
	 */
	public List<String> AsyncfindPricesAndDiscountAndExchange(String product) {
		ExecutorService threadPool = Executors.newFixedThreadPool(100);
		List<CompletableFuture<String>> futurePrices =shops.stream()
				//先通过CompletableFuture.supplyAsync异步调用获得价格的方法,获得字符串
				.map(shop -> CompletableFuture.supplyAsync(()->shop.getQuote(product),threadPool)
						/**
						 * 通过CompletableFuture.thenCombine( CompletionStage<? extends U> other, BiFunction<? super T,? super U,? extends V> fn)
						 * 将两个异步方法同时调用，第一个参数是第二个异步方法的调用CompletableFuture.supplyAsync
						 * 第二个参数是将两个异步方法的返回值合并成一个quote
						 */
						.thenCombine(CompletableFuture.supplyAsync(()->ExchangeServiceImpl.getRateStatic("EURO", "CN",shop),threadPool), 
								(quote,rate)->new Quote(quote.getSHOP(),quote.getPrice()*rate,quote.getDiscount())))
				//thenCompose:连接另外一个异步操作,当第一个完成返回后执行第二个异步
				.map(future->future.thenCompose(quote->CompletableFuture.supplyAsync(()->DiscountServiceImpl.applyDiscountStatic(quote),threadPool)))
				.collect(Collectors.toList());
		return futurePrices.stream().map(CompletableFuture::join).collect(Collectors.toList());
	}
	
	
	/**
	 * 添加价格单位转化，在查询价格的同时将欧元转成人民币
	 * 同时发出两个请求，一个异步查汇率，一个异步查价格，
	 * 当请求返回价格和折扣进行计算后，再与汇率进行计算最终价格返回给用户
	 * 不等待所有的结果，根据不同的服务延时时间每得到一条消息就返回一条
	 * future.thenAccept!!!!!!!!!!!!!
	 * @param product
	 * @return
	 */
	public void AsyncfindPricesAndDiscountAndExchange2WithDelay(String product) {
		long start = System.currentTimeMillis();
		ExecutorService threadPool = Executors.newFixedThreadPool(100);
		//返回CompletableFuture数组
		CompletableFuture<?>[] futurePrices =shops.stream()
				//先通过CompletableFuture.supplyAsync异步调用获得价格的方法,获得字符串
				.map(shop -> CompletableFuture.supplyAsync(
						()->shop.getQuote(product),threadPool)
						/**
						 * 通过CompletableFuture.thenCombine( CompletionStage<? extends U> other, BiFunction<? super T,? super U,? extends V> fn)
						 * 将两个异步方法同时调用，第一个参数是第二个异步方法的调用CompletableFuture.supplyAsync
						 * 第二个参数是将两个异步方法的返回值合并成一个quote
						 */
						.thenCombine(CompletableFuture.supplyAsync(()->ExchangeServiceImpl.getRateStatic("EURO", "CN",shop),threadPool), 
								(quote,rate)->new Quote(quote.getSHOP(),quote.getPrice()*rate,quote.getDiscount()))
						)//map
				//thenCompose:连接另外一个异步操作,当第一个完成返回后执行第二个异步
				.map(future->future.thenCompose(quote->CompletableFuture.supplyAsync(()->DiscountServiceImpl.applyDiscountStatic(quote),threadPool)))
				//future.thenAccept:最终处理CompletableFuture的返回结果,只要有返回就立刻处理
				.map(future->future.thenAccept(content->System.out.println("执行"+content+"使用了"+(System.currentTimeMillis()-start)+"毫秒")))			
				.toArray(size->new CompletableFuture[size]);
		//使用CompletableFuture.allOf实现当所有的服务都处理完后进行的最后的操作
		CompletableFuture.allOf(futurePrices).thenAccept((obj)->System.out.println("all done"));
		//使用CompletableFuture.anyOf实现只要有一个服务返回就处理并且结束
		CompletableFuture.anyOf(futurePrices).thenAccept((obj)->System.out.println("fastest done"));	
	}
}
