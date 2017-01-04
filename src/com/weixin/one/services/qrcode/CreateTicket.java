package com.weixin.one.services.qrcode;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.weixin.one.config.WeiConfig;
import com.weixin.one.services.AccessTokenService;
import com.weixin.one.utils.Tool;

/**
 * @date 2017年1月3日下午3:56:24
 * @author jq.yin@i-vpoints.com
 */
public class CreateTicket {
	
	private static final Logger log = LoggerFactory
			.getLogger(CreateTicket.class);
	
	/**
	 * 创建生成二维码所需tocket
	 * @date 2017年1月3日下午5:23:38
	 * @param map
	 * @author jq.yin@i-vpoints.com
	 */
	public static void createTicket(Map<String,String> map){
		String url = WeiConfig.get("create_ticket.url") + AccessTokenService.getAccess_token();
		String action_name = map.get("action_name");
		String param = null;
		JSONObject jsonObject = new JSONObject();
		switch (action_name){
		case "QR_SCENE" :	//临时二维码
		case "QR_LIMIT_SCENE":	//永久二维码
			jsonObject.put("expire_seconds", map.get("expire_seconds"));
			jsonObject.put("action_name",map.get("action_name"));
			JSONObject jsonObject2 = new JSONObject();
			jsonObject2.put("scene_id", map.get("scene_id"));
			JSONObject jsonObject3 = new JSONObject();
			jsonObject3.put("scene", jsonObject2);
			jsonObject.put("action_info", jsonObject3);
			param = jsonObject.toJSONString();
			break;
		case "QR_LIMIT_STR_SCENE":	//永久的字符串参数值
			jsonObject.put("expire_seconds", map.get("expire_seconds"));
			jsonObject.put("action_name",map.get("action_name"));
			JSONObject jsonObject21 = new JSONObject();
			jsonObject21.put("scene_id", map.get("scene_str"));
			JSONObject jsonObject32 = new JSONObject();
			jsonObject32.put("scene", jsonObject21);
			jsonObject.put("action_info", jsonObject32);
			param = jsonObject.toJSONString();
			break;
		}
		log.info("param:{}",param);
		String result = Tool.sendPost(url, param);
		
		log.info(result);
		
	}
}