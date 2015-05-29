package org.chinalbs.logistics.utils;

public class Consts {
	
	public static final int DELETED = -1;
	public static final int ACTIVATE = 0;

	public class Role {
		public static final int ADMIN = 1;
		public static final int WAREHOUSEOWNER = 2;
		public static final int GOODSOWNER = 3;
		public static final int TRUCKOWNER = 4;
		public static final int DRIVER = 5;
	}
	
	public class Truck{
		public static final int EMPTY = 1;
		public static final int HALF_FULL = 2;
		public static final int FULL = 3;
		public static final int INTENT_PERMISION = 1;
		public static final int INTENT_NOPERMISION = 2;
	}
	
	public class Order{
		public static final int NO_SCORE = -1;
		public static final int TRUCKOWNER_NOT_EVALUATED = 1;
		public static final int TRUCKOWNER_EVALUATED = 2;
		public static final int GOODSOWNER_NOT_EVALUATED = 1;
		public static final int GOODSOWNER_EVALUATED = 2;
		public static final int STATUS_PUBLISHED = 1; //刚发布
		public static final int STATUS_ORDER_INTENT = 2;//已抢单
		public static final int STATUS_GOODOWNER_CONFIRM = 3;
		public static final int STATUS_TRUCKOWNER_CONFIRM = 4;
		public static final int STATUS_FINISHED = 5;
		public static final int STATUS_TRUCK_EVALUTED= 6;//仅用于orderintent表，给客户端使用
	} 
	
	public class Intents{
		public static final int NO_CONFIRM= 1;
		public static final int REFUSE = 2;
		public static final int CANCEL = -1;
	} 
	
	public class FileOpUrl{
		public static final String DOWNLOAD_IMAGE = "/download/image";
		public static final String DOWNLOAD_FILE = "/download/file";
		public static final String DOWNLOAD_APP = "/download/app";
		public static final String DOWNLOAD_AVATAR = "/download/avatar";
		public static final String USER_TEMPLATE = "/download/user_template";
		public static final String UPLOAD_IMAGE = "/upload/image";
		public static final String UPLOAD_IMAGES = "/upload/images";
		public static final String UPLOAD_AVATAR = "upload/avatar";
		public static final String UPLOAD_FILE = "/upload/file";
		public static final String UPLOAD_APP = "/upload/app";
	}
	
	public class Capcare{
		public static final String DEVICE_BIND = "1";
		public static final String DEVICE_UNBIND = "2";
		public static final String DEVICE_MODIFY = "3";

	} 
	
	public class SPLIT{
		public static final String COMMA = ",";

	} 
	
	public class ScoreItem{
		public static final long GOODOWNER_PUBLISHED = 1; //货主发布货源得分
		public static final long GOODOWNER_FINISH_ORDER = 2; //货主完成订单积分
		public static final long GOODOWNER_GOT_GOOD_REVIEW = 3; //货主获得好评积分
		public static final long GOODOWNER_GOT_MEDIUM_REVIEW = 4; //货主获得中评积分
		public static final long GOODOWNER_GOT_BAD_REVIEW = 5; //货主获得差评积分
		public static final long TRUCKOWNER_GOT_GOOD_REVIEW = 6; //车主获得好评积分
		public static final long TRUCKOWNER_GOT_MEDIUM_REVIEW = 7; //车主获得中评积分
		public static final long TRUCKOWNER_GOT_BAD_REVIEW = 8; //车主获得差评积分
	}
	
	public class VipLevel{
		public static final int GOLD = 1; //金卡用户
		public static final int SILVER = 2; //银卡用户
//		public static final int COPPER = 3; //铜卡用户
		public static final int COMMON = 0; //普通用户
	}
}
