package org.yonder.view;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.primefaces.event.SelectEvent;
import org.yonder.model.AddressModel;
import org.yonder.model.PersonModel;
import org.yonder.service.GenericServiceImpl;
import org.yonder.services.TestService;

@ManagedBean(name = "personView")
@ViewScoped
public class PersonView {

	private List<PersonModel> persons;
	@ManagedProperty("#{address}")
	private AddressModel address;
	private List<AddressModel> addresses;
	@ManagedProperty("#{person}")
	private PersonModel person;
	private PersonModel selectedPerson;

	@ManagedProperty("#{testService}")
	private TestService testService;
	
	final static Logger logger = Logger.getLogger(PersonView.class);

	@PostConstruct
	public void init() {
		logger.info("Initializing Person View...");
	}
	
	public void addPerson() {
		List<AddressModel> tempAddress = new ArrayList<AddressModel>();
		tempAddress.add(address);
		logger.info("ADDRESS ID: " + address.getId());
		PersonModel tempPerson = person;
		logger.info("PERSON ID: " + tempPerson.getId());
		person.setAddresses(tempAddress);
		logger.info("Nume: " + tempPerson.getName());
		logger.info("Varsta: " + tempPerson.getAge());
		logger.info("Ocupatie: " + tempPerson.getOccupation());
		logger.info("Adresa: " + tempPerson.getAddresses().get(0).getStreetName() + " " 
				+ tempPerson.getAddresses().get(0).getStreetNumber() + " " 
				+ tempPerson.getAddresses().get(0).getCity());
		testService.savePerson(tempPerson);
		
		tempPerson = new PersonModel();

	}
	
	public void deletePerson() {
		
		int personId = Integer.parseInt(person.getId());
		testService.deletePerson(personId);
		init();
	}
	
	public void updatePerson() {
		testService.updatePerson(person);
		init();
	}

	public PersonModel getPerson() {
		return person;
	}

	public void setPerson(PersonModel person) {
		this.person = person;
	}

	public List<PersonModel> getPersons() {
		return testService.getAllPersons();
	}

	public AddressModel getAddress() {
		return address;
	}

	public void setAddress(AddressModel address) {
		this.address = address;
	}

	public List<AddressModel> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<AddressModel> addresses) {
		this.addresses = addresses;
	}

	public void setTestService(TestService testService) {
		this.testService = testService;
	}

	public PersonModel getSelectedPerson() {
		return selectedPerson;
	}

	public void setSelectedPerson(PersonModel selectedPerson) {
		this.selectedPerson = selectedPerson;
	}
	
	public void onRowSelect(SelectEvent event) {
        FacesMessage msg = new FacesMessage("Person Selected", ((PersonModel) event.getObject()).getId());
        FacesContext.getCurrentInstance().addMessage(null, msg);
        selectedPerson = (PersonModel) event.getObject();
        person = selectedPerson;
    }
	
}
