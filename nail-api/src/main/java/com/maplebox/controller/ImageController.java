package com.maplebox.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import com.maplebox.cache.ImageCacheManager;
import com.maplebox.domain.ImageCacheKey;

@Controller
@RequestMapping(value = "/")
public class ImageController {
	static Logger log = LoggerFactory.getLogger(ImageController.class);
	
	@Value("${img.path}")
	private String imgPath;
	
	@Autowired
	private ImageCacheManager imageManager;
	
	@ResponseBody
	@RequestMapping(value = "image/{fileName:.*}", method = RequestMethod.GET)
	public ResponseEntity<FileSystemResource> image(
			@PathVariable String fileName,
			WebRequest webRequest) throws Exception {
		File imgFile = new File(imgPath, fileName);
		if (imgFile.exists() == false) {
			return new ResponseEntity<FileSystemResource>(HttpStatus.NOT_FOUND);
		}
		
		String extName = FilenameUtils.getExtension(fileName);
		
		long lastModified = imgFile.lastModified();
		if (webRequest.checkNotModified(lastModified)) {
			return new ResponseEntity<FileSystemResource>(HttpStatus.NOT_MODIFIED);
		} else {
			return fileToResponseEntity(extName, imgFile);
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "image/{type}/{fileName:.*}", method = RequestMethod.GET)
	public ResponseEntity<byte[]> thumb(
			@PathVariable String type,
			@PathVariable String fileName,
			WebRequest webRequest) throws Exception {
		log.debug("thumb");
		int width = 640;
		int height = 480;
		if ("h".equals(type)) {
			width = 1024;
			height = 768;
		} else if ("m".equals(type)) {
			width = 640;
			height = 480;
		} else if ("s".equals(type)) {
			width = 240;
			height = 180;
		}
		int fwidth = width;
		int fheight = height;
		
		File imgFile = new File(imgPath, fileName);
		if (imgFile.exists() == false) {
			return new ResponseEntity<byte[]>(HttpStatus.NOT_FOUND);
		}
		
		long lastModified = imgFile.lastModified();
		if (webRequest.checkNotModified(lastModified)) {
			return new ResponseEntity<byte[]>(HttpStatus.NOT_MODIFIED);
		} else {
			String extName = FilenameUtils.getExtension(imgPath);

			ImageCacheKey key = new ImageCacheKey(type, fileName);
			byte[] imageBytes = imageManager.getImage(key, imgFile, fwidth, fheight);
			return byteArrayToResponseEntity(extName, imageBytes, lastModified);
		}
	}
	
	private HttpHeaders httpHeader(String type, long length, long lastModified) {
		HttpHeaders headers = new HttpHeaders();
		if ("png".equals(type)) {
			headers.setContentType(MediaType.IMAGE_PNG);
		} else if ("gif".equals(type)) {
			headers.setContentType(MediaType.IMAGE_GIF);
		} else {
			headers.setContentType(MediaType.IMAGE_JPEG);
		}
		headers.setLastModified(lastModified);
		headers.setContentLength(length);
		headers.setCacheControl("max-age=864000, must-revalidate");
		headers.setExpires(DateUtils.addSeconds(new Date(), 864000).getTime());
		return headers;
	}
	
	private ResponseEntity<byte[]> byteArrayToResponseEntity(String type, byte[] imageBytes, long lastModified) throws IOException {
		HttpHeaders headers = httpHeader(type, imageBytes.length, lastModified);
		return new ResponseEntity<byte[]>(imageBytes, headers, HttpStatus.OK);
	}
	
	private ResponseEntity<FileSystemResource> fileToResponseEntity(String type, File imgFile) throws IOException {
		HttpHeaders headers = httpHeader(type, imgFile.length(), imgFile.lastModified());
		return new ResponseEntity<FileSystemResource>(new FileSystemResource(imgFile), headers, HttpStatus.OK);
	}
}
