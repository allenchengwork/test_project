package com.maplebox.nail.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;

@Controller  
@RequestMapping("/code") 
public class CaptchaController {
	static Logger log = LoggerFactory.getLogger(CaptchaController.class);
	
	@Autowired  
	private Producer captchaProducer;
	
	@ResponseBody
	@RequestMapping(value = "captcha-image", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getKaptchaImage(HttpSession session) throws Exception {         
        String capText = captchaProducer.createText();
        log.debug("驗證碼: {}", capText);
        
        session.setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);
        BufferedImage bi = captchaProducer.createImage(capText);
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
        	ImageIO.write(bi, "jpg", out);
        	out.flush();
        	
        	byte[] imageBytes = out.toByteArray();
        	
        	HttpHeaders headers = new HttpHeaders();
        	headers.setExpires(0);
        	headers.setCacheControl("no-store, no-cache, must-revalidate, post-check=0, pre-check=0");
        	headers.setPragma("no-cache");
        	headers.setContentType(MediaType.IMAGE_JPEG);
        	headers.setContentLength(imageBytes.length);
        	
        	return new ResponseEntity<byte[]>(imageBytes, headers, HttpStatus.OK);
        }
	}
}
