package com.weixin.one.services.menu;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.weixin.one.config.WeiConfig;
import com.weixin.one.services.AccessTokenService;
import com.weixin.one.utils.Tool;

/**
 * @date 2016年12月28日下午3:35:43
 * @author hp
 *
 */
public class MenuService {
	
	private static final Logger log = LoggerFactory
			.getLogger(MenuService.class);
	
	/**
	 * 创建公众号自定义菜单
	 * @date 2016年12月28日下午3:53:16
	 * @return
	 * @author jq.yin@i-vpoints.com
	 */
	public static String createMenu(List<Map<String,String>> menuList){
		String url = WeiConfig.get("create.menu.url") + AccessTokenService.getAccess_token();
		log.info("access_token:{}",AccessTokenService.getAccess_token());
//		String param = " {\"button\":[{\"type\":\"click\",\"name\":\"今日歌曲\",\"key\":\"V1001_TODAY_MUSIC\"      },      {           \"name\":\"菜单\",           \"sub_button\":[           {               \"type\":\"view\",               \"name\":\"搜索\",               \"url\":\"http://www.soso.com/\"            },            {               \"type\":\"view\",               \"name\":\"视频\",               \"url\":\"http://v.qq.com/\"            },            {               \"type\":\"click\",               \"name\":\"赞一下我们\",               \"key\":\"V1001_GOOD\"            }]       }] }";
		
		
		
		// 请求参数
		String param = " {"
						+ "\"button\":["
						+ "{"
							+ "\"type\":\"click\","
							+ "\"name\":\"今日歌曲\","
							+ "\"key\":\"V1001_TODAY_MUSIC\""
						+ "},"
						+ "{"
							+ "\"name\":\"菜单\","
							+ "\"sub_button\":["
							+ "{"
								+ "\"type\":\"view\","
								+ "\"name\":\"搜索\","
								+ "\"url\":\"http://www.soso.com/\""
							+ "},"
							+ "{"
								+ "\"type\":\"view\","
								+ "\"name\":\"视频\","
								+ "\"url\":\"http://v.qq.com/\""
							+ "},"
							+ "{"
								+ "\"type\":\"click\","
								+ "\"name\":\"赞一下我们\","
								+ "\"key\":\"V1001_GOOD\""
							+ "}]"
						+ "}]"
						+ "}";
		String result = Tool.sendPost(url, param);
		log.info(result);
		return result;
	}
	
	/**
	 * 删除自定义菜单
	 * @date 2016年12月28日下午4:09:30
	 * @return
	 * @author jq.yin@i-vpoints.com
	 */
	public static String deleteMenu (){
		String url = WeiConfig.get("delete.menu.url") + AccessTokenService.getAccess_token();
		String result = Tool.urlGet(url);
		log.info(result);
		return result;
	}
	
	public static void main(String[] args) {
		// WeiConfig.init();
		createMenu(null);
		// deleteMenu();
	}
}
