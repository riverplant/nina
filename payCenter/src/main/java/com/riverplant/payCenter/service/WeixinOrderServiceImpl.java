package com.riverplant.payCenter.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.riverplant.payCenter.config.WeixinPayConfig;
import com.riverplant.payCenter.domain.PayResult;
import com.riverplant.payCenter.domain.PreOrder;
import com.riverplant.payCenter.domain.PreOrderResult;
import com.riverplant.payCenter.utils.HttpUtil;
import com.riverplant.payCenter.utils.Sign;
import com.riverplant.payCenter.utils.XmlUtil;

@Transactional
@Service
public class WeixinOrderServiceImpl implements WeixinOrderService {
    @Autowired private WeixinPayConfig wxConfig;

	@Override
	public PreOrderResult placeOrder(String body,String out_trade_no, String total_fee) throws Exception {
		//生成传给微信支付系统的预付单对象
		PreOrder o = new PreOrder();
		//生成随机字符串
		String nonce_str = UUID.randomUUID().toString();
		o.setAppid(wxConfig.getAppId());
		o.setBody(body);
		o.setMch_id(wxConfig.getMerchantId());
		o.setNotify_url(wxConfig.getNotifyUrl());
		o.setOut_trade_no(out_trade_no);
		//判断有没有输入订单总金额，如果没有默认1分钱
		if(total_fee !=null && !total_fee.equals("")) {
			o.setTotal_fee(Integer.parseInt(total_fee));
		} else {
			o.setTotal_fee(1);
		}
		o.setNonce_str(nonce_str);
		o.setTrade_type(wxConfig.getTradType());
		o.setSpbill_create_ip(wxConfig.getSpbillCreateIp());
		SortedMap<Object,Object> p = new TreeMap<>();
		p.put("appid", wxConfig.getAppId());
		p.put("mch_id", wxConfig.getMerchantId());
		p.put("body", body);
		p.put("nonce_str", nonce_str);
		p.put("out_trade_no", out_trade_no);
		p.put("total_fee", total_fee);
		p.put("spbill_create_ip", wxConfig.getSpbillCreateIp());
		p.put("notify_url", wxConfig.getNotifyUrl());
		p.put("trade_type", wxConfig.getTradType());
		//获得签名
		String sign = Sign.createSign("utf-8",p,wxConfig.getSecrectKey());
		o.setSign(sign);
		//Obejct 转XML
		String xml = XmlUtil.object2Xml(o,PreOrder.class);
		//统一下单地址
		String url = wxConfig.getPlaceOrderUrl();
		//调用微信统一下单地址,获得用来验证用户身份信息的二维码信息
		String returnXml = HttpUtil.sendPost(url, xml);
		//XML转换成Object
		PreOrderResult preOrderResult = (PreOrderResult) XmlUtil.xml2Object(returnXml, PreOrderResult.class);
		return preOrderResult;
	}

	@Override
	public PayResult getWxPayResult(InputStream inStream) {
		try(BufferedReader in = new BufferedReader(
				new InputStreamReader(inStream))) {
			String result = "";
			String line;
			while((line = in.readLine()) != null) {
				result += line;
			}
			PayResult pr = (PayResult) XmlUtil.xml2Object(result, PayResult.class);
			return pr;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

}
