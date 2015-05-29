package org.chinalbs.logistics.vo;

import java.io.File;

public class AttachmentInfo {
	private String attachmentName; //实际显示的名字
	private File file; //文件
	
	public AttachmentInfo(){}
	
	public AttachmentInfo(File file, String attachmentName){
		this.attachmentName = attachmentName;
		this.file = file;
	}
	
	public String getAttachmentName() {
		return attachmentName;
	}
	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
}
