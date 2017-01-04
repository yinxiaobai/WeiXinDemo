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

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
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

	// FIXME
	// HttpClient所需配置
	private static RequestConfig config = RequestConfig.custom()
			// .setSocketTimeout(60000)
			// .setConnectTimeout(60000)
			// .setConnectionRequestTimeout(60000)
			.build();

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
	 * 		将获取的xml返回结果转换为Map
	 * @author jq.yin@i-vpoints.com
	 */
	public static Map<String, String> receiveMessage(
			HttpServletRequest request) {

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
	public static String MaptoXml(Map<String, String> map) {
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
	public static String urlGet(String urlStr) /* throws IOException */ {
		BufferedReader br = null;
		StringBuffer sb = new StringBuffer();
		try {
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
			br = new BufferedReader(
					new InputStreamReader(connection.getInputStream()));
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
	public static String sendHttpPost(String urlStr, String param) {
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
			// 设置是否向connection输出，因为这个是post请求，参数要放在
			// http正文内，因此需要设为true
			connection.setDoOutput(true);
			// Read from the connection. Default is true.
			connection.setDoInput(true);
			// 默认为GET
			// connection.setRequestMethod("POST"); //不写也可 ？
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
			in = new BufferedReader(
					new InputStreamReader(connection.getInputStream()));
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

	/**
	 * HttpClient发送POST请求
	 * 
	 * @date 2017年1月4日下午4:29:39
	 * @param url
	 * @param param
	 * @return
	 * @author jq.yin@i-vpoints.com
	 */
	public static String sendPost(String url, String param) {
		HttpPost httpPost = new HttpPost(url);

		// 设置参数
		StringEntity stringEntity = new StringEntity(param, "UTF-8");
		// 正文是urlencoded编码过的form参数，下面我们可以看到我们对正文内容使用URLEncoder.encode
		stringEntity.setContentType("application/x-www-form-urlencoded");
		httpPost.setEntity(stringEntity);

		CloseableHttpClient httpClient = null;
		CloseableHttpResponse httpResponse = null;
		HttpEntity entity = null;
		String responseContent = null;
		try {
			// 创建默认的httpClient实例
			httpClient = HttpClients.createDefault();
			httpPost.setConfig(config);
			// 执行请求
			httpResponse = httpClient.execute(httpPost);
			entity = httpResponse.getEntity();
			responseContent = EntityUtils.toString(entity, "UTF-8");
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		} finally {
			// 关闭连接
			try {
				if (httpResponse != null) {
					httpResponse.close();
				}
				if (httpClient != null) {
					httpClient.close();
				}
			} catch (IOException e) {
				log.error(e.getMessage(), e);
			}
		}
		return responseContent;
	}

	/**
	 * HttpClient发送GET请求
	 * 
	 * @date 2017年1月4日下午4:46:42
	 * @param url
	 * @return
	 * @author jq.yin@i-vpoints.com
	 */
	public static String sendGet(String url) {
		HttpGet httpGet = new HttpGet(url);
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse httpResponse = null;
		HttpEntity entity = null;
		String responseContent = null;
		try {
			// 创建httpClient实例
			httpClient = HttpClients.createDefault();
			httpGet.setConfig(config);
			// 执行请求
			httpResponse = httpClient.execute(httpGet);
			entity = httpResponse.getEntity();
			responseContent = EntityUtils.toString(entity, "UTF-8");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			try {
				if(httpResponse != null){
					httpResponse.close();
				}
				if(httpClient != null){
					httpClient.close();
				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		return responseContent;
	}
}