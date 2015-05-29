package org.chinalbs.logistics.session;

import java.io.Serializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.WebRequest;

/**
 * 放到HttpSession里的数据，用来跟踪当前登录用户信息
 * @author Jason
 *
 */
public class SessionInfo implements Serializable {
	
	private static final long serialVersionUID = 6101664849899327L;
	private static final InheritableThreadLocal<SessionInfo> INFO = new InheritableThreadLocal<SessionInfo>();
	private static final Logger log = LoggerFactory.getLogger(SessionInfo.class);
	public static final String KEY_OF_SESSION_INFO = "SessionInfoKey";
	
	private UserInfo userInfo = new UserInfo(false, null);
	
	private long lastUpdateTime = 0l;
	

	public  static SessionInfo getCurrent() {
		return INFO.get();
	}
	
	public static void setCurrent(SessionInfo info) {
		INFO.set(info);
	}
	
	public static void setCurrent(SessionInfo info, WebRequest webRequest) {
		INFO.set(info);
		webRequest.setAttribute(KEY_OF_SESSION_INFO, info, WebRequest.SCOPE_SESSION);
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}


	public long getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(long lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

}
