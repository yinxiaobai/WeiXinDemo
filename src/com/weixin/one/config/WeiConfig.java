package com.weixin.one.config;

import java.io.IOException;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Service;

import com.weixin.one.services.AccessTokenService;

/**
 * @date 2016年12月20日下午1:14:43
 * @author jq.yin@i-vpoints.com
 * 
 */
@Service
public class WeiConfig {

	private static final Logger log = LoggerFactory.getLogger(WeiConfig.class);

	private static final String PRO_NAME = "config.properties";

	public static Properties pro;
	/**
	 * 加载配置
	 * 服务器启动时自动加载
	 * 
	 * @date 2016年12月20日下午4:17:45
	 * @author jq.yin@i-vpoints.com
	 */
	@PostConstruct //启动项目自动加载
	public static void init() {
		log.info("。。。加载配置");
		Resource resource = new ClassPathResource(PRO_NAME);
		try {
			pro = PropertiesLoaderUtils.loadProperties(resource);
		} catch (IOException e) {
			log.error("【配置文件读取出错】", e);
		}
		
		//启动定时器
		log.info("定时器启动");
		new Timer().schedule(new TimerTask() {
			
			@Override
			public void run() {
				AccessTokenService.receiveAccess_Token();
			}
		}, 0,1000*60*90);
	}

	public static String get(String key) {
		return pro.getProperty(key);
	}

	public static String get(String key, String defaultValue) {
		return pro.getProperty(key, defaultValue);
	}
}