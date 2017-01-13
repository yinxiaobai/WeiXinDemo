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

import com.weixin.one.pay.js.WXSign;
import com.weixin.one.utils.Tool;

/**
 * @date 2017年1月12日下午2:39:06
 * @author jq.yin@i-vpoints.com
 */
@Controller
public class PayController {
	
	private static final Logger log = LoggerFactory
			.getLogger(PayController.class);
			
	@RequestMapping("/pay")
	public String pay(HttpServletRequest request){
		log.info("【~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~pay statrt~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~】");
		String returnCode = request.getParameter("return_code") == null ? "" :request.getParameter("return_code");
		String returnMsg = request.getParameter("return_msg") == null ? "" :request.getParameter("return_code");
		if(!"".equals(returnCode)){
			String appid = request.getParameter("appid") == null ? "" :request.getParameter("return_code");
			String mchId = request.getParameter("mch_id") == null ? "" :request.getParameter("return_code");
			String nonceStr = request.getParameter("nonce_str") == null ? "" :request.getParameter("return_code");
			String sign = request.getParameter("sign") == null ? "" :request.getParameter("return_code");
			String resultCode = request.getParameter("result_code") == null ? "" :request.getParameter("return_code");
			
			if("SUCCESS".equals(returnCode) && "SUCCESS".equals(resultCode)){
				System.out.println("【支付成功】");
				String tradeType = request.getParameter("trade_type") == null ? "" :request.getParameter("return_code");
				String prepayId = request.getParameter("prepay_id") == null ? "" :request.getParameter("return_code");
				String codeUrl = request.getParameter("code_url") == null ? "" :request.getParameter("return_code");
			}
		}
		System.out.println("returnCode:"+returnCode);
		System.out.println("returnMsg:"+returnMsg);
		@SuppressWarnings("unchecked")
		Map<String,Object> map = request.getParameterMap();
		System.out.println(map.toString());
		return "pay";
	}
	
	@RequestMapping("/create")
	public String createOrder (Model model){
		log.info("【create request】");
		Map<String,String> map = WXSign.getPaySign();
		model.addAttribute("pack",map.get("package"));
		model.addAttribute("paySign",map.get("sign"));
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
			e.printStackTrace();
		} finally{
			out.close();
		}
	}
	
	public static void main(String[] args) {
		String s = "BestChoice";
		System.out.println(Tool.md5(s));// cb60803fe1e9cc56164684e040b2bbc0
	}
}
