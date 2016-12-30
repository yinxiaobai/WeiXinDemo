package com.weixin.one.utils;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.weixin.one.config.WeiConfig;

/**
 * 坐标api相关
 * 
 * @date 2016年12月26日下午5:50:39
 * @author hp
 * 
 */
public class MapApi {

	private static final Logger log = LoggerFactory.getLogger(MapApi.class);

	/**
	 * 通过腾讯地图获取位置信息
	 * 腾讯地图无通过xy直接获取天气信息的api...
	 * 火星坐标
	 * xiaobai
	 * 
	 * @date 2016年12月26日下午10:52:59
	 * @param locationX
	 *            纬度
	 * @param locationY
	 *            经度
	 */
	@SuppressWarnings("unused")
	public static void txMap(String locationX, String locationY) {
		String xy = locationX + "," + locationY;

		String url = WeiConfig.get("mapUrl");
		if (!"/".equals(url.indexOf(url.length() - 1))) {
			url = url + "/";
		}
		String key = WeiConfig.get("mapToken");
		String urlName = url + "?location=" + xy + "&key=" + key;
		// TODO 结果为多层json
		String result = Tool.urlGet(urlName);
		JSONObject json = (JSONObject) JSONObject.parse(result);
		JSONObject json2 = (JSONObject) json.get("result");
		JSONObject json3 = (JSONObject) json2.get("ad_info");
		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>) json2.get("ad_info");
		log.info("map:" + map.toString());
		log.info(json.getString("result"));
		log.info(json2.getString("ad_info"));
	}

	/**
	 * 百度地图根据x,y获取天气信息
	 * 此xy信息由腾讯地图获得,由于百度和腾讯的xy获取方式不同,需要对xy进行一定转化
	 * 百度地图获取为百度坐标
	 * //TODO
	 * xiaobai
	 * 2016年12月26日下午10:48:34
	 * 
	 * @param locationX
	 *            纬度
	 * @param locationY
	 *            经度
	 */
	public static void bdMap(String locationX, String locationY) {

		String xy = locationX + "," + locationY;
		String ak = "bNkpbjgApS7MO90oDUag1hHW9ukG6wfG";
		String url = "http://api.map.baidu.com/telematics/v3/weather";
		if ("/".equals(url.indexOf(url.length() - 1))) {
			url = url.substring(0, url.length() - 2);
		}
		// location参数需要进行处理,由腾讯地图获得的xy在这里不能使用。。
		String urlName = url + "?location=" + xy + "&output=" + "json" + "&ak="
				+ ak; // 无结果
		String result = Tool.urlGet(urlName);
		JSONObject json = (JSONObject) JSONObject.parse(result);
		log.info(json.toJSONString());
	}

	/**
	 * 心知天气 <br>
	 * 返回信息较少，需手动坐标转换
	 * GPS坐标
	 * 
	 * @date 2016年12月27日上午10:35:43
	 * @param locationX
	 *            纬度
	 * @param locationY
	 *            经度
	 * @author jq.yin@i-vpoints.com
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> xzWeather(String locationX,
			String locationY) {
		String key = "i3i1kxpawhjmhddm";
		String url = "https://api.thinkpage.cn/v3/weather/now.json";
		String xy = locationX + ":" + locationY;
		String urlName = url + "?key=" + key + "&location=" + xy;
		Map<String, String> map = null;

		String result = Tool.urlGet(urlName);
		JSONObject json = (JSONObject) JSONObject.parse(result);

		String results = json.getString("results").substring(1,
				json.getString("results").length() - 1);
		JSONObject json2 = (JSONObject) JSONObject.parseObject(results);

		map = (Map<String, String>) json2.get("now");
		log.info(map.toString());
		return map;
	}

	public static void main(String[] args) {
		WeiConfig.init();
		String x = "31.270830";
		String y = "121.386482";
		txMap(x, y);
		// bdMap(x, y);
		// xzWeather(x, y);
	}
}