package org.yonder.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.yonder.entity.Address;

public class AddressServiceImpl extends GenericServiceImpl<Address, Integer> implements AddressService {

	public AddressServiceImpl() {
		super(Address.class);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Address> getAddressByCity(String cityName) {
		List<Address> addresses = new ArrayList<Address>();
		try {
			entityManager.getTransaction().begin();
			Query query = entityManager.createQuery("select a from Address a where a.city= :cityName");
			query.setParameter("cityName", cityName);
			entityManager.getTransaction().commit();
			addresses = query.getResultList();
			logger.info("Successfully got person by name from the database.");
		} catch (RuntimeException e) {
			e.printStackTrace();
			logger.info("Exception caught trying to find person by name.");
		}

		if (addresses != null && addresses.size() >= 1) {
			logger.info("Person list size greater than or equal to 1. Returned the first entity in list.");
			return addresses;
		} else {
			logger.info("Person list is empty. No person found for this criteria.");
			return null;
		}

	}

}