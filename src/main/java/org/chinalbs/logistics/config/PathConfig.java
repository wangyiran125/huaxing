package org.chinalbs.logistics.config;

import org.springframework.beans.factory.annotation.Value;

public class PathConfig {
	public static PathConfig INSTANCE = new PathConfig();

	@Value("${file.upload.tmp.dir}")
	private String uploadTmpDir;

	@Value("${wallpaper.repository.dir}")
	private String wallpaperRepository;

	@Value("${theme.repository.dir}")
	private String themeRespository;

	@Value("${base.resource.url}")
	private String baseResourceUrl;

	@Value("${useravatar.repository.dir}")
	private String userAvatarRepository;

	@Value("${picture.repository.dir}")
	private String pictureRepository;

	@Value("${login.permission}")
	private String loginPermission;
	
	public String getUploadTmpDir() {
		return uploadTmpDir;
	}

	public String getWallpaperRepository() {
		return wallpaperRepository;
	}

	public String getThemeRespository() {
		return themeRespository;
	}

	public String getBaseResourceUrl() {
		return baseResourceUrl;
	}

	public String getUserAvatarRepositoty() {
		return userAvatarRepository;
	}

	public String getPictureRepositoty() {
		return pictureRepository;
	}
	
	public String getLoginPermission() {
		return loginPermission;
	}
}
