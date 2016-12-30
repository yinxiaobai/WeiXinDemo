package com.weixin.one.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.weixin.one.services.CoreService;

/**
 * 微信唯一入口
 * 
 * @date 2016年12月19日下午2:17:18
 * @author hp
 * 
 */
@Controller
@RequestMapping("/Wei")
public class CoreWei {

	private static final Logger log = LoggerFactory.getLogger(CoreWei.class);

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

		// 请求处理
		CoreService.messageDo(request, response);
	}
}