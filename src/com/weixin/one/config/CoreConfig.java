package com.weixin.one.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qq.weixin.mp.aes.AesException;
import com.qq.weixin.mp.aes.WXBizMsgCrypt;

/**
 * @date 2016年12月20日下午1:14:43
 * @author jq.yin@i-vpoints.com
 * 
 */
public class CoreConfig {

	private static final Logger log = LoggerFactory.getLogger(CoreConfig.class);

	public static CoreConfig config = new CoreConfig();

	private WXBizMsgCrypt bizMsgCrypt;

	private CoreConfig() {
		if (config != null) {
			throw new RuntimeException("Error! Error! Error!");
		}
		try {
			bizMsgCrypt = new WXBizMsgCrypt(WeiConfig.get("weixin.token"),
					WeiConfig.get("EncodingAESKey"), WeiConfig.get("weixin.appid"));
		} catch (AesException e) {
			log.error(e.getMessage(),e);
		}
	}

	public WXBizMsgCrypt getBizMsgCrypt() {
		return bizMsgCrypt;
	}

}