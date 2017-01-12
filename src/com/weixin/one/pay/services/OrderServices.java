package com.weixin.one.pay.services;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.weixin.one.pay.js.WXSign;
import com.weixin.one.utils.UrlUtils;

/**
 * @date 2017年1月12日下午3:33:14
 * @author jq.yin@i-vpoints.com
 */
public class OrderServices {

	private static final Logger log = LoggerFactory
			.getLogger(OrderServices.class);

	static String url = "http://wxgzh3.i-vpoints.com/WeiXinDemo/pay";
	public static String createOrder(Object obj) {
		String orderUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";
		Map<String,String> sign = WXSign.sign();
		log.info("sign:"+sign.toString());
		String param = "<xml>"
				+ "<appid>"+sign.get("appId")+"</appid>"
//				+ "<attach>"+sign.get("apppid")+"</attach>"
				+ "<body>"+sign.get("body")+"</body>"		// 商品描述
				+ "<mch_id>"+sign.get("mch_id")+"</mch_id>"		// 商户ID
//				+ "<detail>"
//					+ "<![CDATA[{\"goods_detail\":[{\"goods_id\":\"iphone6s_16G\",\"wxpay_goods_id\":\"1001\",\"goods_name\":\"iPhone6s16G\",\"quantity\":1,\"price\":528800,\"goods_category\":\"123456\",\"body\":\"苹果手机\"},{\"goods_id\":\"iphone6s_32G\",\"wxpay_goods_id\":\"1002\",\"goods_name\":\"iPhone6s32G\",\"quantity\":1,\"price\":608800,\"goods_category\":\"123789\",\"body\":\"苹果手机\"}]}]]></detail>"
				+ "<nonce_str>"+sign.get("nonce_str")+"</nonce_str>"
				+ "<notify_url>"+sign.get("notify_url")+"</notify_url>"		// 回调地址
				+ "<openid>"+sign.get("openid")+"</openid>"
				+ "<out_trade_no>"+sign.get("out_trade_no")+"</out_trade_no>"				// 订单号
				+ "<spbill_create_ip>"+sign.get("spbill_create_ip")+"</spbill_create_ip>"	// 用户端ip
				+ "<total_fee>"+sign.get("total_fee")+"</total_fee>"							// 订单金额 单位:分
				+ "<trade_type>"+sign.get("trade_type")+"</trade_type>"				// 交易类型
				+ "<sign>"+sign.get("sign")+"</sign>"
//				+ "<sign_type>"+sign.get("apppid")+"</sign_type>"					// 加密类型
				+ "</xml>";
		System.out.println(param);
		String a = UrlUtils.sendHttpPost(orderUrl, param);
		return a;
	}
	
	public static void main(String[] args) {
		System.out.println(createOrder(null));
	}
	
}