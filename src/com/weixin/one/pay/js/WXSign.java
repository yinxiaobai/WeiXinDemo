package com.weixin.one.pay.js;

import java.util.Map;
import java.util.TreeMap;

import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.weixin.one.config.WeiConfig;
import com.weixin.one.pay.services.OrderServices;
import com.weixin.one.utils.EncryptUtil;
import com.weixin.one.utils.Tool;

/**
 * @date 2017年1月12日下午1:09:29
 * @author jq.yin@i-vpoints.com
 */
public class WXSign {

	private static final Logger log = LoggerFactory.getLogger(WXSign.class);

	/*
	 * fish+
	 * appid: wx17e761a386e1c903
	 * mchId:1432577602
	 * key: cb60803fe1e9cc56164684e040b2bbc0
	 * openid: o921QxGwyiXRxJ1SuTIktWnMitXk
	 */
	/*
	 * tjh
	 * appid: wx5482530469eb4bc2
	 * mchId:1413404502
	 * key: abcdefghijklmnopqrstuvwxyz123456
	 * openid:
	 */
	static String key = WeiConfig.get("fish.key");
	static String appid = WeiConfig.get("fish.appId");
	static String mchId = WeiConfig.get("fish.mch_id");

	/**
	 * 下单验签
	 * 
	 * @date 2017年1月16日下午1:14:39
	 * @return	含有加密字符串的请求参数集合
	 * @author jq.yin@i-vpoints.com
	 */
	public static Map<String, String> sign() {
		String body = "中信春节观影";								// 商品描述
		String notifyUrl = "http://fishplusdev.i-vpoints.com/WeiXinDemo/create/pay";	// 回调地址
		int totalFee = 1;										// 金额	单位:分
		String tradeType = "NATIVE";// "JSAPI";					// 支付方式
		String openid = "o921QxGwyiXRxJ1SuTIktWnMitXk";			// 支付方式为NATIVE(扫码支付)时必填
		String spbillCreateIp = "123.12.12.123";				// 终端IP XXX 
		String nonceStr = Tool.getNonceStr();					// 随机字符串
		String outTradeNo = System.currentTimeMillis() + "";
		// openid = "oE-EQxOiS_r_bI2mYxXBmiC39lPc";
		Map<String, String> signMap = new TreeMap<String, String>();
		signMap.put("appid", appid);
		signMap.put("mch_id", mchId);
		signMap.put("nonce_str", nonceStr);
		signMap.put("body", body);
		signMap.put("out_trade_no", outTradeNo);
		signMap.put("total_fee", totalFee + "");
		signMap.put("spbill_create_ip", spbillCreateIp);
		signMap.put("notify_url", notifyUrl);
		signMap.put("trade_type", tradeType);
		signMap.put("openid", openid);
		return getMd5(key, signMap);
	}

	/**
	 * 对微信支付请求参数进行加密
	 * 
	 * @date 2017年1月13日上午10:35:26
	 * @param key
	 *            商户密钥
	 * @param signMap
	 *            请求参数集合 Ascll排序
	 * @return 含有加密字符串的请求参数集合
	 * @author jq.yin@i-vpoints.com
	 */
	private static Map<String, String> getMd5(String key,
			Map<String, String> signMap) {
		log.info("【进行MD5加密】");
		StringBuilder sb = new StringBuilder();
		for (String keyMap : signMap.keySet()) {
			if (signMap.get(keyMap) != null
					&& !"".equals(signMap.get(keyMap))) {
				sb.append(keyMap).append("=").append(signMap.get(keyMap))
						.append("&");
			}
		}
		String param = sb.toString();
		if ("&".equals(param.substring(param.length() - 1))) {
			param = param.substring(0, param.length() - 1);
		}
		param += "&key=" + key;
		log.info("param:" + param);
		String sign = EncryptUtil.md5(param).toUpperCase();
		log.info("sign:" + sign);
		signMap.put("sign", sign);
		return signMap;
	}

	/**
	 * js页面验签
	 * 
	 * @date 2017年1月16日下午1:14:30
	 * @return	含有加密字符串的请求参数集合
	 * @author jq.yin@i-vpoints.com
	 */
	public static Map<String, String> getPaySign() {
		String prepay_id = "";
		try {
			prepay_id = OrderServices.createOrder().get("prepay_id");
		} catch (DocumentException e) {
			log.error(e.getMessage(),e);
		}

		String timeStamp = System.currentTimeMillis() + "";
		String nonceStr = Tool.getNonceStr();
		String package1 = "prepay_id=" + prepay_id;
		String signType = "MD5";

		// appId = "wx17e761a386e1c903";
		// nonceStr = "YATqa3l6WBMaDb4E";
		// package1 = "prepay_id=wx20170113162959e7f6b016260503267161";
		// timeStamp = "1484296195";

		Map<String, String> map = new TreeMap<String, String>();
		map.put("appId", appid);
		map.put("timeStamp", timeStamp);
		map.put("nonceStr", nonceStr);
		map.put("package", package1);
		map.put("signType", signType);
		return getMd5(key, map);
	}

	public static void main(String[] args) {
		// System.out.println(sign().get("sign"));
		// System.out.println(Tool.createNonceStr());
		System.out.println("paySign:" + getPaySign().get("sign"));
	}

}
