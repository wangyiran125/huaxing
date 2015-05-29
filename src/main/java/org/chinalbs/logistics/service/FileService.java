package org.chinalbs.logistics.service;

import java.io.IOException;
import java.util.List;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

	public String uploadImage(MultipartFile imageFile) throws IOException;
	
	public String uploadImages(List<MultipartFile> imageFiles) throws IOException;

	public ResponseEntity<FileSystemResource> downloadImage(String fileName);

	public String UploadAvatar(MultipartFile avatar)throws IOException;
	
	ResponseEntity<FileSystemResource> downloadAvatar(String fileName);

}
