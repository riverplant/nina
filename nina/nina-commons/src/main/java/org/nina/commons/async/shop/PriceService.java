package org.nina.commons.async.shop;

import java.util.List;

/**
 * 当shop只提供同步的价格方法， 需要使用PriceService来获取价格
 * 
 * @author riverplant
 *
 */
public interface PriceService {
	/**
     * 
     * @param product：接收用户传递的商品名称
     * @return: 返回给用户一组该商品相关的店铺信息及店铺中该商品的价格信息
     */
	public List<String> findPrices(String product);
	/**
	 * 将同步方法转成异步方法
	 * @param product
	 * @return
	 */
	public List<String> AsyncfindPrices(String product);
	/**
	 * 先通过商品获得各个店铺的商品价格，再通过商品价格获得折扣信息
	 * @param product
	 * @return
	 */
	public List<String> AsyncfindPricesAndDiscount(String product);
	
	/**
	 * 线程池大小配置算法
	 * N:处理器核数-->Runtime.getRuntime().availableProcessors()
	 * U:期望CPU利用率0~1
	 * W:等待时间
	 * C:计算时间
	 */
	//T = N * U *( 1 + W/C )
	
}
