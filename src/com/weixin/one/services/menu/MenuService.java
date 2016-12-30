package com.weixin.one.services.menu;

import java.util.LinkedHashMap;
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
		// 请求参数
		// TODO
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
	
	public static String createMenu(){
		log.info("【开始创建自定义菜单】");
		String url = WeiConfig.get("create.menu.url") + AccessTokenService.getAccess_token();
		// 请求参数
		// TODO
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
						+ "},"
						+ "{"
						+ "\"type\":\"pic_weixin\","
						+ "\"name\":\"今日歌曲\","
						+ "\"key\":\"MEDIA_ID2\""
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
	
	public static String menuJson(List<LinkedHashMap<String, String>> menuList){
		String param = "" ;
		for(Map<String,String> map : menuList){
			String type = map.get("type");
			String sub_button = map.get("sub_button");
			/********************************************************/
			if(sub_button != null && type == null){
				param += ",\"name\":\"" + map.get("name") + "\","
						+ ",\"sub_button\":[" ;
			}else if(sub_button == null && type != null){
				param += menuType(map,type) ;
			}else{
				throw new RuntimeException("【未知异常】");
			}
			/********************************************************/
			// 拼接字符串
			param = menuType(map, type);
		}
		
		
		String menuJson = " {"
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
		menuJson += param;
		return menuJson;
	}

	/**
	 * @date 2016年12月30日上午11:55:55
	 * @param param
	 * @param map
	 * @param type
	 * @return
	 * @author jq.yin@i-vpoints.com
	 */
	private static String menuType(Map<String, String> map,
			String type) {
		String param = null;
		switch (type) {
		case "click":
			param = ",{"
					+ "\"type\":"+type+","
					+ "\"name\":"+map.get("name") +","
					+ "\"key\":"+map.get("key")
					+ "},";
			break;
		case "view":
			param = ",{"
					+ "\"type\":"+type+","
					+ "\"name\":"+map.get("name") +","
					+ "\"url\":"+map.get("url") + ",";
			break;
		case "scancode_waitmsg":
			param = ",{"
					+ "\"type\":"+type+","
					+ "\"name\":"+map.get("name") +","
					+ "\"key\":"+map.get("key") +","
					+ "\"sub_button\":"+map.get("sub_button")
					+ "},";
		case "scancode_push":
			break;
		case "pic_sysphoto":
			break;
		case "pic_photo_or_album":
			break;
		case "pic_weixin":
			break;
		case "media_id":
		case "view_limited":
			break;
		default:
			break;
		}
		return param;
	}
	
	/*public static void menuType (List<Map<String,String>> menuList){
		JSONArray array = new JSONArray();
		JSONObject jsonObj = null;
		for(Map<String,String> map : menuList){
			String type = map.get("type");
			String sub_button = map.get("sub_button");
			if(sub_button != null && type == null){
				
			}else if(sub_button == null && type != null){
				jsonObj = new JSONObject();
				jsonObj.put("", "");
				
			}else{
				throw new RuntimeException("【未知异常】");
			}
			jsonObj.put("", "");
		}
	}*/
}
