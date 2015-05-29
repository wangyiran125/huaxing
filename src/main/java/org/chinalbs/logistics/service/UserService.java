package org.chinalbs.logistics.service;

import org.chinalbs.logistics.common.utils.ListSlice;
import org.chinalbs.logistics.domain.LogisticsUser;
import org.chinalbs.logistics.vo.RegisterInfo;
import org.chinalbs.logistics.vo.ResetPasswordInfo;
import org.chinalbs.logistics.vo.UserEditInfo;
import org.chinalbs.logistics.vo.UserInfo;
import org.chinalbs.logistics.vo.UserVipLevelInfo;

public interface UserService {

	public LogisticsUser login(String username, String password);
	public boolean logout();
	public boolean changePassword(Long userId, String oldPassword, String newPassword);
	public UserInfo findById(Long id);
	public LogisticsUser findLogisticsUserById(Long id);
	public void delete(Long id);
	public void deletePhysical(Long id);
	public LogisticsUser create(RegisterInfo registerInfo);
	public void update(UserEditInfo userEditInfo);
	public LogisticsUser findByUserName(String username);
	public ListSlice<UserInfo> findByType(int from, int max, int type, String name, String phone);
	public UserInfo createUser(UserInfo userInfo);
	public void updateUserVipLevel(UserVipLevelInfo userVipLevelInfo);
	public void forgetPassword(ResetPasswordInfo resetPasswordInfo);
	public long forgetPassword(String userToken);
	public boolean resetPassword(ResetPasswordInfo resetPasswordInfo);
	public void deleteOrActivateUser(Long userId);
}