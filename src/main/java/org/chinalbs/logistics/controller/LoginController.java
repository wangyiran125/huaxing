package org.chinalbs.logistics.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.chinalbs.logistics.annotation.OperationDefinition;
import org.chinalbs.logistics.common.json.Response;
import org.chinalbs.logistics.common.json.ResponseHelper;
import org.chinalbs.logistics.common.utils.CommonUtils;
import org.chinalbs.logistics.common.utils.CookieUtils;
import org.chinalbs.logistics.common.utils.TokenUtils;
import org.chinalbs.logistics.domain.LogisticsUser;
import org.chinalbs.logistics.service.UserService;
import org.chinalbs.logistics.session.SessionInfo;
import org.chinalbs.logistics.utils.HTTPUtils;
import org.chinalbs.logistics.utils.MessageDes;
import org.chinalbs.logistics.vo.LoginInfo;
import org.chinalbs.logistics.vo.LoginReturnInfo;
import org.chinalbs.logistics.vo.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;


@RestController
public class LoginController {
	@Autowired
	private UserService userService;
	
	@OperationDefinition(name = "登录", anonymous = true)
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Response<LoginReturnInfo> login(@RequestBody LoginInfo loginInfo, HttpServletRequest request, HttpServletResponse response) {
        LogisticsUser user = userService.login(loginInfo.getUsername(), loginInfo.getPassword());
        if (user != null) {
	        return loginCommon(user, request, response);
        } else {
        	return ResponseHelper.createBusinessErrorResponse(MessageDes.Login.PASSWORD_ERROR);
        }
    }
    
	@OperationDefinition(name = "login for web",anonymous = true)
	@RequestMapping(value = "/loginforweb", method = RequestMethod.POST)
	public Response<LoginReturnInfo> loginForWeb(@RequestBody LoginInfo loginInfo, HttpServletRequest request, HttpServletResponse response){
		LogisticsUser user = userService.login(loginInfo.getUsername(), loginInfo.getPassword());
		if (user != null) {
			if (! CookieUtils.checkVerifyCodeCookie(loginInfo.getVcode(), request, response)) {
				return ResponseHelper.createBusinessErrorResponse(MessageDes.Login.VCODE_ERROR);
			}
	        return loginCommon(user, request, response);
        } else {
        	return ResponseHelper.createBusinessErrorResponse(MessageDes.Login.PASSWORD_ERROR);
        }
	}
	@OperationDefinition(name = "登出")
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public Response<?> logout(WebRequest webRequest,HttpServletRequest request, HttpServletResponse response) {
	    CookieUtils.cleanLoginCookie(request, response);
	    SessionInfo.setCurrent(null);
    	return ResponseHelper.createSuccessResponse();
    }
	
	public Response<LoginReturnInfo> loginCommon(LogisticsUser user, HttpServletRequest request, HttpServletResponse response){
		//凯步关爱 获得token
        LoginReturnInfo loginReturnInfo = new LoginReturnInfo();
        loginReturnInfo.setLogisticsToken(TokenUtils.generateToken(user.getId()));
        String capcareToken = HTTPUtils.getToken(user.getId());
        if (capcareToken == null ) {
        	capcareToken = "";
        }
        loginReturnInfo.setCapcareToken(capcareToken);

        
        CookieUtils.setLoginCookie(user.getId(), capcareToken, request, response);
    	
        SessionInfo info = new SessionInfo();
        info.getUserInfo().setLogin(true);
        info.getUserInfo().setUserId(user.getId());
        info.getUserInfo().setUsername(user.getUsername());
        info.getUserInfo().setRole(user.getRole());
        info.getUserInfo().setCapcareToken(capcareToken);
        SessionInfo.setCurrent(info);

        try {
			CommonUtils.fatherToChild(user, loginReturnInfo);
			UserInfo userInfo = userService.findById(user.getId());
			loginReturnInfo.setUserInfo(userInfo);
            loginReturnInfo.setPassword(null);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseHelper.createBusinessErrorResponse(MessageDes.INTERNAL_ERROR);
		}

        return ResponseHelper.createSuccessResponse(loginReturnInfo);
	}
	
}

