package com.maplebox.nail.domain;

import java.io.Serializable;

import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;

public class ImageCacheKey implements Serializable, Comparable<ImageCacheKey> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2574434812538830925L;
	
	private String type;
	private String fileName;
	
	public ImageCacheKey(String type, String fileName) {
		super();
		this.type = type;
		this.fileName = fileName;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	@Override
	public int hashCode() {
		return Objects.hashCode(type, fileName);
	}
	
	@Override
	public boolean equals(Object object) {
		if (object instanceof ImageCacheKey) {
			ImageCacheKey that = (ImageCacheKey) object;
			return Objects.equal(this.type, that.type) && Objects.equal(this.fileName, that.fileName);
		}
		return false;
	}
	
	@Override
	public int compareTo(ImageCacheKey that) {
		return ComparisonChain.start().compare(this.type, that.type).compare(this.fileName, that.fileName).result();
	}
}
