package com.weixin.one.services;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.weixin.one.utils.MessageUtil;

/**
 * @date 2016年12月19日下午5:17:52
 * @author hp
 * 
 */
public class MessageServiceImpl /* implements MessageService */{

	private static final Logger log = LoggerFactory
			.getLogger(MessageServiceImpl.class);

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
				TextMessage(msg, out);
				break;
			case MessageUtil.REQ_MESSAGE_TYPE_IMAGE: // image,图片消息
				log.info("消息类型为{},等待开发", msg.get("MsgType"));
				break;
			case MessageUtil.REQ_MESSAGE_TYPE_VOICE: // voice,语音消息
				voiceMessage(msg, out);
				break;
			case MessageUtil.REQ_MESSAGE_TYPE_VIDEO: // video,视频消息
				log.info("消息类型为{},等待开发", msg.get("MsgType"));
				break;
			case MessageUtil.REQ_MESSAGE_TYPE_SHORTVIDEO: // shortvideovideo,小视频消息
				log.info("消息类型为{},等待开发", msg.get("MsgType"));
				break;
			case MessageUtil.REQ_MESSAGE_TYPE_LOCATION: // location,地理位置消息
				locationMessage(msg,out);
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
			out.flush();
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		} finally{
			out.close();
		}
	}

	/**
	 * text类型消息处理
	 * 
	 * @date 2016年12月20日上午10:33:57
	 * @param map
	 *            接收到的微信端text类型消息集合
	 * @return 需回复xml字符串
	 * @author jq.yin@i-vpoints.com
	 */
	public static void TextMessage(Map<String, String> map, PrintWriter out) {
		String msg = map.get("Content");
		log.info("接收到" + MessageUtil.REQ_MESSAGE_TYPE_TEXT + "消息:" + msg);

		// TODO
		/********************************************************/
		/*********************** 回复信息处理 ***********************/
		/********************************************************/

		String toUserName = map.get("FromUserName");
		String content = "二姐不是最帅(此条五毛)【杜攀让改的】【杜攀说他是二狗子】";

		// 回复文本消息
		String xml = SendMessageService.sendText(toUserName, content);
		// 回复消息
		log.debug("回复文本消息:" + xml);
		out.println(xml);
	}

	/**
	 * voice类型消息处理
	 * 
	 * @date 2016年12月26日下午3:19:19
	 * @param map
	 * @param out
	 * @author jq.yin@i-vpoints.com
	 */
	public static void voiceMessage(Map<String, String> map, PrintWriter out) {

		String toUserName = map.get("FromUserName");
		String recognition = map.get("Recognition");
		log.info("接收到"+MessageUtil.REQ_MESSAGE_TYPE_VOICE+"内容{}", recognition);

		String content;
		if ("".equals(recognition)) {
			content = "抱歉你发送的语音无法识别,	请勤加练习普通话";
		} else {
			content = "你发送的语音消息为:\"" + recognition
					+ "\"你的普通话很不标准;\n\n(二姐让我发的)二姐最帅[此条五毛]";
		}

		// 回复文本消息
		String xml = SendMessageService.sendText(toUserName, content);
		log.debug("回复文本消息:"+xml);
		out.print(xml);
	}

	/**
	 * location消息处理
	 * @date 2016年12月26日下午5:20:13
	 * @param map
	 * @param out
	 * @author jq.yin@i-vpoints.com
	 */
	public static void locationMessage(Map<String, String> map, PrintWriter out) {
		String toUserName = map.get("FromUserName");
		String locationX = map.get("Location_X");
		
		String locationY = map.get("Location_Y");
		log.info("所在位置纬度{},经度{}",locationX,locationY);
		String content = "你所在经度:"+locationY+" 纬度:"+locationX+",其余功能开发中...";
		//发送文本消息
		String xml = SendMessageService.sendText(toUserName, content);
		out.print(xml);
		out.flush();
	}
}