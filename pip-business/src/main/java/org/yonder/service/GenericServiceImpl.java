package org.yonder.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.yonder.utils.EntityManagerUtil;

public class GenericServiceImpl<E, K> implements GenericService<E, K>{

	protected EntityManager entityManager;
	
	final static Logger logger = Logger.getLogger(GenericServiceImpl.class);
	
	protected Class<? extends E> type;
	
	public GenericServiceImpl(Class<? extends E> type) {
		super();
		this.type = type;
		
		if (entityManager == null) {
			entityManager = EntityManagerUtil.getEntityManager();
			logger.info("Got Entity Manager. Continue...");
		}
	}

	public GenericServiceImpl() {
		if (entityManager == null) {
			entityManager = EntityManagerUtil.getEntityManager();
			logger.info("Got Entity Manager. Continue...");
		}
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public void add(E entity) {
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(entity);
			entityManager.getTransaction().commit();
			logger.info("Entity successfully added in the database.");
		} catch (RuntimeException e) {
			e.printStackTrace();
			logger.info("Exception caught trying to add entity.");
		}
		
	}

	@Override
	public void delete(K entityId) {
		try {
			entityManager.getTransaction().begin();
			E entity = (E) entityManager.find(type, entityId);
			if (entity != null) {
				entityManager.remove(entity);
				entityManager.getTransaction().commit();
				logger.info("Entity successfully deleted from the database. Continue.");
			} else {
				entityManager.getTransaction().rollback();
				logger.info("Entity could not be found in the database. Transaction was rolled back.");
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			logger.info("Exception caught trying to delete entity.");
		} 
		
	}

	@Override
	public void update(E entity) {
		try {
			entityManager.getTransaction().begin();
			entityManager.merge(entity);
			entityManager.getTransaction().commit();
			logger.info("Entity was successfully updated.");
		} catch (RuntimeException e) {
			e.printStackTrace();
			logger.info("Exception caught trying to update entity.");
		}
		
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<E> getAll() {
		List<E> entities = new ArrayList<E>();
		try {
			entityManager.getTransaction().begin();
			entities = entityManager.createQuery("from " + type.getSimpleName() + " " + type.getSimpleName().charAt(1)).getResultList();
			entityManager.getTransaction().commit();
			logger.info("Got all entities from the database. Continue.");
		} catch (RuntimeException e) {
			e.printStackTrace();
			logger.info("Exception caught trying to get all entities.");
		}
		return entities;
	}

	@Override
	public E getById(K id) {
		E entity = null;
		try {
			entityManager.getTransaction().begin();
			entity = (E) entityManager.find(type, id);
			entityManager.getTransaction().commit();
			logger.info("Entity found by id.");
		} catch (RuntimeException e) {
			e.printStackTrace();
			logger.info("Exception caught trying to get entity by id.");
		}
		return entity;
	}
	
}