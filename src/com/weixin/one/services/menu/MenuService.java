package com.weixin.one.services.menu;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.weixin.one.config.WeiConfig;
import com.weixin.one.services.AccessTokenService;
import com.weixin.one.utils.Tool;

/**
 * FIXME
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
	public static String createMenu(List<Map<String,Object>> menuList){
		String url = WeiConfig.get("create_menu.url") + AccessTokenService.getAccess_token();
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
		String url = WeiConfig.get("create_menu.url") + AccessTokenService.getAccess_token();
		log.info(url);
		// 请求参数
		// TODO
		/*String param = " {"
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
						+ "}";*/
		String param = "{\"button\":["
						+ "{"
						+ "\"name\":\"扫码\","
						+ "\"sub_button\":["
							+ "{"
								+ "\"type\":\"scancode_waitmsg\","
								+ "\"name\":\"扫码带提示\","
								+ "\"key\":\"rselfmenu_0_0\""
							+ "},"
							+ "{"
								+ "\"type\":\"scancode_push\","
								+ "\"name\":\"扫码推事件\","
								+ "\"key\":\"rselfmenu_0_1\""
							+ "}"
						+ "]},"
						+ "{"
						+ "\"name\":\"发图\","
						+ "\"sub_button\":["
							+ "{"
								+ "\"type\":\"pic_sysphoto\","
								+ "\"name\":\"系统拍照发图\","
								+ "\"key\":\"rselfmenu_1_0\""
							+ "},{"
								+ "\"type\":\"pic_photo_or_album\","
								+ "\"name\":\"拍照或者相册发图\","
								+ "\"key\":\"rselfmenu_1_1\""
							+ "},{"
								+ "\"type\":\"pic_weixin\","
								+ "\"name\":\"微信相册发图\","
								+ "\"key\":\"rselfmenu_1_2\""
								+ "}"
							+ "]"
						+ "}"
						+ "]}";
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
		String url = WeiConfig.get("delete_menu.url") + AccessTokenService.getAccess_token();
		String result = Tool.urlGet(url);
		log.info(result);
		return result;
	}
	
	/**
	 * 查询自定义菜单
	 * @date 2017年1月3日下午4:06:56
	 * @return
	 * @author jq.yin@i-vpoints.com
	 */
	public static String getMenu(){
		String url = WeiConfig.get("get_menu.url") + AccessTokenService.getAccess_token();
		String result = Tool.urlGet(url);
		log.info(result);
		return result;
	}
	
	
	public static String menuJson(List<Map<String,Object>> munuList){
		// FIXME
		String param = "" ;
//		for(Object object : munuList){
			/*JSONObject jsonObject = (JSONObject) object;
			Object sub_button = jsonObject.get("sub_button");
			String type = jsonObject.getString("type");
			if(sub_button != null && type == null){
				//二级菜单
				JSONArray array = (JSONArray) sub_button;
				
				array.add("");
				
				menuType(jsonObject,type);
			}else if(sub_button == null && type != null){
				//一级菜单
				menuType(jsonObject,type);
			}else{
				throw new RuntimeException("【未知异常】");
			}*/
			/************************/
			
			/*String type = map.get("type");
			String sub_button = map.get("sub_button");
			// FIXME
			*/
			/********************************************************//*
			if(sub_button != null && type == null){
				param += ",\"name\":\"" + map.get("name") + "\","
						+ ",\"sub_button\":[" ;
			}else if(sub_button == null && type != null){
				param += menuType(map,type) ;
			}else{
				throw new RuntimeException("【未知异常】");
			}*/
			/********************************************************/
			// 拼接字符串
			// param = menuType(jsonObject, type);
//		}
		
		
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
	@SuppressWarnings("unused")
	private static String menuType(Map<String,String> map,
			String type) {
		JSONObject jsonObject = new JSONObject();
		String param = null;
		switch (type) {
		case "click":
			jsonObject.put("type", type);
			jsonObject.put("name", map.get("name"));
			jsonObject.put("key", map.get("key"));
			break;
		case "view":
			jsonObject.put("type", type);
			jsonObject.put("name", map.get("name"));
			jsonObject.put("url", map.get("url"));
			break;
		case "scancode_waitmsg":
			jsonObject.put("type", type);
			jsonObject.put("name", map.get("name"));
			jsonObject.put("key", map.get("key"));
		case "scancode_push":
			jsonObject.put("type", type);
			jsonObject.put("name", map.get("name"));
			jsonObject.put("key", map.get("key"));
			break;
		case "pic_sysphoto":
			jsonObject.put("type", type);
			jsonObject.put("name", map.get("name"));
			jsonObject.put("key", map.get("key"));
			break;
		case "pic_photo_or_album":
			jsonObject.put("type", type);
			jsonObject.put("name", map.get("name"));
			jsonObject.put("key", map.get("key"));
			break;
		case "pic_weixin":
			jsonObject.put("type", type);
			jsonObject.put("name", map.get("name"));
			jsonObject.put("key", map.get("key"));
			break;
		case "media_id":
			jsonObject.put("type", type);
			jsonObject.put("name", map.get("name"));
			jsonObject.put("media_id", map.get("media_id"));
		case "view_limited":
			jsonObject.put("type", type);
			jsonObject.put("name", map.get("name"));
			jsonObject.put("media_id", map.get("media_id"));
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
	
	public static void main(String[] args) {
		// WeiConfig.init();
		createMenu();
		// deleteMenu();
	}
}
