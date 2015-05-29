package org.chinalbs.logistics.common.utils;

public class KeyProvider {
    public static final KeyProvider INSTANCE = new KeyProvider();
    private KeyProvider() {}
    
    public String getTokenKey() {
        return "roBu$SofT　LogiStics";
    }
    
    public String getVerifyCodeKey() {
        return "RoBu$SofT　LogiStics";
    }
    
    public String getRandomPassKey() {
    	return "robu$SofT　LogiStics";
    }
}
