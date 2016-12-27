package com.weixin.one.services;

import java.io.PrintWriter;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.weixin.one.utils.MapApi;
import com.weixin.one.utils.MessageUtil;

/**
 * 微信端消息处理
 * 
 * @date 2016年12月27日下午4:54:01
 * @author hp
 * 
 */
public class MessageService {

	private static final Logger log = LoggerFactory
			.getLogger(MessageService.class);

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

		/*********************** 回复信息处理 ***********************/

		// 消息接收者openId
		String toUserName = map.get("FromUserName");
		// FIXME 公众号开发者(申请人)微信号,唯一
		String fromUserName = map.get("ToUserName");
		String content = "二姐不是最帅(此条五毛)【杜攀让改的】【杜攀说他是二狗子】";

		// 回复文本消息
		String xml = SendMessageService.sendText(toUserName, fromUserName,
				content);
		out.println(xml);
		out.flush();
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
		// 消息接收者openId
		String toUserName = map.get("FromUserName");
		// FIXME 公众号开发者(申请人)微信号,唯一
		String fromUserName = map.get("ToUserName");
		String recognition = map.get("Recognition");
		log.info("接收到" + MessageUtil.REQ_MESSAGE_TYPE_VOICE + "内容{}",
				recognition);

		String content;
		if ("".equals(recognition)) {
			content = "抱歉你发送的语音无法识别,	请勤加练习普通话";
		} else {
			content = "你发送的语音消息为:\"" + recognition
					+ "\"你的普通话很不标准;\n\n(二姐让我发的)二姐最帅[此条五毛]";
		}

		// 回复文本消息
		String xml = SendMessageService.sendText(toUserName, fromUserName,
				content);
		log.debug("回复文本消息:" + xml);
		out.print(xml);
		out.flush();
	}

	/**
	 * location消息处理
	 * 
	 * @date 2016年12月26日下午5:20:13
	 * @param map
	 * @param out
	 * @author jq.yin@i-vpoints.com
	 */
	public static void locationMessage(Map<String, String> map, PrintWriter out) {
		// 消息接收者openId
		String toUserName = map.get("FromUserName");
		// FIXME 公众号开发者(申请人)微信号,唯一
		String fromUserName = map.get("ToUserName");
		String locationX = map.get("Location_X");
		String locationY = map.get("Location_Y");
		// 获取天气信息
		Map<String, String> weatherMap = MapApi.xzWeather(locationX, locationY);
		log.info("所在位置纬度{},经度{}", locationX, locationY);
		String content = "你所在经度:" + locationY + " 纬度:" + locationX + ","
				+ "当前天气状况:" + weatherMap.get("temperature") + "C，"
				+ weatherMap.get("text") + "。。。。。。";

		// 发送文本消息
		String xml = SendMessageService.sendText(toUserName, fromUserName,
				content);

		out.print(xml);
		out.flush();
	}
}