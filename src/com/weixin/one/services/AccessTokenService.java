package com.weixin.one.services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.weixin.one.config.WeiConfig;

/**
 * @date 2016年12月19日下午2:48:45
 * @author hp
 * 
 */
public class AccessTokenService {
	private static final Logger log = LoggerFactory.getLogger(AccessTokenService.class);

//	private static String appid = WeiConfig.APPID;
//
//	private static String secret = WeiConfig.SECRET;
	

	/**
	 * 根据appid和secret获取access_token(access_token两小时一更新)
	 * @date 2016年12月20日下午4:11:51
	 * @author jq.yin@i-vpoints.com
	 */
	@SuppressWarnings("unchecked")
	public String getAccess_Token(){
		WeiConfig.init();
		String url = WeiConfig.ACCESS_TOKEN_URL;
		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = url + "&appid=" + WeiConfig.APPID + "&secret="
					+ WeiConfig.SECRET;
			log.info("urlNameString:"+urlNameString);
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();
			
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		Map<String, String> map = JSONObject.parseObject(result,Map.class);
		String access_token = map.get("access_token");
		if("".equals(access_token) || access_token == null){
			throw new RuntimeException("【获取access_token失败】");
		}
		return access_token;
	}
	
	public static void main(String[] args) {
		log.info("start");
		try {
			String access_token = new AccessTokenService().getAccess_Token();
			log.info("access_token:"+access_token);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
}