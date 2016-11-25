package com.maplebox.nail.cache;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Locale;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.ImageOutputStream;

import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Mode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.maplebox.nail.domain.ImageCacheKey;

import net.sf.jmimemagic.Magic;
import net.sf.jmimemagic.MagicException;
import net.sf.jmimemagic.MagicMatch;
import net.sf.jmimemagic.MagicMatchNotFoundException;
import net.sf.jmimemagic.MagicParseException;

@Component
public class ImageCacheManager {
	static Logger log = LoggerFactory.getLogger(ImageCacheManager.class);
	
	@Cacheable(value = "imageCache", key = "#key")
	public byte[] getImage(ImageCacheKey key, File imgFile, int width, int height) throws IOException, MagicParseException, MagicMatchNotFoundException, MagicException {
		log.debug("ImageManager Cache call");
		BufferedImage img = ImageIO.read(imgFile);
		
		MagicMatch match = Magic.getMagicMatch(imgFile, true);
		String imgType = match.getExtension();
		
		BufferedImage scaledImg = Scalr.resize(img, Mode.AUTOMATIC, width, height);
		
		log.debug("imgType = {}", match.getMimeType());
		if ("jpg".equals(imgType)) {
	        try (
	        	ByteArrayOutputStream bout = new ByteArrayOutputStream();
	        	ImageOutputStream ios = ImageIO.createImageOutputStream(bout);
	        ) {
	        	ImageWriter writer = ImageIO.getImageWritersByFormatName("jpg").next();
		        writer.setOutput(ios);
		        ImageWriteParam iwparam = new JPEGImageWriteParam(Locale.getDefault());
		        iwparam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
		        iwparam.setCompressionQuality(1);
		        
		        writer.write(null, new IIOImage(scaledImg, null, null), iwparam);
	            ios.flush();
	            writer.abort();
	            writer.dispose();
	            
	            return bout.toByteArray();
	        }
		} else {
			try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
				ImageIO.write(scaledImg, imgType, out);
				out.flush();
				return out.toByteArray();
			}
		}
	}
}
