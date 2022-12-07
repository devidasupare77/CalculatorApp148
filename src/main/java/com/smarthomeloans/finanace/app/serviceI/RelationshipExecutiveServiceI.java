package com.smarthomeloans.finanace.app.serviceI;

import java.util.List;
import java.util.Optional;

import com.smarthomeloans.finanace.app.model.CustomerRegistration;
import com.smarthomeloans.finanace.app.model.Documents;
import com.smarthomeloans.finanace.app.model.EmailSender;

public interface RelationshipExecutiveServiceI {

	void savedetails(CustomerRegistration cr);

	List<CustomerRegistration> viewclient();

	Optional<CustomerRegistration> findCustomer(String customerName);

	CustomerRegistration editdetails(int customerRegId);

	Optional<CustomerRegistration> findById(int customerRegId);

	List<CustomerRegistration> findAcceptedCustomers(String cibilStatus);

	Optional<Documents> findDocument(int documentid);

	void sendEmail(EmailSender es);

	

	

}
