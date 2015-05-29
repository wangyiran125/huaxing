package org.chinalbs.logistics.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.chinalbs.logistics.common.utils.IOUtils;
import org.chinalbs.logistics.config.PathConfig;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.google.common.io.ByteSource;
import com.google.common.io.Files;

public final class MultipartUtils {
	public static final void saveFile(MultipartFile multipartFile, File target) throws IOException {
		if (target.getParentFile() != null && !target.getParentFile().exists()) {
			target.getParentFile().mkdirs();
		}
		final InputStream is = multipartFile.getInputStream();
			try {
			Files.copy(new ByteSource() {
				@Override
				public InputStream openStream() throws IOException {
					return is;
				}
			}, target);
		} finally {
			IOUtils.closeQuietly(is);
		}
	}
	

	/**
	 * 上传图片通用方法，返回下载URL
	 * @param imageFile
	 * @return
	 * @throws IOException
	 */
	public static final String saveImage(MultipartFile imageFile) throws IOException
	{
		return saveFile(imageFile, PathConfig.INSTANCE.getPictureRepositoty(), true);
	}
	
	/**
	 * 保存文件
	 * @param file 要保存的文件
	 * @param repositoryPath 文件保存目录
	 * @param isUUIDName 是否声称随机文件名
	 * @return
	 * @throws IOException
	 */
	public static final String saveFile(MultipartFile file, String repositoryPath, boolean isUUIDName) throws IOException
	{
		String saveName = getFileName(file, isUUIDName);
		saveFile(file, new File(repositoryPath, saveName));
//		System.out.println(saveName);
		return saveName;
	}
	
	/**
	 * 获取上传后保存的文件名
	 * @param file MultipartFile，要上传的文件
	 * @param isUUIDName 是否动态生成唯一的名字
	 * @return
	 */
	private static final String getFileName(MultipartFile file, boolean isUUIDName)
	{
		String fileName = file.getOriginalFilename();
		if(isUUIDName){
			int lastPoint = fileName.lastIndexOf(".");
			String suffix = lastPoint == -1 ? "" : fileName.substring(lastPoint);
			fileName = UUID.randomUUID().toString() + suffix;
		}
		return fileName;
	}
	
	public static final void saveAllFiles(MultipartRequest multipartRequest, SavingStrategy strategy) throws IOException {
		MultiValueMap<String, MultipartFile> fileMap = multipartRequest.getMultiFileMap();
		for (Iterator<List<MultipartFile>> it = fileMap.values().iterator(); it.hasNext(); ) {
			List<MultipartFile> fileList = it.next();
			for (MultipartFile mfile : fileList) {
				saveFile(mfile, strategy.getTargetFile(mfile));
			}
		}
	}
	
	public static interface SavingStrategy {
		public File getTargetFile(MultipartFile mfile);
	}
}
