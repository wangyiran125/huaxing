package org.chinalbs.logistics.config;

import org.springframework.beans.factory.annotation.Value;

public class CommonConfig {
	public static CommonConfig INSTANCE = new CommonConfig();

	@Value("${kaibu.host}")
	private String kaibuHost;

	@Value("${kaibu.port}")
	private int kaibuPort;
	
	@Value("${kaibu.sysname}")
	private String sysName;

	public String getKaibuHost() {
		return kaibuHost;
	}

	public void setKaibuHost(String kaibuHost) {
		this.kaibuHost = kaibuHost;
	}

	public int getKaibuPort() {
		return kaibuPort;
	}

	public void setKaibuPort(int kaibuPort) {
		this.kaibuPort = kaibuPort;
	}

	public String getSysName() {
		return sysName;
	}

	public void setSysName(String sysName) {
		this.sysName = sysName;
	}
	
	
}
