package com.weixin.one.services;

import java.io.PrintWriter;


import java.util.Map;

/**
 * @date 2016年12月19日下午5:17:52
 * @author hp
 *
 */
public interface MessageService {
	
	/**
	 * text类型消息处理
	 * @date 2016年12月20日上午10:33:57
	 * @param map	接收到的微信端text类型消息集合
	 * @return	需回复xml字符串
	 * @author jq.yin@i-vpoints.com
	 */
	public String TextMessage(Map<String,String> map,PrintWriter out);
	
	
}