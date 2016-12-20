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
	
	public static String APPID;
	public static String SECRET;
	public static String TOKEN;
	public static String ACCESS_TOKEN_URL;
	
	public static void init() {
		
		Resource resource = new ClassPathResource(PRO_NAME);
		try {
			Properties pro = PropertiesLoaderUtils.loadProperties(resource);
			APPID = (String) pro.get("weixin.appid");
			SECRET = pro.getProperty("weixn.secret");
			TOKEN = pro.getProperty("weixin.token");
			ACCESS_TOKEN_URL = pro.getProperty("access.token.url");
		} catch (IOException e) {
			log.error("【配置文件读取出错】",e);
		}
	}
}