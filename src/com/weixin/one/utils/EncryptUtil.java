package com.weixin.one.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @date 2017年1月16日上午11:47:55
 * @author jq.yin@i-vpoints.com
 */
public class EncryptUtil {
	
	private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
	
	/**
	 * SHA1加密
	 * 
	 * @date 2016年12月20日上午9:30:34
	 * @param decript
	 *            加密字符串
	 * @return 密文
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
			e.printStackTrace();
		}
		return "";
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
