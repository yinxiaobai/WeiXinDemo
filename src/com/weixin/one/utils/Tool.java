package com.weixin.one.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thoughtworks.xstream.XStream;

/**
 * 工具类
 * 
 * @date 2016年12月19日下午2:10:00
 * @author hp
 * 
 */
public class Tool {

	private static final Logger log = LoggerFactory.getLogger(Tool.class);

	/**
	 * SHA1加密
	 * 
	 * @date 2016年12月20日上午9:30:34
	 * @param decript
	 * @return
	 * @author jq.yin@i-vpoints.com
	 */
	public static String SHA1(String decript) {
		try {
			MessageDigest digest = java.security.MessageDigest
					.getInstance("SHA-1");
			digest.update(decript.getBytes());
			byte messageDigest[] = digest.digest();
			// Create Hex String
			StringBuffer hexString = new StringBuffer();
			// 字节数组转换为 十六进制 数
			for (int i = 0; i < messageDigest.length; i++) {
				String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
				if (shaHex.length() < 2) {
					hexString.append(0);
				}
				hexString.append(shaHex);
			}
			return hexString.toString();

		} catch (NoSuchAlgorithmException e) {
			log.error(e.getMessage(), e);
		}
		return "";
	}

	/**
	 * 接收微信端消息
	 * 
	 * @date 2016年12月20日下午4:19:41
	 * @param request
	 * @return
	 *         将获取的xml返回结果转换为Map
	 * @author jq.yin@i-vpoints.com
	 */
	public static Map<String, String> receiveMessage(HttpServletRequest request) {

		Map<String, String> map = new HashMap<String, String>();
		SAXReader reader = new SAXReader();

		try {
			InputStream is = request.getInputStream();
			Document doc = reader.read(is);
			Element root = doc.getRootElement();
			@SuppressWarnings("unchecked")
			List<Element> list = root.elements();
			for (Element e : list) {
				map.put(e.getName(), e.getText());
			}
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		} catch (DocumentException e) {
			log.error(e.getMessage(), e);
		}
		return map;
	}

	/**
	 * <未使用> 将map转化为xml字符串
	 * xiaobai
	 * 2016年12月19日下午17:14:41
	 * 
	 * @param map
	 * @return
	 */
	public static String MaptoXml(Map<String,String> map) {
		XStream stream = new XStream();
		stream.alias("xml", map.getClass());
		String xml = stream.toXML(map);
		return xml;
	}

	/**
	 * URL发送get请求
	 * 
	 * @date 2016年12月27日上午11:14:14
	 * @param urlStr
	 *            请求地址
	 * @return 返回的JSONObject对象
	 * @author jq.yin@i-vpoints.com
	 */
	public static String urlGet(String urlStr) /* throws IOException */{
		BufferedReader br = null;
		StringBuffer sb = new StringBuffer();
		try {
			// FIXME
			URL url = new URL(urlStr);
			br = new BufferedReader(new InputStreamReader(url.openStream()));
			String line;

			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
		} catch (MalformedURLException e) {
			log.error(e.getMessage(), e);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		} finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (IOException e) {
				log.error(e.getMessage(), e);
			}
		}
		return sb.toString();
	}

	/**
	 * <未使用>
	 * URLConnection发送GET请求
	 * 
	 * @date 2016年12月28日下午2:23:10
	 * @param urlStr
	 * @return
	 * @author jq.yin@i-vpoints.com
	 */
	public static String urlConnectionGet(String urlStr) {
		StringBuffer sb = new StringBuffer();
		BufferedReader br = null;
		try {
			URL url = new URL(urlStr);
			URLConnection connection = url.openConnection();
			// HttpURLConnection方式访问
			// HttpURLConnection connection =
			// (HttpURLConnection)url.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "/");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();

			// 获取所有响应头字段
			Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
			log.debug(">>>>>>>响应头");
			for (String key : map.keySet()) {
				log.debug(key + ">>>>>>>>" + map.get(key));
			}
			// 定义BufferedReader输入流来读取URL的响应
			br = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
		} catch (MalformedURLException e) {
			log.error(e.getMessage(), e);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		} finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (IOException e) {
				log.error(e.getMessage(), e);
			}
		}
		return sb.toString();
	}

	/**
	 * <未使用>
	 * URLConnection(HttpURLConnection)发送POST请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPost(String urlStr, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		StringBuffer sb = new StringBuffer();
		try {
			URL url = new URL(urlStr);
			// 打开和URL之间的连接
			// URLConnection connection = url.openConnection();
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			connection.setDoOutput(true);
			connection.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(connection.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 获取所有响应头字段
			Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
			log.debug(">>>>>>>响应头");
			for (String key : map.keySet()) {
				log.debug(key + ">>>>>>>>" + map.get(key));
			}
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				sb.append(line);
			}
		} catch (Exception e) {
			log.error("发送 POST 请求出现异常！", e);
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return sb.toString();
	}
}