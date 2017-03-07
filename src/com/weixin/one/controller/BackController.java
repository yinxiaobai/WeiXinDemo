package com.weixin.one.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.weixin.one.config.WeiConfig;
import com.weixin.one.utils.UrlUtils;

/**
 * @date 2017年1月10日下午4:26:55
 * @author jq.yin@i-vpoints.com
 */
@RestController
public class BackController {

	private static final Logger log = LoggerFactory
			.getLogger(BackController.class);

	/**
	 * 授权回调获取用户信息
	 * 
	 * @date 2017年1月16日下午2:26:52
	 * @param request
	 * @param model
	 * @return
	 * @author jq.yin@i-vpoints.com
	 */
	@RequestMapping("/test")
	public String test01(HttpServletRequest request, Model model) {
		log.info("【request】");
		String code = request.getParameter("code") == null ? ""
				: request.getParameter("code");
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
				+ WeiConfig.get("weixin.appid") + "&secret="
				+ WeiConfig.get("weixn.secret") + "&code=" + code
				+ "&grant_type=authorization_code";

		// 获得access_token和openId
		String resultJson = UrlUtils.urlGet(url);
		JSONObject jsonObject = JSONObject.parseObject(resultJson);
		String accessToken = jsonObject.getString("access_token");
		String openid = jsonObject.getString("openid");
		log.info("openId相关信息:" + resultJson);
		if (openid == null) {
			log.info("【openid获取失败】");
			return resultJson;
		}
		// 获取用户信息
		String userUrl = "https://api.weixin.qq.com/sns/userinfo?access_token="
				+ accessToken + "&openid=" + openid + "&lang=zh_CN";
		String userInfo = UrlUtils.urlGet(userUrl);
		log.info("用户信息:" + userInfo);
		model.addAttribute("userInfo", JSONObject.parse(userInfo));
		
		/*
		 * String url2 =
		 * "http://fishplus.i-vpoints.com/fishplus/wechat/auth/userAuth";
		 * openid = "o921QxGwyiXRxJ1SuTIktWnMitXk";
		 * String param = "access_token="+accessToken+"&openid="+openid;
		 * String res = UrlUtils.sendPost(url2, param);
		 * log.info("res::"+res);
		 */
		return userInfo;
	}

}