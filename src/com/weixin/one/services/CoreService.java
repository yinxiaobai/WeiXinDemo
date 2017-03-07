package com.weixin.one.services;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.weixin.one.services.message.ReceiveService;
import com.weixin.one.utils.Tool;

/**
 * 微信端请求处理
 * 
 * @date 2016年12月19日下午2:17:18
 * @author hp
 * 
 */
public class CoreService {

	private static final Logger log = LoggerFactory.getLogger(CoreService.class);
	
	/**
	 * 微信端请求处理
	 * @date 2016年12月28日上午9:30:55
	 * @param request
	 * @param response
	 * @author jq.yin@i-vpoints.com
	 */
	public static void messageDo(HttpServletRequest request,
			HttpServletResponse response) {
		// 获取请求方式
		String method = request.getMethod();
		// FIXME
		if ("GET".equalsIgnoreCase(method)) { // GET,表示微信token验证
			try {
				TokenSignService.sign(request, response);
			} catch (Exception e) {
				// Token验证失败
				log.error(e.getMessage(), e);
			}
		} else if (method.equalsIgnoreCase("POST")) {	// POST,消息交互
			// 接收微信消息
			Map<String, String> msgMap = Tool.receiveMessage(request);
			log.info("收到微信端{}消息{}", msgMap.get("MsgType"), msgMap.toString());
			// 消息处理
			ReceiveService.getMessage(msgMap, response);

		} else { //不会出现
			throw new RuntimeException("未知错误!!!");	
			// log.error("未知异常!");
		}
	}
}