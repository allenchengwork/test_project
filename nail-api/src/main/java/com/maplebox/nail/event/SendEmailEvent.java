package com.maplebox.nail.event;

import java.util.List;

import javax.mail.internet.InternetAddress;

import org.springframework.context.ApplicationEvent;

public class SendEmailEvent extends ApplicationEvent {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6418835742492630286L;
	
	private List<InternetAddress> fromAddress;
	private List<InternetAddress> toAddress;

	public SendEmailEvent(Object source, List<InternetAddress> fromAddress, List<InternetAddress> toAddress) {
		super(source);
		this.fromAddress = fromAddress;
		this.toAddress = toAddress;
	}

	public List<InternetAddress> getFromAddress() {
		return fromAddress;
	}

	public void setFromAddress(List<InternetAddress> fromAddress) {
		this.fromAddress = fromAddress;
	}

	public List<InternetAddress> getToAddress() {
		return toAddress;
	}

	public void setToAddress(List<InternetAddress> toAddress) {
		this.toAddress = toAddress;
	}
}
