package com.maplebox.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;

public class PropertyUtil {
	public static Properties loadFromXMLByClassPath(String xmlPath) throws IOException {
		Properties prop = new Properties();
		try (InputStream in = IOUtils.buffer(new ClassPathResource(xmlPath).getInputStream())) {
			prop.loadFromXML(in);
		}
		return prop;
	}
}
