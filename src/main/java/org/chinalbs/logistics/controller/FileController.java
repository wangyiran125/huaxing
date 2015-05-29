package org.chinalbs.logistics.controller;

import java.io.IOException;
import java.util.List;

import org.chinalbs.logistics.annotation.OperationDefinition;
import org.chinalbs.logistics.common.json.Response;
import org.chinalbs.logistics.common.json.ResponseHelper;
import org.chinalbs.logistics.service.FileService;
import org.chinalbs.logistics.utils.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("")
public class FileController {
	@Autowired
	private FileService fileService;

	@OperationDefinition(name = "下载图片", anonymous = true)
	@RequestMapping(value = Consts.FileOpUrl.DOWNLOAD_IMAGE + "/{fileName}", method = RequestMethod.GET)
	public ResponseEntity<FileSystemResource> downloadImage(@PathVariable String fileName) {
		return fileService.downloadImage(fileName);
	}
	
	@OperationDefinition(name = "下载用户头像", anonymous = true)
	@RequestMapping(value = Consts.FileOpUrl.DOWNLOAD_AVATAR + "/{fileName}", method = RequestMethod.GET)
	public ResponseEntity<FileSystemResource> downloadAvatar(@PathVariable String fileName) {
		return fileService.downloadAvatar(fileName);
	}
	
	@OperationDefinition(name = "上传图片", anonymous = true)
	@RequestMapping(value = Consts.FileOpUrl.UPLOAD_IMAGE, method = RequestMethod.POST)
	public Response<String> uploadImage(@RequestParam("image") MultipartFile imageFile) throws Exception {
		return ResponseHelper.createSuccessResponse(fileService.uploadImage(imageFile));
	}
	
	@OperationDefinition(name = "上传图片,支持多张", anonymous = true)
	@RequestMapping(value = Consts.FileOpUrl.UPLOAD_IMAGES, method = RequestMethod.POST)
	public Response<String> uploadImages(@RequestParam("images") List<MultipartFile> imageFiles) throws Exception {
		return ResponseHelper.createSuccessResponse(fileService.uploadImages(imageFiles));
	}
	
	@OperationDefinition(name = "上传头像", anonymous = true)
	@RequestMapping(value = Consts.FileOpUrl.UPLOAD_AVATAR, method = RequestMethod.POST)
	public Response<String> uploadAvatar(@RequestParam("avatar") MultipartFile multipartFile) throws IOException{
		
		return ResponseHelper.createSuccessResponse(fileService.UploadAvatar(multipartFile));
	}
}
