package org.chinalbs.logistics.common.utils;

import java.security.SecureRandom;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class VerifyCodeUtils {
    public static void setVerifyCode(String code, HttpServletRequest request, HttpServletResponse response) {
        CookieUtils.setVerifyCodeCookie(code, request, response);
    }
    
    public static boolean checkVerifyCode(String code, HttpServletRequest request, HttpServletResponse response) {
        boolean valid = CookieUtils.checkVerifyCodeCookie(code, request, response);
        //CookieUtils.cleanVerifyCodeCookie(request, response);
        return valid;
    }
    
    public static void cleanVerifyCode(HttpServletRequest request, HttpServletResponse response) {
        CookieUtils.cleanVerifyCodeCookie(request, response);
    }
    
    public static StringBuilder getRandomVerifyCode(int size) {
    	Random rand = new SecureRandom();
		StringBuilder vcode = new StringBuilder();
        String aCode = null;
        
        for (int i=0;i<size;i++)
        {
            aCode = String.valueOf(rand.nextInt(10));
            vcode.append(aCode);
        }
        
        return vcode;
	}
}
