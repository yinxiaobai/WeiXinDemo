package com.weixin.one.pay.js;

import java.util.Map;
import java.util.TreeMap;

import org.dom4j.DocumentException;

import com.weixin.one.pay.services.OrderServices;
import com.weixin.one.utils.Tool;

/**
 * @date 2017年1月12日下午1:09:29
 * @author jq.yin@i-vpoints.com
 */
public class WXSign {
	/*
	 * fish+ appid: wx17e761a386e1c903 
	 * mchId:1432577602	
	 * key:	cb60803fe1e9cc56164684e040b2bbc0
	 * openid: o921QxGwyiXRxJ1SuTIktWnMitXk
	 */
	/*
	 * tjh+ appid: wx5482530469eb4bc2 
	 * mchId:1413404502	
	 * key: abcdefghijklmnopqrstuvwxyz123456 
	 * openid:
	 */
	static String key = "cb60803fe1e9cc56164684e040b2bbc0";
	static String appid = "wx17e761a386e1c903";
	static String mchId = "1432577602";
    public static Map<String,String> sign() {
    	String appId = appid;
    	String nonceStr = "asdacsd33fsfsdf";
    	String body = "中信春节观影";
    	String outTradeNo = System.currentTimeMillis() + "";
    	// outTradeNo = "1478523690";
    	int totalFee = 1;
    	String spbillCreateIp = "123.12.12.123";
    	String notifyUrl = "http://fishplusdev.i-vpoints.com/WeiXinDemo/pay";
    	String tradeType  = "JSAPI";//"NATIVE";//"JSAPI";
    	String openid = "o921QxGwyiXRxJ1SuTIktWnMitXk";
    	Map<String,String> signMap = new TreeMap<String,String>();
    	signMap.put("appid", appId);
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
	 * @date 2017年1月13日上午10:35:26
	 * @param key
	 * @param signMap
	 * @return
	 * @author jq.yin@i-vpoints.com
	 */
	private static Map<String, String> getMd5(String key,
			Map<String, String> signMap) {
		StringBuilder sb = new StringBuilder();
    	for(String keyMap : signMap.keySet()){
    		sb.append(keyMap).append("=").append(signMap.get(keyMap)).append("&");
    	}
    	String param = sb.toString();
    	if("&".equals(param.substring(param.length()-1))){
    		param = param.substring(0, param.length()-1);
    	}
    	param += "&key="+key;
    	System.out.println(param);
    	String sign = Tool.md5(param).toUpperCase();
    	System.out.println("sign:"+sign);
    	signMap.put("sign", sign);
		return signMap;
	}
    
    public static Map<String, String> getPaySign(){
    	String prepay_id = "";
		try {
			prepay_id = OrderServices.createOrder().get("prepay_id");
		} catch (DocumentException e) {
			e.printStackTrace();
		}
    	
    	String appId = appid;
    	String timeStamp = "1478852399";
    	String nonceStr = "6fd4632f-b12c-4593-be6e-b6bcd1eedbca";
    	String package1 = "prepay_id="+prepay_id;
    	String signType = "MD5";
    	
//    	appId = "wx17e761a386e1c903";
//    	nonceStr = "YATqa3l6WBMaDb4E";
//    	package1 = "prepay_id=wx20170113162959e7f6b016260503267161";
//    	timeStamp = "1484296195";
    	
    	Map<String,String> map = new TreeMap<String,String>();
    	map.put("appId", appId);
    	map.put("timeStamp", timeStamp);
    	map.put("nonceStr", nonceStr);
    	map.put("package", package1);
    	map.put("signType", signType);
    	return getMd5(key, map);
    }
    
    public static void main(String[] args) {
		// System.out.println(sign().get("sign"));
//    	System.out.println(Tool.createNonceStr());
    	System.out.println("paySign:"+getPaySign().get("sign"));
	}
    
}
