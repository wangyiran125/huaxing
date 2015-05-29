package org.chinalbs.logistics.controller;


import javax.servlet.http.HttpServletRequest;

import org.chinalbs.logistics.annotation.OperationDefinition;
import org.chinalbs.logistics.common.json.Response;
import org.chinalbs.logistics.common.json.ResponseHelper;
import org.chinalbs.logistics.common.json.ReturnCode;
import org.chinalbs.logistics.common.utils.ResponseEntityUtils;
import org.chinalbs.logistics.service.UserService;
import org.chinalbs.logistics.utils.MessageDes;
import org.chinalbs.logistics.vo.ResetPasswordInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pwdreset")
public class PasswordController {

	@Autowired
	private UserService userService;
	
	 public static final String RESET_PASSWORD_FILE  = "../../pages/password_reset.html";
	 public static final String TOKEN_EXPIRED_FILE  = "../../pages/token_expired.html";

	@OperationDefinition(name = "用户申请忘记密码", anonymous = true)
    @RequestMapping(value = "", method = RequestMethod.POST)
	public Response<?> forgetPassword(@RequestBody ResetPasswordInfo resetPasswordInfo){
		userService.forgetPassword(resetPasswordInfo);
		return ResponseHelper.createSuccessResponse();
	}
	
	@OperationDefinition(name = "用户重置密码", anonymous = true)
	@RequestMapping(value = "", method = RequestMethod.PUT)
    public Response<?> resetPassword(@RequestBody ResetPasswordInfo resetPasswordInfo) {
    	if(!userService.resetPassword(resetPasswordInfo)){
    		return ResponseHelper.createResponse(ReturnCode.RESET_PASSWORD_TOKEN_EXPIRED, MessageDes.ResetPassword.RESET_PASSWORD_TOKEN_EXPIRED);
    	}
    	return ResponseHelper.createSuccessResponse();
    }
	
	@OperationDefinition(name = "用户访问重置密码页面", anonymous = true)
    @RequestMapping(value = "/{userToken}", method = RequestMethod.GET, produces = {MediaType.TEXT_HTML_VALUE})
	public ResponseEntity<?> forgetPassword(HttpServletRequest request, @PathVariable String userToken) throws Exception{
		long userId  = userService.forgetPassword(userToken);
		String url = TOKEN_EXPIRED_FILE;
		if(userId != 0){
			url = String.format("%s?t=%s", RESET_PASSWORD_FILE, userToken);
		}
		String body = String.format("若不能跳转请点击<a href='%s'>%s</a>", url, url);
		return ResponseEntityUtils.creatRedirectResponse(body, url);
	}
}
