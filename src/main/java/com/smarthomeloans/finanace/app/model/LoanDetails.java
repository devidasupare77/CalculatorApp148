package com.smarthomeloans.finanace.app.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer loanAccountNumber;
	private Long approvedLoanAmount;
	private Double emi;
	private Double roi;
	private String loanStatus;
	private Integer sanctionedTanure;
}
