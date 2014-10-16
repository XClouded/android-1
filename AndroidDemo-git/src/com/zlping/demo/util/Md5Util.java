package com.zlping.demo.util;

public class Md5Util {

	/**
	 * 获取md5加密串
	 * @param pwd
	 * @return
	 */
	public static String getMd5Pwd(String pwd) {
		MD5 static_MD5 = new MD5();
		return static_MD5.getMD5ofStr(pwd);
	}	
}
