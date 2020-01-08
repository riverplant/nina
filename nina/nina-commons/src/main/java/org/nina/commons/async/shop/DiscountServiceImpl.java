package org.nina.commons.async.shop;

import java.text.NumberFormat;

/**
 * 
 * @author riverplant
 *
 */
public class DiscountServiceImpl implements DiscountService {
	/**
	 * 模拟远程调用
	 */
	public static void delay() {
		try {
			Thread.sleep(1000L);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
    /**
     * 打折服务：获得折扣数
     */
	@Override
	public String apply(Double price, Discount discount) {
		delay();
		return NumberFormat.getInstance().format(price *(100 - discount.getPercent())/100 );
	}
	
	public static String applyStatic(Double price, Discount discount) {
		delay();
		return NumberFormat.getInstance().format(price *(100 - discount.getPercent())/100 );
	}
    /**
     * 打印报价信息
     */
	@Override
	public String applyDiscount(Quote quote) {
		return quote.getSHOP()+"price is "+apply(quote.getPrice(),quote.getDiscount());
	}
	
	public static String applyDiscountStatic(Quote quote) {
		return quote.getSHOP()+"price is "+applyStatic(quote.getPrice(),quote.getDiscount());
	}
	
}
