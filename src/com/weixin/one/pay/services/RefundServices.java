package com.weixin.one.pay.services;

import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.weixin.one.utils.Tool;
import com.weixin.one.utils.UrlUtils;

/**
 * @date 2017年3月7日上午9:35:35
 * @author jq.yin@i-vpoints.com
 */
public class RefundServices {
	
	private static final Logger log = LoggerFactory
			.getLogger(RefundServices.class);
	
	private static String transactionId = "4008562001201703072510261875"; 	// XXX	微信生成订单号		二选一
//	private static String outTradeNo = ""; // XXX		商户订单号		二选一
	
	/**
	 * 退款
	 * @date 2017年3月7日上午9:57:55
	 * @param key
	 * @param map
	 * @author jq.yin@i-vpoints.com
	 */
	public void refoud(String key,Map<String,String> map){
		Map<String,String> refoudMap = Tool.getMd5(key, map);
		log.info("退款详情:" + refoudMap.toString());
		String refoudXml = Tool.mapToXml(refoudMap);
		log.info(refoudXml);
		
		String refundURL = "https://api.mch.weixin.qq.com/secapi/pay/refund";
		String result = UrlUtils.sendPost(refundURL, refoudXml);
		log.info("退款结果:" + result);
	}

	public static void main(String[] args) {
		String key = "cb60803fe1e9cc56164684e040b2bbc0";//WeiConfig.get("fish.key");
		
		Map<String,String> map = new TreeMap<String,String>();
		String appId = "wx17e761a386e1c903";//WeiConfig.get("weixin.appid");
		String mchId = "1432577602";//WeiConfig.get("fish.mch_id");
		String nonceStr = Tool.getNonceStr();
		String outRefundNo = Tool.getUUID();
		String totalFee = "0.01";
		String refundFee = "0.01";
		String opUserId = "1432577602"; // 默认为商户号
		
		map.put("appid", appId);
		map.put("mch_id", mchId);
		map.put("nonce_str", nonceStr);
		map.put("transaction_id", transactionId);
//		map.put("out_trade_no", outTradeNo);
		map.put("out_refund_no", outRefundNo);
		map.put("out_refund_no", outRefundNo);
		map.put("total_fee", totalFee);
		map.put("refund_fee", refundFee);
		map.put("op_user_id", opUserId);
		
		new RefundServices().refoud(key, map);
	}
}
