package com.weixin.one.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.weixin.one.config.WeiConfig;
import com.weixin.one.services.ReceiveService;
import com.weixin.one.services.TokenSignService;
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

	/**
	 * 微信唯一入口
	 * 
	 * @date 2016年12月20日下午4:18:01
	 * @param request
	 * @param response
	 * @author jq.yin@i-vpoints.com
	 */
	@RequestMapping("/core")
	public void coreReceive(HttpServletRequest request,
			HttpServletResponse response) {
		log.info("start...");

		// 加载配置
		WeiConfig.init();
		// 请求方式
		String method = request.getMethod();
		if ("GET".equalsIgnoreCase(method)) { // GET,表示微信token验证
			try {
				TokenSignService.sign(request, response);
			} catch (Exception e) {
				// Token验证失败
				log.error(e.getMessage(), e);
			}
		} else if (method.equalsIgnoreCase("POST")) {	// POST,消息交互
			// 接收微信消息
			Map<String, String> msgMap = Tool.xmlToMap(request);

			log.info("收到微信端{}消息:" + msgMap.toString(), msgMap.get("MsgType"));

			// 消息处理
			ReceiveService.getMessage(msgMap, response);

		} else {
			// throw new RuntimeException("未知错误!!!");
			log.error("未知异常!");
		}
	}
}