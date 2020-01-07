package org.nina.commons.async.shop;

public class ExchangeServiceImpl implements ExchangeService {
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

	@Override
	public double getRate(String souce, String target) {
		// TODO Auto-generated method stub
		return 0;
	}
	/**
	 * 模拟返回汇率是10
	 * @param souce
	 * @param target
	 * @return
	 */
	public static double getRateStatic(String souce, String target) {
		delay();
		return 10;
	}
}
