package org.chinalbs.logistics.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.axis.encoding.Base64;

public class MD5Utils {

	public static String getMD5(String orgStr) {
		String encodeStr = null;
		try {
			if (orgStr != null) {
				byte[] md5Bytes = MessageDigest.getInstance("MD5").digest(
						orgStr.getBytes("UTF-8"));
				StringBuffer hexValue = new StringBuffer();
				for (int i = 0; i < md5Bytes.length; i++) {
					int val = ((int) md5Bytes[i]) & 0xff;
					if (val < 16)
						hexValue.append("0");
					hexValue.append(Integer.toHexString(val));
				}
				encodeStr = hexValue.toString();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return encodeStr;
	}

	public static String getLongUpperMD5(String orgStr) {
		return getMD5(orgStr).toUpperCase();
	}

	public static String getLongLowerMD5(String orgStr) {
		return getMD5(orgStr).toLowerCase();
	}

	public static String getShortUpperMD5(String orgStr) {
		return getMD5(orgStr).toUpperCase().substring(8, 24);
	}

	public static String getShortLowerMD5(String orgStr) {
		return getMD5(orgStr).toLowerCase().substring(8, 24);
	}

	public static String Md5Base64(String str) {
		// 确定计算方法
		MessageDigest md5;
		try {
			md5 = MessageDigest.getInstance("MD5");
//			BASE64Encoder encode = new BASE64Encoder();
			return Base64.encode(md5.digest(str.getBytes()));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
		// 加密后的字符串
	}

	public static void main(String args[]) {
		String orgStr1 = "123456789";
		String md51 = getMD5(orgStr1);
		System.out.println("md51=" + md51);
		System.out.println(getMD5("162534coco"));

//		String orgStr2 = "一二三四五六七八九";
//		String md52 = getMD5(orgStr2);
//		System.out.println("md52=" + md52);
//
//		String orgStr3 = "";
//		String md53 = getMD5(orgStr3);
//		System.out.println("md53=" + md53);
//		System.out.println(System.currentTimeMillis());
//		Timestamp ts = new Timestamp(System.currentTimeMillis()); 
//		System.out.println(ts);
//		System.out.println("1234567890".subSequence(0, 7));
	}
	
	

}
