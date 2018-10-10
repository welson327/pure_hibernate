package my.dao.impl;

import org.hibernate.Session;

import my.hibernate.HibernateSessionFactory;

public class HibernateBaseDao {
	protected Session getSession() {
		return HibernateSessionFactory.getSession();
	}
}
