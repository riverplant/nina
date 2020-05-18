package org.nina.commons.enums;

/**
 * 支付方式
 * 
 * @author riverplant
 *
 */
public enum OrderStatusEnum {
	WAIT_PAY(10, "待付款"), 
	WAIT_DELIVER(20, "已付款，待发货"),
	WAIT_RECEIVE(30, "已发货，待收货"),
	SUCCESS(40, "交易成功"),
	CLOSE(50, "交易关闭");
	public final Integer trype;
	public final String value;

	private OrderStatusEnum(Integer trype, String value) {
		this.trype = trype;
		this.value = value;
	}

	public Integer getTrype() {
		return trype;
	}

	public String getValue() {
		return value;
	}
	public static OrderStatusEnum stateOf(int index) {
        for (OrderStatusEnum e : OrderStatusEnum.values()) {
            if (e.getTrype() == index) {
                return e;
            }
        }
        return null;
    }  
}
