package com.maplebox.table;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Photo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5368312652562620162L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length = 10)
	private String name;
	
	@Column(length = 128)
	private String path;
	
	@Column(length = 128)
	private String description;
}
