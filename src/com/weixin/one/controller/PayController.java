package com.weixin.one.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @date 2017年1月12日下午2:39:06
 * @author jq.yin@i-vpoints.com
 */
@Controller
public class PayController {
	
	@RequestMapping("/pay")
	public String pay(){
		
		
		
		return "pay";
	}
}
