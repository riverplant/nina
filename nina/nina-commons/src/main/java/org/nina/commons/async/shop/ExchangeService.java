package org.nina.commons.async.shop;
/**
 * 汇率服务，与查询价格的服务并行执行
 * @author riverplant
 *
 */
public interface ExchangeService {

	public double getRate(String souce, String target,Shop shop);
}
