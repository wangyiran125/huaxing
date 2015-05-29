package org.chinalbs.logistics.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.http.util.TextUtils;
import org.chinalbs.logistics.common.json.ReturnCode;
import org.chinalbs.logistics.common.utils.ListSlice;
import org.chinalbs.logistics.domain.Admin;
import org.chinalbs.logistics.domain.Driver;
import org.chinalbs.logistics.domain.GoodsOwner;
import org.chinalbs.logistics.domain.LogisticsUser;
import org.chinalbs.logistics.domain.ResetPassword;
import org.chinalbs.logistics.domain.Truck;
import org.chinalbs.logistics.domain.TruckOwner;
import org.chinalbs.logistics.domain.UserCommon;
import org.chinalbs.logistics.error.CodeException;
import org.chinalbs.logistics.repository.AdminRepository;
import org.chinalbs.logistics.repository.DriverRepository;
import org.chinalbs.logistics.repository.GoodsOwnerRepository;
import org.chinalbs.logistics.repository.LogisticsUserRepository;
import org.chinalbs.logistics.repository.ResetPasswordRepository;
import org.chinalbs.logistics.repository.ScoreRepository;
import org.chinalbs.logistics.repository.TruckOwnerRepository;
import org.chinalbs.logistics.repository.TruckRepository;
import org.chinalbs.logistics.service.UserService;
import org.chinalbs.logistics.session.SessionInfo;
import org.chinalbs.logistics.utils.Consts;
import org.chinalbs.logistics.utils.MD5Utils;
import org.chinalbs.logistics.utils.MailUtils;
import org.chinalbs.logistics.utils.MessageDes;
import org.chinalbs.logistics.utils.SimplePageable;
import org.chinalbs.logistics.utils.ThreadPoolUtils;
import org.chinalbs.logistics.vo.RegisterInfo;
import org.chinalbs.logistics.vo.ResetPasswordInfo;
import org.chinalbs.logistics.vo.UserEditInfo;
import org.chinalbs.logistics.vo.UserInfo;
import org.chinalbs.logistics.vo.UserVipLevelInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	
	@Autowired
	private LogisticsUserRepository logisticsUserRepository;
	
	@Autowired
	private GoodsOwnerRepository goodsOwnerRepository;
	
	@Autowired
	private TruckOwnerRepository truckOwnerRepository;
	
	@Autowired
	private DriverRepository driverRepository;
	
	@Autowired
	private TruckRepository truckRepository;

    @Autowired
    private AdminRepository adminRepository;

	@Autowired
	private ScoreRepository scoreRepository;
	@Autowired
	private ResetPasswordRepository resetPasswordRepository;
	
	@Override
	public LogisticsUser login(String username, String password) {
		LogisticsUser user = logisticsUserRepository.findOneByUsernameAndPassword(username, MD5Utils.getMD5(password));
        if (user != null) {
        	if (user.getIsDeleted() == Consts.DELETED) {
				throw new CodeException(ReturnCode.USER_WAS_FORBDDEN, MessageDes.Login.USER_WAS_FORBDDEN);
			}
        	if (user.getRole() == Consts.Role.DRIVER) {
        		Driver driver = driverRepository.findOnebyUserId(user.getId());
				LogisticsUser parentUser = logisticsUserRepository.findOne(driver.getParentUserId());
				if (parentUser != null && parentUser.getIsDeleted() == Consts.DELETED) {
					throw new CodeException(ReturnCode.USER_WAS_FORBDDEN, MessageDes.Login.USER_WAS_FORBDDEN);
				}
			}
            user.setLastLoginTime(new java.sql.Date(System.currentTimeMillis()));
            logisticsUserRepository.save(user);
        }
		return user;
	}

	@Override
	public boolean logout() {
		return false;
	}

	@Override
	public boolean changePassword(Long userId, String oldPassword, String newPassword) {
		LogisticsUser user = logisticsUserRepository.findOne(userId);
		if (!user.getPassword().equals(MD5Utils.getMD5(oldPassword))) {
			return false;
		}
		user.setPassword(MD5Utils.getMD5(newPassword));
		logisticsUserRepository.save(user);
		return true;
	}


	/*
	 * 创建用户(包括司机)
	 */
	@Override
	public LogisticsUser create(RegisterInfo registerInfo) {
        if (registerInfo.getUsername() == null || registerInfo.getPassword() == null 
        		|| registerInfo.getRole() == 0 ) {
        	//输入信息校验
            throw new CodeException(ReturnCode.INVALID_PARAMETER, MessageDes.PARAM_ERROR );
        }
        if (logisticsUserRepository.findOneByUsername(registerInfo.getUsername()) != null) {
        	//用户名重复
            throw new CodeException(ReturnCode.USERNAME_EXIST, MessageDes.User.USER_DUPLICATE );
        }
        if (registerInfo.getRole() < 1 || registerInfo.getRole() > 5) {
            throw new CodeException(ReturnCode.INVALID_PARAMETER, MessageDes.User.ROLE_ERROR);
        }
		LogisticsUser logisticsUser = new LogisticsUser();
		logisticsUser.setUsername(registerInfo.getUsername());
		logisticsUser.setPassword(MD5Utils.getMD5(registerInfo.getPassword()));
		logisticsUser.setRegisterTime(new Date());
		logisticsUser.setRole(registerInfo.getRole());
		logisticsUser = logisticsUserRepository.save(logisticsUser);
		if (registerInfo.getRole() == Consts.Role.ADMIN) {
			LogisticsUser user = logisticsUserRepository.findOne(SessionInfo.getCurrent().getUserInfo().getUserId());
			if(user.getRole() != Consts.Role.ADMIN){
				throw new CodeException(ReturnCode.NO_PERMISSION, MessageDes.User.NO_DRIVER_PERMINSION);
			}
			Admin admin = makeAdmin(new Admin(), registerInfo);
			admin.setUserId(logisticsUser.getId());
			adminRepository.save(admin);
		}else if (registerInfo.getRole() == Consts.Role.TRUCKOWNER) {
			TruckOwner truckOwner = makeTruckOwner(new TruckOwner(), registerInfo);
			truckOwner.setUserId(logisticsUser.getId());
			truckOwnerRepository.save(truckOwner);
		}else if (registerInfo.getRole() == Consts.Role.GOODSOWNER 
				|| registerInfo.getRole() == Consts.Role.WAREHOUSEOWNER) {
			GoodsOwner goodsOwner = makeGoodsOwner(new GoodsOwner(), registerInfo);
			goodsOwner.setUserId(logisticsUser.getId());
			goodsOwnerRepository.save(goodsOwner);
		}
		else if (registerInfo.getRole() == Consts.Role.DRIVER ) {
			Driver driver = makeDriver(new Driver(), registerInfo);
			driver.setUserId(logisticsUser.getId());
			driver.setParentUserId(SessionInfo.getCurrent().getUserInfo().getUserId());
			driverRepository.save(driver);
		}
		return logisticsUser;
	}

	@Override
	public void update(UserEditInfo userEditInfo) {		
		LogisticsUser user = logisticsUserRepository.findOne(SessionInfo.getCurrent().getUserInfo().getUserId());
		//user.setUsername(userEditInfo.getUsername()); //用户名不允许修改
//		logisticsUserRepository.save(user);
		if (user != null) {
			if (user.getRole() == Consts.Role.ADMIN) {
				Admin admin = adminRepository.findOneByUserId(user.getId());
				adminRepository.save(makeAdmin(admin, userEditInfo));
			}else if (user.getRole() == Consts.Role.TRUCKOWNER) {
				TruckOwner truckOwner = truckOwnerRepository.findOneByUserId(user.getId());
				truckOwnerRepository.save(makeTruckOwner(truckOwner, userEditInfo));
			}else if (user.getRole() == Consts.Role.GOODSOWNER || user.getRole() == Consts.Role.WAREHOUSEOWNER) {
				GoodsOwner goodsOwner = goodsOwnerRepository.findOneByUserId(user.getId());
				goodsOwnerRepository.save(makeGoodsOwner(goodsOwner, userEditInfo));
			} else if (user.getRole() == Consts.Role.DRIVER) {
                Driver driver = driverRepository.findOnebyUserId(user.getId());
                driverRepository.save(makeDriver(driver, userEditInfo));
            }
		}
	}
	
	public LogisticsUser findLogisticsUserById(Long id) {
		return logisticsUserRepository.findOne(id);
	}
	@Override
	public UserInfo findById(Long id) {
		LogisticsUser user = logisticsUserRepository.findOne(id);
		UserInfo userInfo = null;
		if (user != null) {
//			userInfo.setUsername(user.getUsername());
//			userInfo.setRole(user.getRole());
			if (user.getRole() == Consts.Role.TRUCKOWNER) {
				TruckOwner truckOwner = truckOwnerRepository.findOneByUserId(user.getId());
//				userInfo.setName(truckOwner.getName());
//				userInfo.setIdCard(truckOwner.getIdCard());
//				userInfo.setMobile(truckOwner.getMobile());
//				userInfo.setPhone(truckOwner.getPhone());
//				userInfo.setEmail(truckOwner.getEmail());
//				userInfo.setQq(truckOwner.getQq());
//				userInfo.setAvatar(truckOwner.getAvatar());
//				userInfo.setTruckLabel(truckOwner.getTruckLabel());
				userInfo = makeUserInfo(truckOwner, user);
			}else if (user.getRole() == Consts.Role.DRIVER) {
				Driver driver = driverRepository.findOnebyUserId(user.getId());
				userInfo = makeUserInfo(driver, user);
			}
			
			else if(user.getRole() == Consts.Role.ADMIN){
				Admin admin = adminRepository.findOneByUserId(user.getId());
				userInfo = makeUserInfo(admin, user);
			}else if (user.getRole() == Consts.Role.GOODSOWNER 
					|| user.getRole() == Consts.Role.WAREHOUSEOWNER) {
				GoodsOwner goodsOwner = goodsOwnerRepository.findOneByUserId(user.getId());
//				userInfo.setName(goodsOwner.getName());
//				userInfo.setIdCard(userInfo.getIdCard());
//				userInfo.setMobile(goodsOwner.getMobile());
//				userInfo.setPhone(goodsOwner.getPhone());
//				userInfo.setEmail(goodsOwner.getEmail());
//				userInfo.setQq(goodsOwner.getQq());
//				userInfo.setAvatar(goodsOwner.getAvatar());
				userInfo = makeUserInfo(goodsOwner, user);
			}		
			if (scoreRepository.sumScoreByUserId(user.getId()) != null) {
				userInfo.setScore(scoreRepository.sumScoreByUserId(user.getId()));
			}
			
		
		}
		return userInfo;
	}



	@Override
	public LogisticsUser findByUserName(String username) {
		LogisticsUser user = logisticsUserRepository.findOneByUsername(username);
		return user;
	}

	private TruckOwner makeTruckOwner(TruckOwner truckOwner, UserInfo userInfo){
		truckOwner.setName(userInfo.getName() == null ? truckOwner.getName() : userInfo.getName());
		truckOwner.setIdCard(userInfo.getIdCard() == null ? truckOwner.getIdCard() : userInfo.getIdCard());
		truckOwner.setMobile(userInfo.getMobile() == null ? truckOwner.getMobile() : userInfo.getMobile());
		truckOwner.setPhone(userInfo.getPhone() == null ? truckOwner.getPhone() : userInfo.getPhone());
		truckOwner.setEmail(userInfo.getEmail() == null ? truckOwner.getEmail() : userInfo.getEmail());
		truckOwner.setQq(userInfo.getQq() == null ? truckOwner.getQq() : userInfo.getQq());
		truckOwner.setAvatar(userInfo.getAvatar() == null ? truckOwner.getAvatar() : userInfo.getAvatar());
		truckOwner.setTruckLabel(userInfo.getTruckLabel() == null ? truckOwner.getTruckLabel() : userInfo.getTruckLabel());
		return truckOwner;
	}
	private GoodsOwner makeGoodsOwner(GoodsOwner goodsOwner, UserInfo userInfo){
		goodsOwner.setName(userInfo.getName() == null ? goodsOwner.getName() : userInfo.getName());
		goodsOwner.setIdCard(userInfo.getIdCard() == null ? goodsOwner.getIdCard() : userInfo.getIdCard());
		goodsOwner.setMobile(userInfo.getMobile() == null ? goodsOwner.getMobile() : userInfo.getMobile());
		goodsOwner.setPhone(userInfo.getPhone() == null ? goodsOwner.getPhone() : userInfo.getPhone());
		goodsOwner.setEmail(userInfo.getEmail() == null ? goodsOwner.getEmail() : userInfo.getEmail());
		goodsOwner.setQq(userInfo.getQq() == null ? goodsOwner.getQq() : userInfo.getQq());
		goodsOwner.setAvatar(userInfo.getAvatar() == null ? goodsOwner.getAvatar() : userInfo.getAvatar());
		return goodsOwner;
	}
	private Admin makeAdmin(Admin admin, UserInfo userInfo){
		admin.setName(userInfo.getName() == null ? admin.getName() : userInfo.getName());
		admin.setIdCard(userInfo.getIdCard() == null ? admin.getIdCard() : userInfo.getIdCard());
		admin.setMobile(userInfo.getMobile() == null ? admin.getMobile() : userInfo.getMobile());
		admin.setPhone(userInfo.getPhone() == null ? admin.getPhone() : userInfo.getPhone());
		admin.setEmail(userInfo.getEmail() == null ? admin.getEmail() : userInfo.getEmail());
		admin.setQq(userInfo.getQq() == null ? admin.getQq() : userInfo.getQq());
		admin.setAvatar(userInfo.getAvatar() == null ? admin.getAvatar() : userInfo.getAvatar());
		return admin;
	}
	
	private Driver makeDriver(Driver driver, UserInfo userInfo){
		driver.setName(userInfo.getName() == null ? driver.getName() : userInfo.getName());
		driver.setIdCard(userInfo.getIdCard() == null ? driver.getIdCard() : userInfo.getIdCard());
		driver.setMobile(userInfo.getMobile() == null ? driver.getMobile() : userInfo.getMobile());
		driver.setPhone(userInfo.getPhone() == null ? driver.getPhone() : userInfo.getPhone());
//		driver.setEmail(userInfo.getEmail());
//		driver.setQq(userInfo.getQq());
		driver.setAvatar(userInfo.getAvatar() == null ? driver.getAvatar() : userInfo.getAvatar());
		driver.setTruckId(userInfo.getTruckId() == 0 ? driver.getTruckId() : userInfo.getTruckId());
		driver.setTruckLabel(userInfo.getTruckLabel() == null ? driver.getTruckLabel() : userInfo.getTruckLabel());
		return driver;
	}
