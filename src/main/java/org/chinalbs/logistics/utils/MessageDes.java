package org.chinalbs.logistics.utils;

public class MessageDes {
	
	public static final String INTERNAL_ERROR = "内部异常";
	public static final String PARAM_ERROR = "输入信息异常";
	public static final String NO_PERMISSION = "您无权进行此操作，请重新登录！";

	public class Login {
		public static final String PASSWORD_ERROR = "用户名或密码输入错误";
		public static final String USER_WAS_FORBDDEN = "账号已被停用";
		public static final String VCODE_ERROR = "验证码输入错误";
	}
	
	public class User {
		public static final String CAPCARE_USER_CREATE_ERROR = "凯步关爱创建用户失败";
		public static final String CAPCARE_RETURN_DATA_ERROR = "凯步接口返回无效数据";
		public static final String USER_DUPLICATE = "该用户名已被注册";
		public static final String ORIGINAL_PASSWORD_ERROR = "原始密码输入错误";
		public static final String ROLE_ERROR = "请选择正确的角色";
		public static final String USER_NOT_EXISTS= "用户不存在";
		public static final String NO_DRIVER_PERMINSION= "用户权限不足";
	}

	
	public class Goods {
		public static final String DELETE_GUARD_STATUS= "该货物已经处于运输中或完成状态，不可删除";
		public static final String UPDATE_GUARD_STATUS= "该货物已经处于运输中或完成状态，不可修改";
		public static final String WRONG_ROLE = "请用货主或者仓库主账号登录后再进行此操作！";
	}
	
	public class Truck {
		public static final String DELETE_GUARD_STATUS = "该车辆已抢单或者运输中，不可删除";
		public static final String PLATE_NUMBER_ERROR = "该车牌号码已经被占用，请确认输入是否正确，或者联系管理员";
		public static final String WRONG_ROLE = "请用车主账号登录后再进行此操作！";
		public static final String TRUCK_NOT_AVAILABLE = "您选择的车辆已经分配给其他人，请选择其他车辆！";
	}
	
	public class Intents {
		public static final String ORDER_CONFLIT_GOODSOWNER = "该车主已对该货物进行抢单，请查看货物列表并联系对应车主";
		public static final String ORDER_CONFLIT_TRUCKOWNER= "该货主已选择了您的车辆，请查看抢单列表并联系对应货主";
		public static final String ORDER_REFUSD_TRUCKOWNER= "该货主已拒绝了您对本货物的抢单，请选择其他货物";
		public static final String ORDER_HAS_INTENT= "该货主已选择其他车主发货，请选择其他货物";
	}
	
	public class Order {
		public static final String NO_ORDER_ID = "运单号不存在";
		public static final String NO_SCORE_ITEM = "积分字典没有正确初始化";
		public static final String GOODS_EVLUATED = "货主已评价";
		public static final String TRUCK_EVLUATED = "车主已评价";

	}	
	
	public class ResetPassword{
		public static final String NO_USER_OR_WRONG_EMAIL = "您输入的账户或邮箱无效";
		public static final String RESET_PASSWORD_TOKEN_EXPIRED = "重置密码链接已过期";
	}
}
