package com.weixin.one.services;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.weixin.one.config.WeiConfig;
import com.weixin.one.utils.Tool;

/**
 * Tocken验证
 * 
 * @date 2016年12月26日上午11:09:18
 * @author hp
 * 
 */
public class TokenSignService {

	private static final Logger log = LoggerFactory
			.getLogger(TokenSignService.class);

	/**
	 * Token验证
	 * 
	 * @date 2016年12月26日下午3:01:18
	 * @param request
	 * @param response
	 * @author jq.yin@i-vpoints.com
	 */
	public static void sign(HttpServletRequest request,
			HttpServletResponse response) {

		PrintWriter out = null;
		try {
			
			out = response.getWriter();

			String signature = request.getParameter("signature") == null ? ""
					: request.getParameter("signature");
			String timestamp = request.getParameter("timestamp") == null ? ""
					: request.getParameter("timestamp");
			String nonce = request.getParameter("nonce") == null ? "" : request
					.getParameter("nonce");
			String echostr = request.getParameter("echostr") == null ? ""
					: request.getParameter("echostr");

			String[] arr = new String[] { WeiConfig.TOKEN, timestamp, nonce };
			Arrays.sort(arr);
			String str = Tool.SHA1(arr[0] + arr[1] + arr[2]);
			if (str.equals(signature)) {
				log.info("【TOKEN验证失败】");
				throw new RuntimeException("Token验证失败");
			}
			log.info("【TOKEN验证成功】");
			// 将echostr返回给微信
			out.println(echostr);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		} finally {
			out.flush();
			out.close();
		}
	}
}
