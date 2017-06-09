package org.yonder.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;

import org.yonder.entity.Person;

@Local
public interface PersonService extends GenericService<Person, Integer>{
	
//	public void addPerson(Person person);
//	
//	public void deletePerson(int personId);
//	
//	public void updatePerson(Person person);
//	
//	public List<Person> getAllPersons();
//	
//	public Person getPersonById(int personId);
	
	public Person getPersonByName(String personName);
	
}
