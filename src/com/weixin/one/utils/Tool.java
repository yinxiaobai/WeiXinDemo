package com.weixin.one.utils;

import java.io.IOException;
import java.io.InputStream;
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
 * @date 2016年12月19日下午2:10:00
 * @author hp
 *
 */
public class Tool {
	
	private static final Logger log = LoggerFactory.getLogger(Tool.class);
	
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
			log.error(e.getMessage(),e);
		}
		return "";
	}
	
	//将XML转换为Map
	@SuppressWarnings("unchecked")
	public static Map<String,String> xmlToMap(HttpServletRequest request){
		
		Map<String,String> map = new HashMap<String, String>();
		SAXReader reader = new SAXReader();
		
		try {
			InputStream is = request.getInputStream();
			Document doc = reader.read(is);
			Element root = doc.getRootElement();
			List<Element> list = root.elements();
			for(Element e : list){
				map.put(e.getName(), e.getText());
			}
		} catch (IOException e) {
			log.error(e.getMessage(),e);
		} catch (DocumentException e) {
			log.error(e.getMessage(),e);
		}
		return map;
	}
	
	/**
	 * 将map转化为xml字符串
	 * xiaobai
	 * 2016年12月20日上午12:14:41
	 * @param map
	 * @return
	 */
	public static String MaptoXml(Object map){
		XStream stream = new XStream();
		stream.alias("xml", map.getClass());
		String xml = stream.toXML(map);
		return xml;
	}
}
