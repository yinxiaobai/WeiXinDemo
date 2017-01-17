package com.weixin.one.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.weixin.one.pay.js.WXSign;
import com.weixin.one.utils.EncryptUtil;
import com.weixin.one.utils.Tool;

/**
 * @date 2017年1月12日下午2:39:06
 * @author jq.yin@i-vpoints.com
 */
@Controller
@RequestMapping("/create")
public class PayController {
	
	private static final Logger log = LoggerFactory
			.getLogger(PayController.class);
			
	@RequestMapping("/pay")
	@ResponseBody
	public String pay(HttpServletRequest request,HttpServletResponse response){
		log.info("【~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~pay statrt~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~】");
		Map<String,String> map = Tool.receiveMessage(request);
		log.info(map.toString());
		
		String returnXml = "";
		try {
			PrintWriter out = response.getWriter();
			returnXml = "<xml>"
					+ "<return_code><![CDATA["+map.get("return_code")+"]]></return_code>"
					+ "<return_msg><![CDATA["+map.get("return_msg")+"]]></return_msg></xml>";
			out.println(returnXml);
		} catch (IOException e) {
			log.error(e.getMessage(),e);
		}
		
		return "success";
	}
	
	@RequestMapping("/createOrder")
	public String createOrder (Model model){
		log.info("【create request】");
		Map<String,String> map = WXSign.getPaySign();
		if(map != null){
			log.info("【开始支付】");
		}
		model.addAttribute("pack",map.get("package"));
		model.addAttribute("map",map);
		return "pay";
	}
	
	@RequestMapping("/form")
	public void formPut (HttpServletResponse resp){
		log.info("【request】");
		String url = "weixin://wxpay/bizpayurl?pr=9L2TUyZ";
		url = "https://www.baidu.com";
		String form = "<body><form name='myform' method='post' action='"+url+"'>"
				+ "</form><script>document.myform.submit();</script></body>";
		form = "<script>location.href='"+url+"'</script>";
		PrintWriter out = null;
		try {
			out = resp.getWriter();
			out.print(form);
			out.flush();
		} catch (IOException e) {
			log.error(e.getMessage(),e);
		} finally{
			out.close();
		}
	}
	
	public static void main(String[] args) {
		String s = "BestChoice";
		System.out.println(EncryptUtil.md5(s));// cb60803fe1e9cc56164684e040b2bbc0
	}
}
