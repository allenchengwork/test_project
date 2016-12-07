package com.maplebox.encrypt;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import com.google.common.base.Splitter;
import com.maplebox.util.TextEncryptUtil;

public class EncryptPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {
	static Logger log = LoggerFactory.getLogger(EncryptPropertyPlaceholderConfigurer.class);
	
	private String key;
	private String iv;
	
	private List<String> encryptPropNameList;
	
	public EncryptPropertyPlaceholderConfigurer(String key, String iv) {
		super();
		this.key = key;
		this.iv = iv;
	}
	
	@Override  
    protected String convertProperty(String propertyName, String propertyValue) {
        if (encryptPropNameList != null && encryptPropNameList.contains(propertyName)) {  
            try {
				return TextEncryptUtil.decryptAES(key, iv, propertyValue);
			} catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException
					| NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException
					| InvalidAlgorithmParameterException e) {
				log.error("convertProperty Error:", e);
				throw new RuntimeException(e);
			}
        }  
  
        return super.convertProperty(propertyName, propertyValue);  
    }
    
    public List<String> getEncryptPropNameList() {  
        return encryptPropNameList;  
    }
  
    public void setEncryptPropNameList(List<String> encryptPropNameList) {  
        this.encryptPropNameList = encryptPropNameList;  
    }
    
    public void setEncryptPropNames(String encryptPropNames) {
    	List<String> encryptPropNameList = Splitter.on(',')
    		.trimResults()
    		.omitEmptyStrings()
    		.splitToList(encryptPropNames);
        this.encryptPropNameList = encryptPropNameList;  
    }
}
