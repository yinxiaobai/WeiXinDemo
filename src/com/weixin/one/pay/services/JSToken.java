package com.weixin.one.pay.services;

import com.alibaba.fastjson.JSONObject;
import com.weixin.one.services.AccessTokenService;
import com.weixin.one.utils.UrlUtils;

/**
 * @date 2017年1月13日上午10:18:10
 * @author jq.yin@i-vpoints.com
 */
public class JSToken {
	public static void main(String[] args) {
		// WeiConfig.init();
		String accessTonek = AccessTokenService.getInstance().getAccess_token();
		accessTonek = "B5N08e4iuKgblwWs8Ciz1VmYX8RUFUQn6Sq7Zxl6LWgHCFI8qsSbVvtqaXOVqDJxU8CafO-fOK7dYs2rqI57cGlbd8bG58R3J6bCO8WsGX4BzSXvWFk-ykedxOhFwXc7LJIgAAASQP";
		String url = "http://api.weixin.qq.com/cgi-bin/ticket/getticket?type=jsapi&access_token="+accessTonek+"";
		String jsToken = UrlUtils.sendGet(url);
		JSONObject jsonObject = (JSONObject) JSONObject.parse(jsToken);
		jsonObject.get("ticket");
		System.out.println(jsToken);
		System.out.println("ticket:"+jsonObject.get("ticket"));
	}
}
