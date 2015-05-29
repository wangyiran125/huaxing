package org.chinalbs.logistics.service.impl;

import java.io.IOException;
import java.util.List;

import org.chinalbs.logistics.common.utils.ResponseEntityUtils;
import org.chinalbs.logistics.config.PathConfig;
import org.chinalbs.logistics.service.FileService;
import org.chinalbs.logistics.utils.Base64Utils;
import org.chinalbs.logistics.utils.Consts;
import org.chinalbs.logistics.utils.MultipartUtils;
import org.chinalbs.logistics.utils.PathUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class FileServiceImpl implements FileService{
	

	@Override
	public String uploadImage(MultipartFile imageFile) throws IOException {
		return PathUtils.completeImageUrl(MultipartUtils.saveImage(imageFile));
	}

	@Override
	public ResponseEntity<FileSystemResource> downloadImage(String fileName) {
		return ResponseEntityUtils.creatOKResponse(new FileSystemResource(PathUtils.getFullPath(Base64Utils.decode(fileName))), MediaType.IMAGE_PNG);
	}
	
	@Override
	public String UploadAvatar(MultipartFile avatar) throws IOException {
		return PathUtils.completeAvatarUrl(MultipartUtils.saveFile(avatar,PathConfig.INSTANCE.getUserAvatarRepositoty(),true));
	}

	@Override
	public ResponseEntity<FileSystemResource> downloadAvatar(String fileName) {
		return ResponseEntityUtils.creatOKResponse(new FileSystemResource(PathUtils.getFullAvatarPath(Base64Utils.decode(fileName))), MediaType.IMAGE_PNG);
	}

	@Override
	public String uploadImages(List<MultipartFile> imageFiles) throws IOException {
		if (imageFiles != null && imageFiles.size() > 0) {
			StringBuffer s = new StringBuffer();
			for (int i = 0; i < imageFiles.size(); i++) {
				s.append(PathUtils.completeImageUrl(MultipartUtils.saveImage(imageFiles.get(i))));
				if (i != (imageFiles.size() - 1)) {
					s.append(Consts.SPLIT.COMMA);
				}
			}
			return s.toString();
		}
		return null;
	}

}