//	private String encrypt(String plainPassword) {
//		return PasswordUtils.encrypt(plainPassword, Constants.SALT);
//	}

	@Override
	public ListSlice<UserInfo> findByType(int from, int max, int type, String name, String phone) {
//		Page<LogisticsUser> users = logisticsUserRepository.findPageByType(type, new SimplePageable(from, max));
		name = (name == null) ? "%" : String.format("%%%s%%", name);
		phone = (phone == null) ? "%" : String.format("%%%s%%", phone);
		ListSlice<UserInfo> infos = null;
		switch(type){
			case Consts.Role.ADMIN:{
				Page<Admin> admins = adminRepository.findPageByNameAndPhone(name, phone, new SimplePageable(from, max));
				infos = makeUserInfo(admins);
				break;
			}
			case Consts.Role.TRUCKOWNER:{
				Page<TruckOwner> truckOwners = truckOwnerRepository.findPageByNameAndPhone(name, phone, new SimplePageable(from, max));
				infos = makeUserInfo(truckOwners);
				break;
			}
			case Consts.Role.GOODSOWNER:{
				Page<GoodsOwner> goodsOwners = goodsOwnerRepository.findPageByRoleAndNameAndPhone(type, name, phone, new SimplePageable(from, max));
				infos = makeUserInfo(goodsOwners);
				break;
			}
			case Consts.Role.WAREHOUSEOWNER:{
				Page<GoodsOwner> goodsOwners = goodsOwnerRepository.findPageByRoleAndNameAndPhone(type, name, phone, new SimplePageable(from, max));
				infos = makeUserInfo(goodsOwners);
				break;
			}
			default:
//				Page<GoodsOwner> goodsOwners = goodsOwnerRepository.findPage(new SimplePageable(from, max));
//				infos = makeUserInfo(goodsOwners);
				break;
		}
		return infos;
	}

	private ListSlice<UserInfo> makeUserInfo(Page<? extends UserCommon> users) {
		List<? extends UserCommon> content = users.getContent();
		List<UserInfo> uinfos = makeUserInfos(content);
		return new ListSlice<UserInfo>(users.getTotalElements(), uinfos);
	}

	private List<UserInfo> makeUserInfos(List<? extends UserCommon> content) {
		List<UserInfo> uinfos = new ArrayList<UserInfo>();
		for(UserCommon u: content){
			uinfos.add(makeUserInfo(u));
		}
		return uinfos;
	}

	private UserInfo makeUserInfo(UserCommon uc) {
		LogisticsUser user = logisticsUserRepository.findOne(uc.getUserId());
		uc.setIsDeleted(user.getIsDeleted());
		return makeUserInfo(uc, user);
	}
	
	private UserInfo makeUserInfo(UserCommon uc, LogisticsUser user){
        Driver driver = driverRepository.findOnebyUserId(user.getId());
		UserInfo userInfo = new UserInfo();
		BeanUtils.copyProperties(uc, userInfo);
		userInfo.setPassword(null);
		userInfo.setRole(user.getRole());
		userInfo.setUsername(user.getUsername());
        if (driver != null && driver.getTruckId() > 0) {
            userInfo.setTruckId((int)driver.getTruckId());
            Truck truck = truckRepository.findOne(driver.getTruckId());
            userInfo.setLicensePlateNumber(truck.getLicensePlateNumber());
        }
		return userInfo;
	}

	@Override
	public UserInfo createUser(UserInfo userInfo) {
		LogisticsUser logisticsUser = new LogisticsUser();
		logisticsUser.setUsername(userInfo.getUsername());
		logisticsUser.setPassword(MD5Utils.getMD5(userInfo.getPassword()));
		logisticsUser.setRegisterTime(new Date());
		logisticsUser.setRole(userInfo.getRole());
		logisticsUser = logisticsUserRepository.save(logisticsUser);
		if (userInfo.getRole() == Consts.Role.ADMIN) {
			Admin admin = makeAdmin(new Admin(), userInfo);
			admin.setUserId(logisticsUser.getId());
			adminRepository.save(admin);
		}else if (userInfo.getRole() == Consts.Role.TRUCKOWNER) {
			TruckOwner truckOwner = makeTruckOwner(new TruckOwner(), userInfo);
			truckOwner.setUserId(logisticsUser.getId());
			truckOwnerRepository.save(truckOwner);
		}else {
			GoodsOwner goodsOwner = makeGoodsOwner(new GoodsOwner(), userInfo);
			goodsOwner.setUserId(logisticsUser.getId());
			goodsOwnerRepository.save(goodsOwner);
		}
		return userInfo;
	}

	@Override
	public void delete(Long id) {
		TruckOwner truckOwner = truckOwnerRepository.findOneByUserId(id);
		if (truckOwner != null) {
			truckOwner.setIsDeleted(Consts.DELETED);
			truckOwnerRepository.save(truckOwner);
		}
		LogisticsUser logisticsUser = logisticsUserRepository.findOne(id);
		if (logisticsUser != null) {
			logisticsUser.setIsDeleted(Consts.DELETED);
			logisticsUserRepository.save(logisticsUser);
		}
	}
	
	@Override
	public void deletePhysical(Long id) {
		TruckOwner truckOwner = truckOwnerRepository.findOneByUserId(id);
		if (truckOwner != null) {
			truckOwnerRepository.delete(truckOwner.getId());
		}
		logisticsUserRepository.delete(id);
	}

	@Override
	public void updateUserVipLevel(UserVipLevelInfo userVipLevelInfo) {
		TruckOwner truckOwner = truckOwnerRepository.findOneByUserId(userVipLevelInfo.getUserId());
		if(truckOwner == null){
			throw new CodeException(ReturnCode.USER_NOT_EXIST, MessageDes.User.USER_NOT_EXISTS);
		}
		truckOwner.setVipLevel(userVipLevelInfo.getVipLevel());
	}

	@Override
	public void forgetPassword(ResetPasswordInfo resetPasswordInfo) {
		final String username = resetPasswordInfo.getUsername();
		final String email = resetPasswordInfo.getEmail();
		UserCommon uc = null;
		String url = "http://56c.chinalbs.org";
		if (!TextUtils.isEmpty(username) && !TextUtils.isBlank(email)) {
			LogisticsUser user = logisticsUserRepository.findOneByUsername(username);
			if(user != null){
				if (user.getRole() == Consts.Role.GOODSOWNER || user.getRole() == Consts.Role.WAREHOUSEOWNER) {
					uc = goodsOwnerRepository.findOneByUserId(user.getId());
					url = "http://56.chinalbs.org";
				}else if (user.getRole() == Consts.Role.TRUCKOWNER) {
					uc = truckOwnerRepository.findOneByUserId(user.getId());
				}else if(user.getRole() == Consts.Role.DRIVER){
					uc = driverRepository.findOnebyUserId(user.getId());
				}
			}
        }
		
		if(uc == null || !email.equals(uc.getEmail())){
            throw new CodeException(ReturnCode.NO_USER_OR_WRONG_EMAIL, MessageDes.ResetPassword.NO_USER_OR_WRONG_EMAIL);
		}
		Date now = new Date();
		String userToken = UUID.randomUUID().toString();
		ResetPassword resetPassword = new ResetPassword();
		resetPassword.setApplyTime(now);
		resetPassword.setEmail(email);
		resetPassword.setUserId(uc.getUserId());
		resetPassword.setUsername(username);
		resetPassword.setToken(userToken);
		resetPasswordRepository.save(resetPassword);
		
		final String userUrl = String.format("%s/api/pwdreset/%s", url, userToken);
		final String name = uc.getName();
		ThreadPoolUtils.execute(new Runnable(){
			@Override
			public void run() {
				try {
					MailUtils.sendFindPasswordUrlToMail(email, userUrl, TextUtils.isEmpty(name) ? username : name);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	@Override
	public long forgetPassword(String userToken) {
		ResetPassword reset = resetPasswordRepository.findOneByToken(userToken);
		long userId = 0;
		if(reset != null && reset.getStatus() == 0){
			Date now = new Date();
			long applyTime = reset.getApplyTime().getTime();
			if(now.getTime() - applyTime <= 30 * 60 * 1000){
				userId = reset.getUserId();
			}else{
				reset.setStatus(-1);
				reset.setProcessTime(now);
				resetPasswordRepository.save(reset);
			}
		}
		return userId;
	}

	@Override
	public boolean resetPassword(ResetPasswordInfo resetPasswordInfo) {
		boolean b = false;
		ResetPassword reset = resetPasswordRepository.findOneByToken(resetPasswordInfo.getToken());
		if(reset != null && reset.getStatus() == 0){
			Date now = new Date();
			reset.setProcessTime(now);
			long applyTime = reset.getApplyTime().getTime();
			if(now.getTime() - applyTime <= 30 * 60 * 1000){
				LogisticsUser user = logisticsUserRepository.findOne(reset.getUserId());
				user.setPassword(MD5Utils.getMD5(resetPasswordInfo.getPassword()));
				reset.setStatus(1);
				b = true;
			}else{
				reset.setStatus(-1);
			}
		}
		return b;
	}

	@Override
	public void deleteOrActivateUser(Long userId) {
		LogisticsUser user = logisticsUserRepository.findOne(userId);
		if (user == null) {
			throw new CodeException(ReturnCode.USER_NOT_EXIST, MessageDes.User.USER_NOT_EXISTS);
		}
		switch (user.getIsDeleted()) {
		case Consts.DELETED:
			user.setIsDeleted(Consts.ACTIVATE);
			break;
		case Consts.ACTIVATE:
			user.setIsDeleted(Consts.DELETED);
			break;
		default:
			break;
		}
		logisticsUserRepository.save(user);
	}
}
