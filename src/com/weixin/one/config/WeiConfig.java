package com.weixin.one.config;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

/**
 * @date 2016年12月20日下午1:14:43
 * @author hp
 *
 */
public class WeiConfig {
	
	private static final Logger log = LoggerFactory.getLogger(WeiConfig.class);
	
	private static final String PRO_NAME = "config.properties";
	
	//addid
	public static String APPID;
	//secret
	public static String SECRET;
	//token
	public static String TOKEN;
	//access_tonke获取地址
	public static String ACCESS_TOKEN_URL;
	//开发者微信号
	public static String OPENID;
	
	/**
	 * 加载配置
	 * @date 2016年12月20日下午4:17:45
	 * @author jq.yin@i-vpoints.com
	 */
	public static void init() {
		
		Resource resource = new ClassPathResource(PRO_NAME);
		try {
			Properties pro = PropertiesLoaderUtils.loadProperties(resource);
			APPID = (String) pro.get("weixin.appid");
			SECRET = pro.getProperty("weixn.secret");
			TOKEN = pro.getProperty("weixin.token");
			OPENID = pro.getProperty("weixin.openid");
			ACCESS_TOKEN_URL = pro.getProperty("access.token.url");
		} catch (IOException e) {
			log.error("【配置文件读取出错】",e);
		}
	}
}