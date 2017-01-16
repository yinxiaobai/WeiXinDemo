package com.weixin.one.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;

import com.qq.weixin.mp.aes.AesException;
import com.qq.weixin.mp.aes.WXBizMsgCrypt;
import com.thoughtworks.xstream.XStream;
import com.weixin.one.config.WeiConfig;

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
	 * 转换为URL编码
	 * 
	 * @date 2017年1月16日下午1:38:00
	 * @param str
	 * @return
	 * @author jq.yin@i-vpoints.com
	 */
	public static String getURLString(String str) {
		try {
			str = URLEncoder.encode(str, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			log.error(e.getMessage(), e);
		}
		return str;
	}

	/**
	 * 接收微信端xml消息,转换为Map
	 * 
	 * @date 2016年12月20日下午4:19:41
	 * @param request
	 * @return
	 * 		接收到的微信端消息明文
	 * @author jq.yin@i-vpoints.com
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> receiveMessage(
			HttpServletRequest request) {

		Map<String, String> map = new HashMap<String, String>();
		SAXReader reader = new SAXReader();

		try {
			InputStream is = request.getInputStream();
			Document doc = reader.read(is);
			Element root = doc.getRootElement();

			if ("true".equalsIgnoreCase(WeiConfig.get("encrypt"))) {	// 密文模式
				log.info("【密文模式】");
				// xml格式密文
				String xmlStr = root.asXML();
				// 验证信息
				String timeStamp = request.getParameter("timestamp");
				String nonce = request.getParameter("nonce");
				String msgSignature = request.getParameter("msg_signature");
				// 获得密文信息
				// 获得WXBizMsgCrypt对象
				WXBizMsgCrypt pc = WeiConfig.getWXBizMsgCrypt();
				// 解密,获取明文信息 xml格式
				String msg = pc.decryptMsg(msgSignature, timeStamp, nonce,
						xmlStr);
				StringReader xmlReader = new StringReader(msg);
				InputSource source = new InputSource(xmlReader);
				// 再次调用dom4j将xml信息转换为Map FIXME
				doc = reader.read(source);
				root = doc.getRootElement();
			}

			// 封装Map
			List<Element> list = root.elements();
			for (Element e : list) {
				map.put(e.getName(), e.getText());
			}
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		} catch (DocumentException e) {
			log.error(e.getMessage(), e);
		} catch (AesException e) {
			log.error(e.getMessage(), e);
		}
		return map;
	}

	/**
	 * xml字符串转换为Map
	 * 
	 * @date 2017年1月16日上午11:49:37
	 * @param xmlStr
	 * @return
	 * @throws DocumentException
	 * @author jq.yin@i-vpoints.com
	 */
	public static Map<String, String> xmlToMap(String xmlStr)
			throws DocumentException {
		StringReader xmlReader = new StringReader(xmlStr);
		InputSource source = new InputSource(xmlReader);
		SAXReader reader = new SAXReader();
		;
		Document doc = reader.read(source);
		Element root = doc.getRootElement();
		Map<String, String> map = new HashMap<String, String>();
		// 封装Map
		@SuppressWarnings("unchecked")
		List<Element> list = root.elements();
		for (Element e : list) {
			map.put(e.getName(), e.getText());
		}
		return map;
	}

	/**
	 * 将Map转化为xml字符串
	 * xiaobai
	 * 2016年12月19日下午23:14:41
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
	 * 生成随机字符串
	 * 
	 * @date 2017年1月12日下午5:25:26
	 * @return
	 * @author jq.yin@i-vpoints.com
	 */
	public static String createNonceStr() {
		return UUID.randomUUID().toString();
	}

	public static void main(String[] args) {
		System.out.println(createNonceStr());
	}

}