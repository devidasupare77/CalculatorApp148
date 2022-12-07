package com.smarthomeloans.finanace.app.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smarthomeloans.finanace.app.exception.RecordNotFoundException;
import com.smarthomeloans.finanace.app.model.Address;
import com.smarthomeloans.finanace.app.model.CustomerRegistration;
import com.smarthomeloans.finanace.app.model.Documents;
import com.smarthomeloans.finanace.app.model.EmailSender;
import com.smarthomeloans.finanace.app.serviceI.RelationshipExecutiveServiceI;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@CrossOrigin("*")
@RestController
@RequestMapping("/smartHomeLoan")
public class FinanaceController {

	@Autowired
	RelationshipExecutiveServiceI res;
	
	@Value("${spring.mail.username}")
	private String from;

	@PostMapping("/customerRegistration")
	public ResponseEntity<String> saveClient(@RequestParam("panCard") MultipartFile filepan,
			@RequestParam("adharCard") MultipartFile fileadhar,
			@RequestParam("electricityBill") MultipartFile fileelectricity,
			@RequestParam("signature") MultipartFile filesign, @RequestParam("itr") MultipartFile fileitr,
			@RequestParam("photo") MultipartFile filephoto, @RequestPart("details") String details) {
		try {
			ObjectMapper om = new ObjectMapper();
			CustomerRegistration cr = new CustomerRegistration();
			Documents d = new Documents();
			d.setAdharCard(fileadhar.getBytes());
			d.setPanCard(filepan.getBytes());
			d.setPhoto(filephoto.getBytes());
			d.setElectricityBill(fileelectricity.getBytes());
			d.setItr(fileitr.getBytes());
			d.setSignature(filesign.getBytes());

			CustomerRegistration cf = om.readValue(details, CustomerRegistration.class);
			cr.setDocuments(d);
			cr.setCustomerName(cf.getCustomerName());
			cr.setMobileNumber(cf.getMobileNumber());
			cr.setPanNo(cf.getPanNo());
			cr.setEmail(cf.getEmail());
			cr.setAdharNo(cf.getAdharNo());
			cr.setGender(cf.getGender());
			cr.setLoanAmount(cf.getLoanAmount());
			cr.setTenure(cf.getTenure());
			cr.setSalary(cf.getSalary());
			cr.setCibilStatus(cf.getCibilStatus());
			cr.setRemark(cf.getRemark());
			cr.setDob(cf.getDob());

			Address adr = new Address();
			adr.setAreaName(cf.getAddress().getAreaName());
			adr.setCityName(cf.getAddress().getCityName());
			adr.setDistrict(cf.getAddress().getDistrict());
			adr.setPincode(cf.getAddress().getPincode());
			adr.setState(cf.getAddress().getState());

			cr.setAddress(adr);

			res.savedetails(cr);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Failed to register cutomer", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>("Customer registered", HttpStatus.CREATED);
	}
 
	
	
	@GetMapping("/registeredCustomers")
	public ResponseEntity<List<CustomerRegistration>> viewall() {
		List<CustomerRegistration> regList = res.viewclient();
		return new ResponseEntity<List<CustomerRegistration>>(regList, HttpStatus.OK);
	}

	
	
	@GetMapping("/findCustomer/{customerName}")
	public ResponseEntity<CustomerRegistration> findCustomer(@PathVariable String customerName)
			throws RecordNotFoundException {
		Optional<CustomerRegistration> op = res.findCustomer(customerName);
		if (op.isPresent()) {
			return new ResponseEntity<CustomerRegistration>(op.get(), HttpStatus.OK);
		} else {
			throw new RecordNotFoundException("Details are not present for this name");
		}
	}

	
	
	 @PutMapping("/generateCibilStatus/{customerRegId}")
	  public ResponseEntity<String> putCustomerCibil(@PathVariable int customerRegId)
	  { 
		  int min=550; int max=999;
	  int cibil=(int)(Math.random()*(max-min+1)+min);
	  CustomerRegistration cr=new CustomerRegistration();
	  cr.setCibil(cibil);
	       if(cr.getCibil()<750) 
	       {
		   CustomerRegistration op=res.editdetails(customerRegId);
		   op.setCibil(cr.getCibil());
		   op.setCibilStatus("Rejected");
		   res.savedetails(op);
	       }
	       else {
		   CustomerRegistration op=res.editdetails(customerRegId);
		   op.setCibil(cr.getCibil());
		   op.setCibilStatus("Accepted");
		   res.savedetails(op);
		   }
	        return new ResponseEntity<String>("cibil added succesfully",HttpStatus.OK);
        }  
	
	 
	 @GetMapping("/findAcceptedCustomers/{Accepted}")
	 public ResponseEntity<List<CustomerRegistration>> findAcceptedCustomers(@PathVariable("Accepted") String cibilStatus )
	 {
		 List<CustomerRegistration> ac=res.findAcceptedCustomers(cibilStatus);
		return new ResponseEntity<List<CustomerRegistration>>(ac,HttpStatus.OK);
		 
	 }
	 
	 @GetMapping("/findDocument/{documentid}")
	 public ResponseEntity<Optional<Documents>> findDocument(@PathVariable int documentid)
	 {
		 
		Optional<Documents> doc=res.findDocument(documentid);
		return new ResponseEntity<Optional<Documents>>(doc,HttpStatus.OK);
		 
	 }
	 
	 @PutMapping("/docVerify/{customerRegId}")
	 public ResponseEntity<String> docVerified(@PathVariable int customerRegId)
	 {
		
		      CustomerRegistration op=res.editdetails(customerRegId);
			   op.setDocumentStatus("Verified");
			   EmailSender es=new EmailSender();
			   es.setFrom(from);
			   es.setTo(op.getEmail());
			   es.setSub("Document Verification");
			   es.setBody("Congratulations, your all documents are successfully verified.\r\n"
			   		+ "\r\n"
			   		+ "your status are forwared to loan sactionaning officer,kindly wait for next update.\r\n"
			   		+ "\r\n"
			   		+"Document Verification Ofiicer.\r\n"
			   		+"Albert Robert");
				try{
					res.sendEmail(es);
				}
				catch(Exception e2)
				{
					return new ResponseEntity<String>("email not sent",HttpStatus.SERVICE_UNAVAILABLE);
				}
				
				log.info("call sendEmail()");
				
			   
			   res.savedetails(op);
		        return new ResponseEntity<String>("Document Successfully verified",HttpStatus.OK);
		  
	 }
	 
	 @PutMapping("/docReject/{customerRegId}")
	 public ResponseEntity<String> docRejected(@PathVariable int customerRegId)
	 {
		
		      CustomerRegistration op=res.editdetails(customerRegId);
			   op.setDocumentStatus("Rejected");
			   
			   EmailSender es=new EmailSender();
			   es.setFrom(from);
			   es.setTo(op.getEmail());
			   es.setSub("Document Verification");
			   es.setBody("It has been observed that you have not submitted your documents required for employee verification such as.\r\n"
			   		+ "\r\n"
			   		+ "Adhaar card,Pan Card,ITR,Electricity Bill.\r\n"
			   		+"\r\n"
			   		+"You are hereby warned to submit all the documents,Kindly treat this as very urgent.\r\n"
			   		+ "\r\n"
			   		+"Document Verification Ofiicer.\r\n"
			   		+"Albert Robert");
				try{
					res.sendEmail(es);
				}
				catch(Exception e2)
				{
					return new ResponseEntity<String>("email not sent",HttpStatus.SERVICE_UNAVAILABLE);
				}
				
				log.info("call sendEmail()");
				
				 res.savedetails(op);
		        return new ResponseEntity<String>("Document Rejected",HttpStatus.NOT_ACCEPTABLE);
		  
			   
			 }

	 
}
