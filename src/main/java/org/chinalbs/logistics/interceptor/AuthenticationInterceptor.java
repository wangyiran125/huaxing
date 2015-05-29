package org.chinalbs.logistics.interceptor;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.chinalbs.logistics.ObjectMapperHolder;
import org.chinalbs.logistics.annotation.OperationDefinition;
import org.chinalbs.logistics.common.json.Response;
import org.chinalbs.logistics.common.json.ResponseHelper;
import org.chinalbs.logistics.common.json.ReturnCode;
import org.chinalbs.logistics.common.utils.CookieUtils;
import org.chinalbs.logistics.service.UserService;
import org.chinalbs.logistics.session.SessionInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.chinalbs.logistics.common.utils.TokenUtils;
import org.chinalbs.logistics.config.PathConfig;
import org.chinalbs.logistics.domain.LogisticsUser;

@Component
public class AuthenticationInterceptor extends HandlerInterceptorAdapter {

	private static final Logger logger = LoggerFactory.getLogger(AuthenticationInterceptor.class);
	
	@Autowired
	private UserService userService;
	
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		

		logger.info("remoteAddr = "+ request.getRemoteAddr()+", url ="+request.getRequestURI());
		
		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod)handler;
			
			boolean anonymous = false;
			if (isAnonymous(handlerMethod)) {
                anonymous = true;
			}

			Long userId = achiveUserId(request, response);
			if (userId == null && !anonymous) {
                responseNoLogin(response);
                return false;
			}
			else if (userId == null && anonymous) {
				SessionInfo.setCurrent(null);
				return true;
			}

			
			LogisticsUser user = userService.findLogisticsUserById(userId);
			if (user == null) {
			    CookieUtils.cleanLoginCookie(request, response);
			    SessionInfo.setCurrent(null);
			    return false;
			}
			if (!anonymous && !PathConfig.INSTANCE.getLoginPermission().contains(user.getRole()+"")) {
				responseNoPermission(response);
				return false;
			}
			
			Long lastUpdateTime = achiveLastUpdateTime(request);
			if (lastUpdateTime == null) {
				lastUpdateTime = 0l;
			}

			String capcareToken  = achiveCapcareToken(request);
			if (capcareToken == null) {
				capcareToken = "";
			}
			
	        SessionInfo info = new SessionInfo();
	        info.getUserInfo().setUserId(userId);
            info.getUserInfo().setUsername(user.getUsername());
	        info.setLastUpdateTime(lastUpdateTime);
            info.getUserInfo().setRole(user.getRole());
            info.getUserInfo().setCapcareToken(capcareToken);
	        SessionInfo.setCurrent(info);
    		logger.info("token verify successfully : " + handler);
//			String identity = ReflectionUtils.toSimpleSignature(handlerMethod.getMethod());
//			
//			Set<Role> roles = SessionInfo.getCurrent().getAuthInfo().getRoles();
//			
//			boolean hasPermission = authorizationService.hasPermissionByOperation(roles, identity);
//						
//			if (!hasPermission) {
//				responseNoPermission(response);
//				return false;
//			}
		} else {
			logger.warn("find a non-HandlerMethod handler : " + handler);
		}
		return true;
	}
	
	private void responseNoPermission(HttpServletResponse response) {
		Response<?> rsp = ResponseHelper.createResponse(ReturnCode.NO_PERMISSION, "没有访问权限，请使用其他用户登录!");
		try {
			response.getOutputStream().write(ObjectMapperHolder.getInstance().getMapper().writeValueAsBytes(rsp));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void responseNoLogin(HttpServletResponse response) {
		Response<?> rsp = ResponseHelper.createResponse(ReturnCode.INVALID_LOGIN_INFO, "用户无效，请先登录。");
		try {
			response.getOutputStream().write(ObjectMapperHolder.getInstance().getMapper().writeValueAsBytes(rsp));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private boolean isAnonymous(HandlerMethod handlerMethod) {
		OperationDefinition od = handlerMethod.getMethod().getAnnotation(OperationDefinition.class);

		return od != null && od.anonymous();
	}
	
	private Long achiveUserId(HttpServletRequest request, HttpServletResponse response) {
		Long userId = null;
		if (request.getHeader(CookieUtils.LOGIN_COOKIE_NAME) != null) {
			//用户认证 from header
			logger.debug("It is phone user : ");
			userId = TokenUtils.getUserIdFromToken(request.getHeader(CookieUtils.LOGIN_COOKIE_NAME));
    		if (userId == null) {
    			logger.error("token verify failed : ");
    			responseNoPermission(response);
    		}
		}
		else if (CookieUtils.getToken(request) != null) {
			//用户认证 from cookie
			logger.debug("It is web user : ");
    		userId = TokenUtils.getUserIdFromToken(CookieUtils.getToken(request));
    		if (userId == null) {
    			logger.error("token verify failed : ");
    			responseNoPermission(response);
    		}
		}
		else {
			//未登录用户
			logger.error("no login user : ");
		}
		return userId;
	}
	
	private Long achiveLastUpdateTime(HttpServletRequest request) {
		Long lastUpdateTime = null;
		if (request.getHeader(CookieUtils.LAST_UPDATE_TIME) != null) {
			//lastUpdateTime from header
			try {
				lastUpdateTime = Long.parseLong(request.getHeader(CookieUtils.LAST_UPDATE_TIME));
			}
			catch (Exception e) {
				
			}
		}
		else if (CookieUtils.getToken(request) != null) {
			//lastUpdateTime from cookie
			try {
				lastUpdateTime = Long.parseLong(CookieUtils.getCookie(CookieUtils.LAST_UPDATE_TIME, request));
			}
			catch (Exception e) {					
			}
		}
		return lastUpdateTime;
	}
	
	
	private String achiveCapcareToken(HttpServletRequest request) {
		String capcareToken = null;
		if (request.getHeader(CookieUtils.CAPCARE_COOKIE_NAME) != null) {
			//lastUpdateTime from header
			try {
				capcareToken = request.getHeader(CookieUtils.CAPCARE_COOKIE_NAME);
			}
			catch (Exception e) {
				
			}
		}
		else if (CookieUtils.getToken(request) != null) {
			//lastUpdateTime from cookie
			try {
				capcareToken = CookieUtils.getCookie(CookieUtils.CAPCARE_COOKIE_NAME, request);
			}
			catch (Exception e) {					
			}
		}
		return capcareToken;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception
			ex) throws Exception {
			SessionInfo.setCurrent(null);
		}
}
