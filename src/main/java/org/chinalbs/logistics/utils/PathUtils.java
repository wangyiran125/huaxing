package org.chinalbs.logistics.utils;



import java.io.File;

import org.chinalbs.logistics.config.PathConfig;

public class PathUtils {
	private static final String HTTPPREFIX = "http://";
	public static String getFullPath(String fileName) {
		return getFullPath(fileName, PathConfig.INSTANCE.getPictureRepositoty());
	}
	
	public static String completeImageUrl(String fileName) {
		return completeUrl(fileName, Consts.FileOpUrl.DOWNLOAD_IMAGE);
	}
	
	public static String completeFileUrl(String fileName) {
		return completeUrl(fileName, Consts.FileOpUrl.DOWNLOAD_FILE);
	}
	
	
	public static String completeAppUrl(String fileName) {
		return completeUrl(fileName, Consts.FileOpUrl.DOWNLOAD_APP);
	}
	
	private static String completeUrl(String fileName, String subPath)
	{
		String path = fileName;
		if(fileName != null && !fileName.equals("") && !fileName.toLowerCase().startsWith(HTTPPREFIX)){
			fileName = Base64Utils.encode(fileName);
			path = PathConfig.INSTANCE.getBaseResourceUrl() + subPath + "/" + fileName;
		}
		return path;
	}
	
	public static String getFullPath(String fileName, String repositoryPath){
		if(fileName == null || fileName.equals("")){
			return null;
		}
		return repositoryPath + File.separator + fileName;
	}
	
	/**
	 * 根据文件全路径URL获取资源名
	 * @param fullName
	 * @return 如果是站外资源原样返回
	 */
	public static String getFileNameFromUrl(String fullName){
		String fileName = fullName;
		if(fullName != null && fullName.startsWith(PathConfig.INSTANCE.getBaseResourceUrl())){
			fileName = fullName.substring(fullName.lastIndexOf("/") + 1);
			fileName = Base64Utils.decode(fileName);
		}
		return fileName;
	}

	public static String getFullAvatarPath(String fileName) {
		return getFullPath(fileName, PathConfig.INSTANCE.getUserAvatarRepositoty());
	}

	public static String completeAvatarUrl(String fileName) {
		return completeUrl(fileName, Consts.FileOpUrl.DOWNLOAD_AVATAR);
	}
}
