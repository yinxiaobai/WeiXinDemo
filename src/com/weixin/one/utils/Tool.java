package com.weixin.one.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
	
	private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

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
			// SHA1签名生成
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
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
				String msg = pc.decryptMsg(msgSignature, timeStamp, nonce, xmlStr);
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
	
	@SuppressWarnings("unchecked")
	public static Map<String,String> xmlToMap(String xmlStr) throws DocumentException{
		StringReader xmlReader = new StringReader(xmlStr);
		InputSource source = new InputSource(xmlReader);
		SAXReader reader = new SAXReader();;
		Document doc = reader.read(source);
		Element root = doc.getRootElement();
		Map<String,String> map = new HashMap<String,String>();
		// 封装Map
		List<Element> list = root.elements();
		for (Element e : list) {
			map.put(e.getName(), e.getText());
		}
		return map;
	}

	/**
	 * 将map转化为xml字符串
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
     * 生成随机字符串
     * @date 2017年1月12日下午5:25:26
     * @return
     * @author jq.yin@i-vpoints.com
     */
    public static String createNonceStr () {
    	return UUID.randomUUID().toString();
    }
	
    private static String toHex(byte[] bytes) {
		StringBuilder ret = new StringBuilder(bytes.length * 2);
		for (int i=0; i<bytes.length; i++) {
			ret.append(HEX_DIGITS[(bytes[i] >> 4) & 0x0f]);
			ret.append(HEX_DIGITS[bytes[i] & 0x0f]);
		}
		return ret.toString();
	}
    
	public static String md5(String srcStr){
		return hash("MD5", srcStr);
	}
	
	public static String hash(String algorithm, String srcStr) {
		try {
			MessageDigest md = MessageDigest.getInstance(algorithm);
			byte[] bytes = md.digest(srcStr.getBytes("utf-8"));
			return toHex(bytes);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}