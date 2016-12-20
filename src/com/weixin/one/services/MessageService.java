package com.weixin.one.services;

import java.util.Date;
import java.util.Map;

/**
 * @date 2016年12月19日下午5:17:52
 * @author hp
 *
 */
public class MessageService {
	
	/**
	 * text类型消息处理
	 * @date 2016年12月20日上午10:33:57
	 * @param map	接收到的微信端text类型消息集合
	 * @return	需回复xml字符串
	 * @author jq.yin@i-vpoints.com
	 */
	public static String TextMessage(Map<String,String> map){
		
		String FromUserName = map.get("ToUserName");
		String ToUserName = map.get("FromUserName");
		String MsgType = map.get("MsgType");
		String CreateTime = new Date().getTime() + "";
		String Content = "呵呵";
		
		StringBuffer xml = new StringBuffer();
		
		// FIXME 手动拼接xml,待修改
		xml.append("<xml><ToUserName><![CDATA[").append(ToUserName).append("]]></ToUserName>")
		.append("<FromUserName><![CDATA[").append(FromUserName).append("]]></FromUserName>")
		.append("<CreateTime>").append(CreateTime).append("</CreateTime>")
		.append("<MsgType><![CDATA[").append(MsgType).append("]]></MsgType>")
		.append("<Content><![CDATA[").append(Content).append("]]></Content>")
		.append("</xml>");
		
		return xml.toString();
	}
}