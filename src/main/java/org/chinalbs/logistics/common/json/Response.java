package org.chinalbs.logistics.common.json;


/**
 * RESTful 返回结果封装类
 */

public class Response<T> {

	private int code;
	private String description;
    private long lastUpdateTime;
	private T payload;
 
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

    public long getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(long lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public T getPayload() {
		return payload;
	}

	public void setPayload(T payload) {
		this.payload = payload;
	}

	@Override
	public String toString() {
		return "Response [code=" + code + ", description=" + description
				+ ", lastUpdateTime=" + lastUpdateTime + ", payload=" + payload
				+ "]";
	}
	
	

}
