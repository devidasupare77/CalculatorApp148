package com.smarthomeloans.finanace.app.model;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CustomerRegistration {

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer customerRegId;
	private String customerName;
	private Long mobileNumber;
	private String email;
	private String panNo;
	private Long adharNo;
	private String gender;
	private Long loanAmount;
	private Integer tenure;
	private Double salary;
	private String remark;
	private String dob;	
	private Integer cibil;
	private String cibilStatus;
	private String documentStatus;
	@OneToOne(cascade = CascadeType.ALL)
	private Documents documents;
	@OneToOne(cascade = CascadeType.ALL)
	private Address address;
	@OneToOne(cascade = CascadeType.ALL)
	private LoanDetails loanDetails;

}
