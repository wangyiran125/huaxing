package org.chinalbs.logistics.utils;

import org.apache.axis.encoding.Base64;

public class Base64Utils {
	public static String encode(String s)
	{
		String encodStr = Base64.encode(s.getBytes());
		encodStr = encodStr.replaceAll("\\+", "-");
		encodStr = encodStr.replaceAll("/", "_");
		return encodStr;
	}
	public static String decode(String s)
	{
		s = s.replaceAll("-", "+");
		s = s.replaceAll("_", "/");
		return new String(Base64.decode(s));
	}
}
