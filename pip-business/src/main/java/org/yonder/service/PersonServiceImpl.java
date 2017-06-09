package org.yonder.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import org.yonder.entity.Person;

//@EJB(name="java:global/yonder/service/PersonService", beanInterface=org.yonder.service.PersonService.class)
@Stateless
public class PersonServiceImpl extends GenericServiceImpl<Person, Integer> implements PersonService {
	
	public PersonServiceImpl() {
		super(Person.class);
	}
	
//	@Override
//	public void addPerson(Person person) {
//
//		try {
//			entityManager.getTransaction().begin();
//			entityManager.persist(person);
//			entityManager.getTransaction().commit();
//		} catch (RuntimeException e) {
//			e.printStackTrace();
//		}
//	}

//	@Override
//	public void deletePerson(int personId) {
//		try {
//			entityManager.getTransaction().begin();
//			Person person = (Person) entityManager.find(Person.class, personId);
//			if (person != null) {
//				entityManager.remove(person);
//				entityManager.getTransaction().commit();
//			} else {
//				entityManager.getTransaction().rollback();
//			}
//		} catch (RuntimeException e) {
//			e.printStackTrace();
//		} 
//	}

//	@Override
//	public void updatePerson(Person person) {
//		try {
//			entityManager.getTransaction().begin();
//			entityManager.merge(person);
//			entityManager.getTransaction().commit();
//		} catch (RuntimeException e) {
//			e.printStackTrace();
//		}
//	}

//	@Override
//	@SuppressWarnings("unchecked")
//	public List<Person> getAllPersons() {
//		List<Person> persons = new ArrayList<Person>();
//		try {
//			entityManager.getTransaction().begin();
//			persons = entityManager.createQuery("from Person p").getResultList();
//			entityManager.getTransaction().commit();
//		} catch (RuntimeException e) {
//			e.printStackTrace();
//		}
//		return persons;
//	}

//	@Override
//	public Person getPersonById(int personId) {
//		Person person = null;
//		try {
//			entityManager.getTransaction().begin();
//			person = (Person) entityManager.find(Person.class, personId);
//			entityManager.getTransaction().commit();
//		} catch (RuntimeException e) {
//			e.printStackTrace();
//		}
//		return person;
//	}

	@Override
	@SuppressWarnings("unchecked")
	public Person getPersonByName(String personName) {
		List<Person> persons = new ArrayList<Person>();
		try {
			entityManager.getTransaction().begin();
			Query query = entityManager.createQuery("select p from Person p where p.name= :personName");
			query.setParameter("personName", personName);
			entityManager.getTransaction().commit();
			persons = query.getResultList();
			logger.info("Successfully got person by name from the database.");
		} catch (RuntimeException e) {
			e.printStackTrace();
			logger.info("Exception caught trying to find person by name.");
		}

		if (persons != null && persons.size() >= 1) {
			logger.info("Person list size greater than or equal to 1. Returned the first entity in list.");
			return persons.get(0);
		} else {
			logger.info("Person list is empty. No person found for this criteria.");
			return null;
		}
	}

	// @Override
	// public Set<Address> getPersonAddresses(int personId) {
	// Person person = null;
	// Set<Address> addresses = new HashSet<Address>();
	// Transaction transaction = null;
	// Session session = openCurrentSession();
	// try {
	// transaction = session.beginTransaction();
	// String queryString = "from Person where id = :id";
	// Query query = session.createQuery(queryString);
	// query.setInteger("id", personId);
	// person = (Person) query.uniqueResult();
	// addresses = person.getAddresses();
	// } catch (RuntimeException e) {
	// e.printStackTrace();
	// } finally {
	// session.flush();
	// session.close();
	// }
	// return addresses;
	// }

}
