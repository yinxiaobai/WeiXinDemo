package com.weixin.one.pay.js;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import com.weixin.one.utils.Tool;

/**
 * @date 2017年1月12日下午1:09:29
 * @author jq.yin@i-vpoints.com
 */
public class WXSign {

    public static Map<String,String> sign() {
    	String appId = "wx5482530469eb4bc2";
    	String mchId = "1413404502";
    	String nonceStr = Tool.createNonceStr();
    	nonceStr = "asdacsdfsfsdf";
    	String body = "中信春节观影";
    	String outTradeNo = System.currentTimeMillis() + "";
    	int totalFee = 1;
    	String spbillCreateIp = "123.12.12.123";
    	String notifyUrl = "http://wxgzh3.i-vpoints.com/WeiXinDemo/pay";
    	String tradeType  = "JSAPI";
    	String openid = "o921QxGwyiXRxJ1SuTIktWnMitXk";
    	String key = "abcdefghijklmnopqrstuvwxyz123456";
    	Map<String,String> signMap = new TreeMap<String,String>();
    	signMap.put("appId", appId);
    	signMap.put("mch_id", mchId);
    	signMap.put("nonce_str", nonceStr);
    	signMap.put("body", body);
    	signMap.put("out_trade_no", outTradeNo);
    	signMap.put("total_fee", totalFee + "");
    	signMap.put("spbill_create_ip", spbillCreateIp);
    	signMap.put("notify_url", notifyUrl);
    	signMap.put("trade_type", tradeType);
    	signMap.put("openid", openid);
    	signMap.put("key", key);
    	StringBuilder sb = new StringBuilder();
    	for(String keyMap : signMap.keySet()){
    		sb.append(keyMap).append("=").append(signMap.get(keyMap)).append("&");
    	}
    	String param = sb.toString();
    	if("&".equals(param.substring(param.length()-1))){
    		param = param.substring(0, param.length()-1);
    	}
    	System.out.println(param);
    	String sign = Tool.md5(param).toUpperCase();
    	
    	signMap.put("sign", sign);
		return signMap;
    }
    
    public static void main(String[] args) {
		// System.out.println(sign().get("sign"));
    	System.out.println(Tool.createNonceStr());
	}
    
}
