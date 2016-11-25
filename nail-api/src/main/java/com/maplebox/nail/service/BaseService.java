package com.maplebox.nail.service;

import java.lang.reflect.InvocationTargetException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true, timeout = 10, rollbackFor = Throwable.class)
public abstract class BaseService {
	static Logger log = LoggerFactory.getLogger(BaseService.class);
	
	@PersistenceContext
    private EntityManager entityManager;
	
	@Autowired
	private EntityManagerFactory entityManagerFactory;
	
	protected EntityManager getEntityManager() {
		return entityManager;
	}
	
	protected EntityManagerFactory getEntityManagerFactory() {
		return entityManagerFactory;
	}
	
	protected Session getSession() {
        return entityManager.unwrap(Session.class);
    }
	
	protected void nullAwareBeanCopy(Object dest, Object source) throws IllegalAccessException, InvocationTargetException {
	    new BeanUtilsBean() {
	        @Override
	        public void copyProperty(Object dest, String name, Object value)
	                throws IllegalAccessException, InvocationTargetException {
	            if(value != null) {
	                super.copyProperty(dest, name, value);
	            }
	        }
	    }.copyProperties(dest, source);
	}
}

