package com.weixin.one.controller;

import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @date 2016年12月28日下午4:15:46
 * @author hp
 * 
 */
@RequestMapping("/manage")
public class Manage {
	/**
	 * TODO
	 * 自定义菜单操作
	 * 
	 * @date 2016年12月28日下午4:12:44
	 * @param todo
	 *            创建、删除、查询
	 * @param button
	 * @author jq.yin@i-vpoints.com
	 */
	@RequestMapping("/menu")
	public void menu(String todo, String... button) {
		
	}
}
