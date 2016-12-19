package com.weixin.one.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.weixin.one.services.TwoService;

/**
 * @date 2016年12月19日上午11:26:40
 * @author hp
 *
 */
@Controller
@RequestMapping("one/")
public class Two {
	
	private static final Logger log = LoggerFactory.getLogger(Two.class);
	
	@RequestMapping("getMsg")
	@ResponseBody
	public String getMsg(HttpServletRequest request){
		
		log.info("接收到请求");
		
		return "success";
	}
	
	
	
	
	public static void main(String[] args) {
		log.info("start");
		try {
			String access_token = new TwoService().getAccess_Token();
			System.out.println(access_token);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
}