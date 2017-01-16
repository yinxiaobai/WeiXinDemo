package com.weixin.one.services;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.weixin.one.config.WeiConfig;
import com.weixin.one.utils.UrlUtils;

/**
 * access_token获取
 * 
 * @date 2016年12月19日下午2:48:45
 * @author hp
 * 
 */
public class AccessTokenService {
	
	private static final Logger log = LoggerFactory
			.getLogger(AccessTokenService.class);
	
	private static AccessTokenService single = new AccessTokenService();
	
	private AccessTokenService(){
		if(single != null){
			throw new RuntimeException("【单例模式被破坏】");
		}
	}
	
	public static AccessTokenService getInstance(){
		return single;
	}
	
	public String access_token;
	
	/**
	 * FIXME
	 * 根据appid和secret获取access_token
	 * access_token两小时一更新,需做特殊处理
	 * 
	 * @date 2016年12月20日下午4:11:51
	 * @author jq.yin@i-vpoints.com
	 */
	public void receiveAccess_Token() {
//		WeiConfig.init();
		String url = WeiConfig.get("access_token.url");
		String urlName = url + "&appid="
				+ WeiConfig.get("weixin.appid") + "&secret="
				+ WeiConfig.get("weixn.secret");
		// 通过URL发送get请求
//		String result = Tool.urlGet(urlName);
		String result = UrlUtils.sendGet(urlName);
		@SuppressWarnings("unchecked")
		Map<String, String> map = JSONObject.parseObject(result, Map.class);
		// 有效时间 单位:s
		access_token = map.get("access_token");
		if ("".equals(access_token) || access_token == null) {
			throw new RuntimeException("【获取access_token失败】");
		}
		log.info("获取access_token成功:"+access_token);
		log.info("凭证有效时间{}s",map.get("expires_in"));
	}
	
	public String getAccess_token() {
		return access_token;
	}
	

	public static void main(String[] args) {
		log.info("start");
		WeiConfig.init();
		System.out.println(new AccessTokenService().getAccess_token());
		// log.info("access_token:" + access_token);
	}
}