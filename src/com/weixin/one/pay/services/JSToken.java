package com.weixin.one.pay.services;

import com.alibaba.fastjson.JSONObject;
import com.weixin.one.services.AccessTokenService;
import com.weixin.one.utils.UrlUtils;

/**
 * @date 2017年1月13日上午10:18:10
 * @author jq.yin@i-vpoints.com
 */
public class JSToken {
	public static void main(String[] args) {
		// WeiConfig.init();
		String accessTonek = AccessTokenService.getInstance().getAccess_token();
		accessTonek = "vfXXOIegbdVZ1fHCbheqAirdcGETj3xJpIHkPB7kOyInDcBIpKV6U4BnJhg2QyMeATrLPuCHgXknaQO70s_fCTJg34BeOJCNVSbJ69hfxrmXyfGWlQl3Xsw2lNeiIVN_KIEeAGAYWS";
		String url = "http://api.weixin.qq.com/cgi-bin/ticket/getticket?type=jsapi&access_token="+accessTonek+"";
		String jsToken = UrlUtils.sendGet(url);
		JSONObject jsonObject = (JSONObject) JSONObject.parse(jsToken);
		jsonObject.get("ticket");
		System.out.println(jsToken);
		System.out.println("ticket:"+jsonObject.get("ticket"));
	}
}
