package com.weixin.one.services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

/**
 * @date 2016年12月19日下午2:48:45
 * @author hp
 * 
 */
public class AccessTokenService {
	
	private static final Logger log = LoggerFactory.getLogger(AccessTokenService.class);

	private static final String appid = "wxbba2bd6cd3833298";

	private static final String secret = "07c62fff8eb8a98e5547c6bb917e5f0f";

	public URLConnection getConnect(String url,Object... obc){
		
		
		return null;
	}
	
	
	
	public String getAccess_Token() throws Exception {
		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential";
		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = url + "&appid=" + appid + "&secret="
					+ secret;
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
			
			// 获取所有响应头字段
			//Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
//			for (String key : map.keySet()) {
//				System.out.println(key + "--->" + map.get(key));
//			}
			
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
		@SuppressWarnings("unchecked")
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
			System.out.println(access_token);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
}
