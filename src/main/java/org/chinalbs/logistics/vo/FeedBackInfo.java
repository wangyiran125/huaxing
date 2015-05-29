package org.chinalbs.logistics.vo;

public class FeedBackInfo {

    private String name;
    private String email;
	private String content;
	private String mobile;
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
	public String toString() {
		return "FeedBackInfo [getContent()=" + getContent() + ", getMobile()="
				+ getMobile() + "]";
	}
	
	
	
}
