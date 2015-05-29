package org.chinalbs.logistics.common.utils;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CookieUtils {
    private static Logger logger = LoggerFactory.getLogger(CookieUtils.class);
    //js中也使用这个常量，要同时修改
    public static final String LOGIN_COOKIE_NAME = "LogisticsToken";
    public static final String CAPCARE_COOKIE_NAME = "CapcareToken";
    public static final String LAST_UPDATE_TIME = "LastUpdateTime";
    public static final String VERIFY_CODE_COOKIE_NAME = "VERIFY_CODE";
    public static final String RESETPASS_COOKIE_NAME = "MDESK_RAC";
    public static final String REGIST_MOBI_VERIFY_NAME = "REG_VERIFY";
    private static final long VERIFY_CODE_EXPIRE = 1000 * 60 * 10;
    public static String getCookie(String name, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) return null;
        for (Cookie c : cookies) {
            if (name.equals(c.getName())) {
                logger.debug("found cookie for {}, value is {}", name, c.getValue());
                return c.getValue();
            }
        }
        logger.debug("didn't find cookie for name, {} ", name);
        return null;
    }
    
    public static String getToken(HttpServletRequest request) {
        return getCookie(LOGIN_COOKIE_NAME, request);
    }
    
    public static Long getUserId(HttpServletRequest request) {
        return TokenUtils.getUserIdFromToken(getToken(request));
    }
    
    public static String getContextPath(HttpServletRequest request) {
    	return request.getContextPath()+"/";
    }
    
    public static void setLoginCookie(Long userId, String capcareToke, HttpServletRequest request, HttpServletResponse response) {
        String token = TokenUtils.generateToken(userId);
        Cookie cookie = new Cookie(LOGIN_COOKIE_NAME, token);
        Cookie capcarecookie = new Cookie(CAPCARE_COOKIE_NAME, capcareToke);
        cookie.setPath(getContextPath(request));
        capcarecookie.setPath(getContextPath(request));
        response.addCookie(cookie);
        response.addCookie(capcarecookie);
    }
    
    public static void setResetPasswordCookie(Long userId, HttpServletRequest request, HttpServletResponse response) {
        String token = TokenUtils.generateToken(userId);
        Cookie cookie = new Cookie(RESETPASS_COOKIE_NAME, token);
        cookie.setPath(getContextPath(request));
        response.addCookie(cookie);
    }
    
    public static Long getUserIdFromResetPassCookie(HttpServletRequest request) {
    	String token = getCookie(RESETPASS_COOKIE_NAME, request);
        return TokenUtils.getUserIdFromToken(token);
    }
    
    public static void cleanResetPassCookie(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = new Cookie(RESETPASS_COOKIE_NAME, null);
        cookie.setMaxAge(0);
        cookie.setPath(getContextPath(request));
        response.addCookie(cookie);
    }
    
    public static void cleanLoginCookie(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = new Cookie(LOGIN_COOKIE_NAME, null);
        cookie.setMaxAge(0);
        cookie.setPath(getContextPath(request));
        Cookie capcareCookie = new Cookie(CAPCARE_COOKIE_NAME, null);
        capcareCookie.setMaxAge(0);
        capcareCookie.setPath(getContextPath(request));
        response.addCookie(cookie);
        response.addCookie(capcareCookie);
    }
    
    public static void setCookie(String name, String value, HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath(getContextPath(request));
        response.addCookie(cookie);
    }    
    
    public static void cleanCookie(String name, HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = new Cookie(name, null);
        cookie.setMaxAge(0);
        cookie.setPath(getContextPath(request));
        response.addCookie(cookie);
    }
    
    public static void setVerifyCodeCookie(String code, HttpServletRequest request, HttpServletResponse response) {
        try {
            long currentTime = System.currentTimeMillis();
            String md5 = MD5.getMD5((currentTime + code + KeyProvider.INSTANCE.getVerifyCodeKey()).getBytes("UTF-8"));
            setCookie(VERIFY_CODE_COOKIE_NAME, currentTime + ":" + md5, request, response);
        } catch (UnsupportedEncodingException e) {  }
    }
    
    public static void setRegistVerifyCookie(String mdn, String code, HttpServletRequest request, HttpServletResponse response) {
        try {
            long currentTime = System.currentTimeMillis();
            String md5 = MD5.getMD5((currentTime + mdn + code + KeyProvider.INSTANCE.getVerifyCodeKey()).getBytes("UTF-8"));
            setCookie(REGIST_MOBI_VERIFY_NAME, currentTime + ":" + md5, request, response);
        } catch (UnsupportedEncodingException e) {  }
    }
    
    public static boolean checkVerifyCodeCookie(String code, HttpServletRequest request, HttpServletResponse response) {
        String md5 = null;
        try {
            String cookie = getCookie(VERIFY_CODE_COOKIE_NAME, request);
            long timeStamp = Long.parseLong(cookie.substring(0, cookie.indexOf(":")));
            md5 = MD5.getMD5((timeStamp + code + KeyProvider.INSTANCE.getVerifyCodeKey()).getBytes("UTF-8"));
            if ((timeStamp + ":" + md5).equals(cookie) && System.currentTimeMillis() - timeStamp < VERIFY_CODE_EXPIRE) {
                return true;
            }
        } catch (Exception e) {  }
        return false;
    }
    
    public static boolean checkRegistVerifyCookie(String mdn, String code, HttpServletRequest request) {
        String md5 = null;
        try {
            String cookie = getCookie(REGIST_MOBI_VERIFY_NAME, request);
            long timeStamp = Long.parseLong(cookie.substring(0, cookie.indexOf(":")));
            md5 = MD5.getMD5((timeStamp + mdn + code + KeyProvider.INSTANCE.getVerifyCodeKey()).getBytes("UTF-8"));
            if ((timeStamp + ":" + md5).equals(cookie) && System.currentTimeMillis() - timeStamp < VERIFY_CODE_EXPIRE) {
                return true;
            }
        } catch (Exception e) {  }
        return false;
    }
    
    public static void cleanVerifyCodeCookie(HttpServletRequest request, HttpServletResponse response) {
        cleanCookie(VERIFY_CODE_COOKIE_NAME, request, response);
    }
    
    public static void cleanRegistVerifyCookie(HttpServletRequest request, HttpServletResponse response) {
        cleanCookie(REGIST_MOBI_VERIFY_NAME, request, response);
    }
    
}
