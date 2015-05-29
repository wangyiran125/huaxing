package org.chinalbs.logistics.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.chinalbs.logistics.annotation.OperationDefinition;
import org.chinalbs.logistics.common.domain.BaseDict;
import org.chinalbs.logistics.common.json.Response;
import org.chinalbs.logistics.common.json.ResponseHelper;
import org.chinalbs.logistics.common.json.ReturnCode;
import org.chinalbs.logistics.common.utils.ListSlice;
import org.chinalbs.logistics.config.PathConfig;
import org.chinalbs.logistics.domain.LogisticsUser;
import org.chinalbs.logistics.error.CodeException;
import org.chinalbs.logistics.service.UserService;
import org.chinalbs.logistics.session.SessionInfo;
import org.chinalbs.logistics.utils.Consts;
import org.chinalbs.logistics.utils.HTTPUtils;
import org.chinalbs.logistics.utils.MessageDes;
import org.chinalbs.logistics.vo.PasswordInfo;
import org.chinalbs.logistics.vo.RegisterInfo;
import org.chinalbs.logistics.vo.UserEditInfo;
import org.chinalbs.logistics.vo.UserInfo;
import org.chinalbs.logistics.vo.UserVipLevelInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final Map<String, String> ROLE_DICT = new HashMap<String, String>();
    static {
        ROLE_DICT.put("1", "管理员");
        ROLE_DICT.put("2", "仓库主");
        ROLE_DICT.put("3", "货主");
        ROLE_DICT.put("4", "车主");
        ROLE_DICT.put("5", "司机");
    }
    private static final List<BaseDict> ROLE_LIST = new ArrayList<BaseDict>();
    static {
        String[] roles  = PathConfig.INSTANCE.getLoginPermission().split(",");
        for (int i = 0; i < roles.length; i++) {
            String name = ROLE_DICT.get(roles[i]);
            if (name != null) {
                BaseDict role = new BaseDict();
                role.setCode(roles[i]);
                role.setName(name);
                ROLE_LIST.add(role);
            }
        }
    }

	@Autowired
	private UserService userService;

	@OperationDefinition(name = "注册用户", anonymous = true)
    @RequestMapping(value = "", method = RequestMethod.POST)
	public Response<LogisticsUser> create(@RequestBody RegisterInfo registerInfo){
		LogisticsUser created = userService.create(registerInfo);
		if (created.getRole() == Consts.Role.TRUCKOWNER && !HTTPUtils.addUser2(created.getId(),registerInfo)) {
			//凯步关爱 添加用户
			userService.deletePhysical(created.getId());
			throw new CodeException(ReturnCode.CAPCARE_EXCEPTION, MessageDes.User.CAPCARE_USER_CREATE_ERROR);
		}
		return ResponseHelper.createSuccessResponse(created);
	}
	
	@OperationDefinition(name = "查看用户名是否存在", anonymous = true)
	@RequestMapping(value = "", method = RequestMethod.GET)
	public Response<?> verifyUsername(@RequestParam String username){
		LogisticsUser user = userService.findByUserName(username);
		if (user != null) {
			return ResponseHelper.createResponse(ReturnCode.USERNAME_EXIST, MessageDes.User.USER_DUPLICATE);
		}
		return ResponseHelper.createSuccessResponse();
	}
	
	@OperationDefinition(name = "修改个人信息")
    @RequestMapping(value = "", method = RequestMethod.PUT)
	public Response<?> update(@RequestBody UserEditInfo userEditInfo) {
		userService.update(userEditInfo);
		return ResponseHelper.createSuccessResponse();
	}
	
	
	@OperationDefinition(name = "获取用户详细信息")
    @RequestMapping(value = "/userinfo", method = RequestMethod.GET)
    public Response<UserInfo> view() {
		long userId = SessionInfo.getCurrent().getUserInfo().getUserId();
        return ResponseHelper.createSuccessResponse(userService.findById(userId));
    }
	
	@OperationDefinition(name = "根据用户Id获取用户详细信息")
    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public Response<UserInfo> getUserInfo(@PathVariable long userId) {
        return ResponseHelper.createSuccessResponse(userService.findById(userId));
    }
	
	
	@OperationDefinition(name = "修改自己密码")
	@RequestMapping(value = "password", method = RequestMethod.PUT)
    public Response<?> changePassword(@RequestBody PasswordInfo passwordInfo) {
    	long userId = SessionInfo.getCurrent().getUserInfo().getUserId();
    	boolean changed = userService.changePassword(userId, passwordInfo.getOldPassword(), passwordInfo.getNewPassword());
    	if (changed) {
    		return ResponseHelper.createSuccessResponse();
    	} else {
    		return ResponseHelper.createBusinessErrorResponse(MessageDes.User.ORIGINAL_PASSWORD_ERROR);
    	}
    }
    
	@OperationDefinition(name = "按类型获取用户详情")
    @RequestMapping(value = "", params={"from", "max"}, method = RequestMethod.GET)
    public Response<ListSlice<UserInfo>> getUsersByType(@RequestParam int from, @RequestParam int max, int type, String name, String phone) {
		ListSlice<UserInfo> infos = userService.findByType(from, max, type, name, phone);
        return ResponseHelper.createSuccessResponse(infos);
    }

    @OperationDefinition(name = "获取有效角色列表")
    @RequestMapping(value = "/roles", method = RequestMethod.GET)
    public Response<List<BaseDict>> roles() {
        return ResponseHelper.createSuccessResponse(ROLE_LIST);
    }
	
	@OperationDefinition(name = "管理员创建用户", anonymous = true)
    @RequestMapping(value = "/add", method = RequestMethod.POST)
	public Response<UserInfo> createUser(@RequestBody UserInfo userInfo){
		UserInfo info = userService.createUser(userInfo);
		return ResponseHelper.createSuccessResponse(info);
	}
	
	@OperationDefinition(name = "修改个人信息")
    @RequestMapping(value = "/truckowner/viplevel", method = RequestMethod.PUT)
	public Response<?> updateUserVipLevel(@RequestBody UserVipLevelInfo userVipLevelInfo) {
		userService.updateUserVipLevel(userVipLevelInfo);
		return ResponseHelper.createSuccessResponse();
	}
	
	@OperationDefinition(name = "禁用或激活用户")
	@RequestMapping(value = "/{userid}", method = RequestMethod.DELETE)
	public Response<?> deleteOrActivateUser(@PathVariable Long userid){
		userService.deleteOrActivateUser(userid);
		return ResponseHelper.createSuccessResponse();
	}
}
