package com.weixin.one.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @date 2016年12月26日下午5:50:39
 * @author hp
 * 
 */
public class ToMap {
	
	private static final Logger log = LoggerFactory.getLogger(ToMap.class);
	
	public static void txMap(String xy,String key) {
		String url = "http://apis.map.qq.com/ws/geocoder/v1/";
		String urlName = url + "?location=" + xy + "&key=" + key;
		StringBuffer sb = new StringBuffer();
		//Map<String,String> map = new HashMap<String, String>();
		try {
			URL realUrl = new URL(urlName);
			BufferedReader br = new BufferedReader(new InputStreamReader(realUrl.openStream()));
			
			
			
		} catch (MalformedURLException e) {
			log.error(e.getMessage(),e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
