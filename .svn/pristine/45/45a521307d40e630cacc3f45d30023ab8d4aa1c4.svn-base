package org.yonder.utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerUtil {
	
	private static final EntityManagerFactory entityManagerFactory;
    static {
      try {
        entityManagerFactory = Persistence.createEntityManagerFactory("pip-persistence");
        

      } catch (Throwable ex) {          
        throw new ExceptionInInitializerError(ex);
      }
    }

    public static EntityManager getEntityManager() {
      return entityManagerFactory.createEntityManager();

    }
}
