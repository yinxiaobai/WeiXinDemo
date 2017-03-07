package com.weixin.one;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @date 2017年3月6日上午10:26:20
 * @author jq.yin@i-vpoints.com
 */
public class ConnTest {
	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		Connection con = DriverManager.getConnection("jdbc:oracle:thin:@192.168.0.161:1521/orcl", "bcmis", "bcmis");
//		con.setAutoCommit(false);
		System.out.println(">>>>>>>>>>>>>>>>>>>"+con.getAutoCommit());
		con.createStatement();
		con.close();
	}
}
