package com.weixin.one.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.weixin.one.services.MessageService;
import com.weixin.one.utils.MessageUtil;
import com.weixin.one.utils.Tool;

/**
 * Token验证类
 * 
 * @date 2016年12月19日下午2:17:18
 * @author hp
 * 
 */
@Controller
@RequestMapping("/Wei")
public class Wei {

	private static final Logger log = LoggerFactory.getLogger(Wei.class);

	private static final String token = "ae8pvIDE8NzmMEcHvpcJPs7r";

	@RequestMapping("/core")
	public void oneTest(HttpServletRequest request, HttpServletResponse response) {

		log.info("start...");
		
		PrintWriter out = null;
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			out = response.getWriter();
		} catch (IOException e) {
			log.info(e.getMessage(),e);
		}

		String signature = request.getParameter("signature") == null ? ""
				: request.getParameter("signature");
		String timestamp = request.getParameter("timestamp") == null ? ""
				: request.getParameter("timestamp");
		String nonce = request.getParameter("nonce") == null ? "" : request
				.getParameter("nonce");
		String echostr = request.getParameter("echostr") == null ? "" : request
				.getParameter("echostr");
		//请求方式
		String method = request.getMethod();
		// token验证
		//if (!"".equals(signature)) {
		if("GET".equalsIgnoreCase(method)){ //GET
			String[] arr = new String[] { token, timestamp, nonce };
			Arrays.sort(arr);
			String str = Tool.SHA1(arr[0] + arr[1] + arr[2]);
			if (!str.equals(signature)) {
				log.info("【TOKEN验证失败】");
				return;
			}
			log.info("【TOKEN验证成功】");
			//将echostr返回给微信
			out.println(echostr);
			out.flush();
		}else{
			//接收微信消息
			Map<String, String> map = Tool.xmlToMap(request);
			
			log.info("收到微信端消息:"+map.toString());
			
			String MsgType = map.get("MsgType");
			switch (MsgType) {
			case MessageUtil.REQ_MESSAGE_TYPE_TEXT:			//text,文本消息
				String msg = map.get("Content");
				log.info("接收到类型为" + MessageUtil.REQ_MESSAGE_TYPE_TEXT + "的消息:"
						+ msg);
				//TODO
				/********************************************************/
				/***********************回复信息处理***********************/
				/********************************************************/
				
				//回复信息
				String xml = MessageService.TextMessage(map);
				
				//log.info(Tool.MaptoXml(map));
				log.info("回复消息:"+xml);
				out.println(xml);
				out.flush();
				break;
			case MessageUtil.REQ_MESSAGE_TYPE_IMAGE:		//image,图片消息
				break;
			case MessageUtil.REQ_MESSAGE_TYPE_VOICE:		//voice,语音消息
				break;
			case MessageUtil.REQ_MESSAGE_TYPE_VIDEO:		//video,视频消息
				break;
			case MessageUtil.REQ_MESSAGE_TYPE_SHORTVIDEO:	//shortvideovideo,小视频消息
				break;
			case MessageUtil.REQ_MESSAGE_TYPE_LOCATION:		//location,地理位置消息
				break;
			case MessageUtil.REQ_MESSAGE_TYPE_LINK:			//link,链接消息
				break;
			case MessageUtil.REQ_MESSAGE_TYPE_EVENT:		//event,事件
				break;
			case MessageUtil.EVENT_TYPE_SUBSCRIBE:			//subscribe,订阅事件
				break;
			case MessageUtil.EVENT_TYPE_UNSUBSCRIBE:		//unsubscribe,取消订阅事件
				break;
			case MessageUtil.EVENT_TYPE_CLICK:
				break;
			}
		}
	}
}