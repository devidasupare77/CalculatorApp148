package com.smarthomeloans.finanace.app.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import lombok.Data;

@Data
@Entity
public class Documents {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer documentid;
	@Lob
	private byte[] adharCard;
	@Lob
	private byte[] panCard;
	@Lob
	private byte[] photo;
	@Lob
	private byte[] electricityBill;
	@Lob
	private byte[] itr;
	@Lob
    private byte[] signature;
	
}
