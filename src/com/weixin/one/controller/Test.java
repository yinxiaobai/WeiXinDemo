package com.weixin.one.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @date 2017年1月10日下午4:26:55
 * @author jq.yin@i-vpoints.com
 */
@Controller
public class Test {
	@RequestMapping("/test")
	public String test01(){
		System.out.println(123);
		return "pay";
	}
}