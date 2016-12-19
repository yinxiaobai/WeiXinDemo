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
import org.springframework.web.bind.annotation.ResponseBody;

import com.weixin.one.services.ThreeService;
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
public class One {

	private static final Logger log = LoggerFactory.getLogger(Two.class);

	private static final String token = "ae8pvIDE8NzmMEcHvpcJPs7r";

	@RequestMapping("/one")
	@ResponseBody
	public String oneTest(HttpServletRequest request, HttpServletResponse response) {

		log.info("start...");

		String signature = request.getParameter("signature") == null ? ""
				: request.getParameter("signature");
		String timestamp = request.getParameter("timestamp") == null ? ""
				: request.getParameter("timestamp");
		String nonce = request.getParameter("nonce") == null ? "" : request
				.getParameter("nonce");
		String echostr = request.getParameter("echostr") == null ? "" : request
				.getParameter("echostr");
		// token验证
		if (!"".equals(signature)) {
			String[] arr = new String[] { token, timestamp, nonce };
			Arrays.sort(arr);
			String str = Tool.SHA1(arr[0] + arr[1] + arr[2]);
			if (!str.equals(signature)) {
				log.info("【TOKEN验证失败】");
				return null;
			}
			log.info("【TOKEN验证成功】");
			try {
				//将echostr返回给微信
				PrintWriter out = response.getWriter();
				out.println(echostr);
				out.flush();
			} catch (IOException e) {
				log.error(e.getMessage(), e);
			}
		}
		
		Map<String, String> map = Tool.xmlToMap(request);

		String MsgType = map.get("MsgType");
		switch (MsgType) {
		case MessageUtil.REQ_MESSAGE_TYPE_TEXT:
			log.info("接收到类型为" + MessageUtil.REQ_MESSAGE_TYPE_TEXT + "的消息:"
					+ map.get("Content"));
			ThreeService.TextMessage(map);
			return "success";
		case MessageUtil.REQ_MESSAGE_TYPE_IMAGE:
			break;
		case MessageUtil.REQ_MESSAGE_TYPE_LINK:
			break;
		case MessageUtil.REQ_MESSAGE_TYPE_LOCATION:
			break;
		case MessageUtil.REQ_MESSAGE_TYPE_VOICE:
			break;
		case MessageUtil.REQ_MESSAGE_TYPE_EVENT:
			break;
		case MessageUtil.EVENT_TYPE_SUBSCRIBE:
			break;
		case MessageUtil.EVENT_TYPE_UNSUBSCRIBE:
			break;
		case MessageUtil.EVENT_TYPE_CLICK:
			break;
		}

		log.info(map.toString());
		return null;
	}
}