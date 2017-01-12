package com.weixin.one.services.message;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.weixin.one.utils.MessageUtil;

/**
 * 消息回复
 * 
 * @date 2016年12月26日下午1:04:02
 * @author hp
 * 
 */
public class SendMessageService {

	private static final Logger log = LoggerFactory
			.getLogger(SendMessageService.class);

	/**
	 * 回复文本信息
	 * 
	 * @date 2016年12月26日下午2:10:37
	 * @param openId
	 *            消息接收者openId
	 * @param content
	 *            回复文本内容
	 * @author jq.yin@i-vpoints.com
	 */
	public static String sendText(String openId, String fromUserName,
			String content) {
		String toUserName = openId;
		// String fromUserName = WeiConfig.OPENID; FIXME
		String msgType = MessageUtil.REQ_MESSAGE_TYPE_TEXT;
		String createTime = new Date().getTime() + "";

		StringBuffer xml = new StringBuffer();

		// FIXME 手动拼接回复xml,待修改
		xml.append("<xml><ToUserName><![CDATA[").append(toUserName)
				.append("]]></ToUserName>").append("<FromUserName><![CDATA[")
				.append(fromUserName).append("]]></FromUserName>")
				.append("<CreateTime>").append(createTime)
				.append("</CreateTime>").append("<MsgType><![CDATA[")
				.append(msgType).append("]]></MsgType>")
				.append("<Content><![CDATA[").append(content)
				.append("]]></Content>").append("</xml>");
		// 回复消息
		log.debug("回复文本消息:" + xml);
		return xml.toString();
	}
	
	public static String sendImage (String openId, String fromUserName,
			String mediaId){
		String toUserName = openId;
		// String fromUserName = WeiConfig.OPENID; FIXME
		String msgType = MessageUtil.REQ_MESSAGE_TYPE_IMAGE;
		String createTime = new Date().getTime() + "";

		StringBuffer xml = new StringBuffer();

		// FIXME 手动拼接回复xml,待修改
		xml.append("<xml><ToUserName><![CDATA[").append(toUserName)
				.append("]]></ToUserName>").append("<FromUserName><![CDATA[")
				.append(fromUserName).append("]]></FromUserName>")
				.append("<CreateTime>").append(createTime)
				.append("</CreateTime>").append("<MsgType><![CDATA[")
				.append(msgType).append("]]></MsgType>")
				.append("<Content><![CDATA[").append(mediaId)
				.append("]]></Content>").append("</xml>");
		// 回复消息
		log.debug("回复图片消息:" + xml);
		return xml.toString();
	}
}