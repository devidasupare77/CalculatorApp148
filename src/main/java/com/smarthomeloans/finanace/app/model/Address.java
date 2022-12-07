package com.smarthomeloans.finanace.app.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Address {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer addrId;
	private String areaName;
	private String cityName;
	private String district;
	private String state;
	private long pincode;
	
}
