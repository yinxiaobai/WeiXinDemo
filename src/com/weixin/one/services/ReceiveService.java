package com.weixin.one.services;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.weixin.one.utils.MessageUtil;

/**
 * 接收处理微信端消息
 * 
 * @date 2016年12月19日下午5:17:52
 * @author hp
 * 
 */
public class ReceiveService {

	private static final Logger log = LoggerFactory
			.getLogger(ReceiveService.class);

	static PrintWriter out = null;

	/**
	 * 微信端消息接收处理
	 * 
	 * @date 2016年12月26日下午4:03:28
	 * @param msg
	 *            收到个消息信息
	 * @param response
	 * @author jq.yin@i-vpoints.com
	 */
	public static void getMessage(Map<String, String> msg,
			HttpServletResponse response) {
		String msgType = msg.get("MsgType");

		response.setCharacterEncoding("utf-8");

		try {
			out = response.getWriter();
			// 消息处理
			switch (msgType) {
			case MessageUtil.REQ_MESSAGE_TYPE_TEXT: // text,文本消息
				MessageService.TextMessage(msg, out);
				break;
			case MessageUtil.REQ_MESSAGE_TYPE_IMAGE: // image,图片消息
				log.info("消息类型为{},等待开发", msg.get("MsgType"));
				break;
			case MessageUtil.REQ_MESSAGE_TYPE_VOICE: // voice,语音消息
				MessageService.voiceMessage(msg, out);
				break;
			case MessageUtil.REQ_MESSAGE_TYPE_VIDEO: // video,视频消息
				log.info("消息类型为{},等待开发", msg.get("MsgType"));
				break;
			case MessageUtil.REQ_MESSAGE_TYPE_SHORTVIDEO: // shortvideovideo,小视频消息
				log.info("消息类型为{},等待开发", msg.get("MsgType"));
				break;
			case MessageUtil.REQ_MESSAGE_TYPE_LOCATION: // location,地理位置消息
				MessageService.locationMessage(msg, out);
				log.info("消息类型为{},等待开发", msg.get("MsgType"));
				break;
			case MessageUtil.REQ_MESSAGE_TYPE_LINK: // link,链接消息
				log.info("消息类型为{},等待开发", msg.get("MsgType"));
				break;
			case MessageUtil.REQ_MESSAGE_TYPE_EVENT: // event,事件
				log.info("消息类型为{},等待开发", msg.get("MsgType"));
				break;
			case MessageUtil.EVENT_TYPE_SUBSCRIBE: // subscribe,订阅事件
				log.info("消息类型为{},等待开发", msg.get("MsgType"));
				break;
			case MessageUtil.EVENT_TYPE_UNSUBSCRIBE: // unsubscribe,取消订阅事件
				log.info("消息类型为{},等待开发", msg.get("MsgType"));
				break;
			case MessageUtil.EVENT_TYPE_CLICK:
				log.info("消息类型为{},等待开发", msg.get("MsgType"));
				break;
			}
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		} finally {
			out.flush();
			out.close();
		}
	}
}