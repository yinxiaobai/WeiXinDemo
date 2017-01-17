package com.weixin.one.pay.services;

import java.util.Map;

import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.weixin.one.pay.js.WXSign;
import com.weixin.one.utils.Tool;
import com.weixin.one.utils.UrlUtils;

/**
 * @date 2017年1月12日下午3:33:14
 * @author jq.yin@i-vpoints.com
 */
public class OrderServices {

	private static final Logger log = LoggerFactory
			.getLogger(OrderServices.class);

	// 微信统一下单接口
	static String orderUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";

	/**
	 * 统一下单
	 * @date 2017年1月16日下午2:27:35
	 * @return
	 * @throws DocumentException
	 * @author jq.yin@i-vpoints.com
	 */
	public static Map<String, String> createOrder() throws DocumentException {
		Map<String, String> sign = WXSign.sign();
		StringBuffer xmlParam = new StringBuffer();
		// 拼接xml信息
		xmlParam.append("<xml>");
		for(String key : sign.keySet()){
			xmlParam.append("<"+key+">").append(sign.get(key)).append("</"+key+">");
		}
		xmlParam.append("</xml>");
		String param = xmlParam.toString();
		String result = UrlUtils.sendHttpPost(orderUrl, param);
		if(result != null && !"".equals(result)){
			Map<String, String> reqMap = Tool.xmlToMap(result);
			log.info("下单返回信息:" + reqMap.toString());
			if ("SUCCESS".equals(reqMap.get("return_code"))) {
				log.info("【下单成功】");
				log.info("prepay_id:" + reqMap.get("prepay_id"));
				if ("NATIVE".equals(sign.get("trade_type"))) {
					log.info("扫码支付:" + reqMap.get("code_url"));
				}
			} else {
				log.info("【下单失败】");
			}
			return reqMap;
		}else{
			log.error("【!!!】");
			return null;
		}
	}

	public static void main(String[] args) throws DocumentException {
		Map<String, String> reqMap = createOrder();
		System.out.println("返回结果:" + reqMap);
		System.out.println("code_url:" + reqMap.get("code_url"));
		System.out.println("prepay_id:" + reqMap.get("prepay_id"));
	}

}