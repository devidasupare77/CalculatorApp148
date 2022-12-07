package com.smarthomeloans.finanace.app.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.smarthomeloans.finanace.app.model.CustomerRegistration;
import com.smarthomeloans.finanace.app.model.Documents;
import com.smarthomeloans.finanace.app.model.EmailSender;
import com.smarthomeloans.finanace.app.repository.DocumentRepository;
import com.smarthomeloans.finanace.app.repository.RelationshipExecutiveRepository;
import com.smarthomeloans.finanace.app.serviceI.RelationshipExecutiveServiceI;

@Service
public class RelationshipExecutiveServiceImpl implements RelationshipExecutiveServiceI {
	
	@Autowired
	RelationshipExecutiveRepository rer;
	
	@Autowired
	DocumentRepository dr;
	
	@Autowired
	JavaMailSender sender;


	@Override
	public void savedetails(CustomerRegistration cr) {
		rer.save(cr);
	}

	@Override
	public List<CustomerRegistration> viewclient() {
		
		return rer.findAll();
	}

	@Override
	public Optional<CustomerRegistration> findCustomer(String customerName) {
		              Optional<CustomerRegistration> op=rer.findByCustomerName(customerName);
		return op;
		
	}

	@Override
	public CustomerRegistration editdetails(int customerRegId) {
		CustomerRegistration op=rer.findById(customerRegId).get();
		return  op;
	}

	@Override
	public Optional<CustomerRegistration> findById(int customerRegId) {
		
		return rer.findById(customerRegId);
	}

	@Override
	public List<CustomerRegistration> findAcceptedCustomers(String cibilStatus) {
	return	rer.findByCibilStatus(cibilStatus);
	
	}

	@Override
	public Optional<Documents> findDocument(int documentid) {
		
	    return dr.findById(documentid);
	     
		 
	     
	}

	public void sendEmail(EmailSender es) {
		// TODO Auto-generated method stub
		SimpleMailMessage message=new SimpleMailMessage();
		message.setFrom(es.getFrom());
		message.setTo(es.getTo());
		message.setSubject(es.getSub());
		message.setText(es.getBody());
		sender.send(message);
		System.out.println("email sent...");
		
	}

}
