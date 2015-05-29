package org.chinalbs.logistics.common.json;

public interface ReturnCode {
    static final int SUCCESS = 0;
    static final int CACHE_AVAILABLE = 1;
    static final int INVALID_LOGIN_INFO = 2;// 用户名或密码错误
    static final int NO_PERMISSION = 3;
    static final int EXCEPTION = -1;
    static final int BUSINESS_ERROR = -2;
    static final int USERNAME_EXIST = -3;
    static final int INVALID_PARAMETER = -4;
    static final int USERNAME_NOT_EXIST = -5;
    static final int CAPCARE_EXCEPTION = -6;
    static final int USER_NOT_EXIST = -7;
    static final int NO_USER_OR_WRONG_EMAIL = -8; //用户忘记密码时输入的用户名不存在或用户名和邮箱绑定信息不正确
    static final int RESET_PASSWORD_TOKEN_EXPIRED = -9; //用户忘记密码时token过期
    static final int USER_WAS_FORBDDEN = -10; //账号已被停用
    static final int NEVER_USED_CODE = -999999;// -999999：假定为永远也不会出现的returnCode
}
