package com.weixin.one.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @date 2017年1月5日下午5:32:10
 * @author jq.yin@i-vpoints.com
 */
public class UrlUtils {

	private static final Logger log = LoggerFactory.getLogger(UrlUtils.class);

	// FIXME
	// HttpClient所需配置
	private static RequestConfig config = RequestConfig.custom()
			// .setSocketTimeout(60000)
			// .setConnectTimeout(60000)
			// .setConnectionRequestTimeout(60000)
			.build();

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
	 * URLConnection(HttpURLConnection)发送POST请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendHttpPost(String urlStr, String param) {
		log.info("【开始发送post请求】");
		PrintWriter out = null;
		BufferedReader in = null;
		StringBuffer sb = new StringBuffer("\n");
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
			connection.setRequestMethod("POST"); //不写也可 ？
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
				sb.append(line).append("\n");
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
		log.info("post请求结果:"+sb.toString());
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
		// 正文是URLEncoded编码过的form参数，下面我们可以看到我们对正文内容使用URLEncoder.encode
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
			log.debug("请求起始行:" + httpResponse.getStatusLine().toString());
			// 获得首部信息
			log.debug("【获取响应头信息】");
			Header[] hs = httpResponse.getAllHeaders();
			for (Header h : hs) {
				log.debug(h.getName() + "\t" + h.getValue());
			}
			entity = httpResponse.getEntity();
			responseContent = EntityUtils.toString(entity, "UTF-8");
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		} finally {
			// 关闭相关资源
			try {
				/*
				 * 关闭资源，底层的流
				 * InputStream inputStream = entity.getContent();
				 * inputStream.close();
				 */
				EntityUtils.consume(entity);

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
			log.debug("起始行:" + httpResponse.getStatusLine().toString());
			// 获得首部信息
			log.debug("【获取响应头信息】");
			Header[] hs = httpResponse.getAllHeaders();
			for (Header h : hs) {
				log.debug(h.getName() + "\t" + h.getValue());
			}
			// 获得实体
			entity = httpResponse.getEntity();
			responseContent = EntityUtils.toString(entity, "UTF-8");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			try {
				// 关闭 InputStream 流
				EntityUtils.consume(entity);
				if (httpResponse != null) {
					httpResponse.close();
				}
				if (httpClient != null) {
					httpClient.close();
				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		return responseContent;
	}
	
	public static void main(String[] args) {
		String url = "http://wxgzh3.i-vpoints.com/ZYbankLove/core/test?phoneNo=13322445432";
		//String param = "<xml><appid>wx2421b1c4370ec43b</appid><attach>支付测试</attach>   <body>JSAPI支付测试</body>   <mch_id>10000100</mch_id>   <detail><![CDATA[{ \"goods_detail\":[ { \"goods_id\":\"iphone6s_16G\", \"wxpay_goods_id\":\"1001\", \"goods_name\":\"iPhone6s 16G\", \"quantity\":1, \"price\":528800, \"goods_category\":\"123456\", \"body\":\"苹果手机\" }, { \"goods_id\":\"iphone6s_32G\", \"wxpay_goods_id\":\"1002\", \"goods_name\":\"iPhone6s 32G\", \"quantity\":1, \"price\":608800, \"goods_category\":\"123789\", \"body\":\"苹果手机\" } ] }]]></detail>   <nonce_str>1add1a30ac87aa2db72f57a2375d8fec</nonce_str>   <notify_url>http://wxpay.wxutil.com/pub_v2/pay/notify.v2.php</notify_url>   <openid>oUpF8uMuAJO_M2pxb1Q9zNjWeS6o</openid>   <out_trade_no>1415659990</out_trade_no>   <spbill_create_ip>14.23.150.211</spbill_create_ip>   <total_fee>1</total_fee>   <trade_type>JSAPI</trade_type>   <sign>0CB01533B8C1EF103065174F50BCA001</sign></xml>";
		log.info(sendGet(url));
	}
}
