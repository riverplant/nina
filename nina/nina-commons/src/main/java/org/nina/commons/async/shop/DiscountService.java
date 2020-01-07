package org.nina.commons.async.shop;
/**
 * 模拟折扣服务
 * @author riverplant
 *
 */
public interface DiscountService {

  public String apply(Double price,Discount discount );
  
  public String applyDiscount(Quote quote);
}
