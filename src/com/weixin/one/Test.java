package com.weixin.one;

import com.weixin.one.utils.UrlUtils;

/**
 * @date 2017年1月13日上午11:26:45
 * @author jq.yin@i-vpoints.com
 */
public class Test {
	public static void main(String[] args) {
		test01();
		/*Callable<String> cal = new Callable<String>() {

			@Override
			public String call() throws Exception {
				System.out.println(123);
				return "Hello";
			}
		};
		FutureTask<String> task = new FutureTask<String>(cal);
		new Thread(task).start();*/
	}

	/**
	 * @date 2017年3月7日下午2:55:13
	 * @author jq.yin@i-vpoints.com
	 */
	public static void test01() {
		String url = "http://fishplus.i-vpoints.com/fishplus/wechat/auth/userAuth";
		String param = "openid=o921QxGwyiXRxJ1SuTIktWnMitXk&TOKEN=F254C24536E8F319EB9C5C1BECFDEC96";
		String res = UrlUtils.sendHttpPost(url, param);
		System.out.println(res);
	}
	
}
