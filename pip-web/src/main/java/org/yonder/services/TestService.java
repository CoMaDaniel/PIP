package org.yonder.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.yonder.entity.Address;
import org.yonder.entity.Person;
import org.yonder.entity.User;
import org.yonder.model.AddressModel;
import org.yonder.model.PersonModel;
import org.yonder.service.GenericServiceImpl;
import org.yonder.service.PersonService;
import org.yonder.service.UserService;

@ManagedBean(name = "testService")
@ApplicationScoped
public class TestService {

	@Inject
	private PersonService personService;

	@Inject
	private UserService userService;

	final static Logger logger = Logger.getLogger(GenericServiceImpl.class);

	public PersonModel getPersonByName(String name) {

		PersonModel person = new PersonModel();

		Person dbPerson = new Person();

		dbPerson = personService.getPersonByName(name);

		person = entityToModel(dbPerson);

		return person;
	}

	public List<PersonModel> getAllPersons() {
		List<PersonModel> persons = new ArrayList<PersonModel>();

		List<Person> dbPersons = new ArrayList<Person>();

		dbPersons = personService.getAll();

		for (int i = 0; i < dbPersons.size(); i++) {
			persons.add(entityToModel(dbPersons.get(i)));
		}

		return persons;
	}

	public PersonModel getPersonById(int id) {

		PersonModel person = new PersonModel();
		Person dbPerson = new Person();

		dbPerson = personService.getById(id);
		person = entityToModel(dbPerson);

		return person;

	}

	public void savePerson(PersonModel person) {

		FacesMessage message = null;

		try {
			Person dbPerson = modelToEntity(person);
			personService.add(dbPerson);
		} catch (Exception e) {
			logger.info("Could not add person to the database");
			e.printStackTrace();
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid data", "Invalid data entered. Try again.");
		}

	}

	private PersonModel entityToModel(Person dbPerson) {

		PersonModel personModel = new PersonModel();

		personModel.setId(Integer.toString(dbPerson.getId()));
		personModel.setAge(Integer.toString(dbPerson.getAge()));
		personModel.setName(dbPerson.getName());
		personModel.setOccupation(dbPerson.getOccupation());
		personModel.setAddresses(new ArrayList<AddressModel>());
		Iterator<Address> iterator = dbPerson.getAddresses().iterator();
		while (iterator.hasNext()) {
			personModel.getAddresses().add(entityToModel(iterator.next()));
		}

		return personModel;
	}

	private AddressModel entityToModel(Address dbAddress) {

		AddressModel addressModel = new AddressModel();

		addressModel.setStreetName(dbAddress.getStreetName());
		addressModel.setStreetNumber(Integer.toString(dbAddress.getStreetNumber()));
		addressModel.setCity(dbAddress.getCity());

		return addressModel;
	}

	private Person modelToEntity(PersonModel person) {

		Person dbPerson = new Person();

		dbPerson.setName(person.getName());
		dbPerson.setAge(Integer.parseInt((person.getAge())));
		dbPerson.setOccupation(person.getOccupation());
		dbPerson.setAddresses(new HashSet<Address>());
		if (person.getAddresses() != null && person.getAddresses().size() != 0) {
			for (int i = 0; i < person.getAddresses().size(); i++) {
				dbPerson.getAddresses().add(modelToEntity(person.getAddresses().get(i)));
			}
		}
		return dbPerson;

	}

	private Address modelToEntity(AddressModel address) {

		Address dbAddress = new Address();

		dbAddress.setStreetName(address.getStreetName());
		dbAddress.setStreetNumber(Integer.parseInt(address.getStreetNumber()));
		dbAddress.setCity(address.getCity());

		return dbAddress;

	}

	public void updatePerson(PersonModel person) {
		Person dbPerson = modelToEntity(person);
		dbPerson.setId(Integer.parseInt(person.getId()));
		logger.info("Trying to update person with id=" + dbPerson.getId());
		personService.update(dbPerson);
	}

	public void deletePerson(int personId) {
		personService.delete(personId);
	}

	public String getPasswordForUser(String userName) {

		User dbUser = new User();
		String password = null;
		dbUser = userService.getUserByName(userName);

		if (dbUser != null) {
			password = dbUser.getPassword();
		}

		return password;
	}

}