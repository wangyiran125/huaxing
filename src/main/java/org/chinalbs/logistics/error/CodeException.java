package org.chinalbs.logistics.error;

/**
 * Created by Jason on 15/4/18.
 * All Rights Reserved.
 */
public class CodeException extends RuntimeException {
    private int code;
    public CodeException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
