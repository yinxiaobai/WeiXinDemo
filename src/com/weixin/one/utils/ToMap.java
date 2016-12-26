package com.weixin.one.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.weixin.one.config.WeiConfig;

/**
 * @date 2016年12月26日下午5:50:39
 * @author hp
 * 
 */
public class ToMap {

	private static final Logger log = LoggerFactory.getLogger(ToMap.class);

	/**
	 * 通过腾讯地图获取位置信息
	 * xiaobai
	 * 2016年12月26日下午10:52:59
	 * @param xy
	 */
	public static void txMap(String xy) {

		String url = WeiConfig.MAP_URL;
		if (!"/".equals(url.indexOf(url.length() - 1))) {
			url = url + "/";
		}
		String key = WeiConfig.MAP_TOKEN;
		String urlName = url + "?location=" + xy + "&key=" + key;
		StringBuffer sb = new StringBuffer();
		// Map<String,String> map = new HashMap<String, String>();
		try {
			URL realUrl = new URL(urlName);
			BufferedReader br = new BufferedReader(new InputStreamReader(realUrl.openStream()));
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			JSONObject json = JSONObject.parseObject(sb.toString());
			JSONObject json2 = (JSONObject) json.get("result");
			JSONObject json3 = (JSONObject) json.get("ad_info");
			log.info(json.getString("result"));
			log.info(json2.getString("ad_info"));
		} catch (MalformedURLException e) {
			log.error(e.getMessage(), e);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * 百度地图根据x,y获取天气信息 
	 * 此xy信息由腾讯地图获得,由于百度和腾讯的xy获取方式不同,需要对xy进行一定转化
	 * 腾讯地图无通过xy直接获取天气信息的api... 
	 * //TODO xiaobai
	 * 2016年12月26日下午10:48:34
	 * 
	 * @param xy
	 */
	public static void bdMap(String xy) {
		String ak = "bNkpbjgApS7MO90oDUag1hHW9ukG6wfG";
		String url = "http://api.map.baidu.com/telematics/v3/weather";
		if ("/".equals(url.indexOf(url.length() - 1))) {
			url = url.substring(0, url.length() - 2);
		}
		// location参数需要进行处理,由腾讯地图获得的xy在这里不能使用。。
		// TODO
		String urlName = url + "?location=" + xy + "&output=" + "json" + "&ak=" + ak; // 无结果
		URL realUrl;
		try {
			realUrl = new URL(urlName);
			log.info(urlName);
			BufferedReader br = new BufferedReader(new InputStreamReader(realUrl.openStream()));
			String line;
			StringBuffer sb = new StringBuffer();
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			//No result available
			log.info(sb.toString());
		} catch (MalformedURLException e) {
			log.error(e.getMessage(), e);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}

	public static void main(String[] args) {
		WeiConfig.init();
		String xy = "31.270830,121.386482";
		// txMap(xy);
		bdMap(xy);
	}
}
