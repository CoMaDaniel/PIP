package org.yonder.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import org.yonder.entity.User;

@Stateless
public class UserServiceImpl extends GenericServiceImpl<User, Integer> implements UserService{
	
	public UserServiceImpl(){
		super(User.class);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public User getUserByName (String userName) {
		List<User> users = new ArrayList<User>();
		try {
			entityManager.getTransaction().begin();
			Query query = entityManager.createQuery("select u from User u where u.username= :userName");
			query.setParameter("userName", userName);
			entityManager.getTransaction().commit();
			users = query.getResultList();
			logger.info("Successfully got user by name from the database.");
		} catch (RuntimeException e) {
			e.printStackTrace();
			logger.info("Exception caught trying to find user by name.");
		}

		if (users != null && users.size() >= 1) {
			logger.info("Users list size greater than or equal to 1. Returned the first entity in list.");
			return users.get(0);
		} else {
			logger.info("User list is empty. No user found for this criteria.");
			return null;
		}
	}

}
