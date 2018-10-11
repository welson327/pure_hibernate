package my.dao.impl;

import org.hibernate.Session;

import my.hibernate.HibernateSessionFactory;

public class HibernateSessionManager {
	protected Session getSession() {
		return HibernateSessionFactory.getSession();
	}
}
