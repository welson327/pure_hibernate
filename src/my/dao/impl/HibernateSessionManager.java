package my.dao.impl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.hibernate.Session;
import org.hibernate.Transaction;
import my.hibernate.HibernateSessionFactory;

public class HibernateSessionManager {
	public interface HibernateCallback<T> {
		public T doInHibernate(Session session);
	}
	
	/*
	 * Execute session template
	 * @param hc - HibernateCallback
	 * @note eg: execTemplate(new HibernateCallback() {
	 *   @Override
	 *   public T doInHibernate(Session session) {
	 *     // do your work
	 *   }
	 * })
	 */
	public <E> E execTemplate(HibernateCallback<E> hc) {
		E result = null;
		Session s = getSession();
        Transaction tx = null;
		
		try {
			tx = s.beginTransaction();
			result = hc.doInHibernate(s); // equals to: s.saveOrUpdate(entity);
			tx.commit();
		} catch(Exception e) {
			e.printStackTrace();
			if(tx != null) tx.rollback();
		} finally {
			if(s != null) {
				s.close();
				s = null;
			}
		}
		return result;
	}
	
	protected Session getSession() {
		return HibernateSessionFactory.getSession();
	}
	
	
	
	
	
	
	
	//------------------------------ Deprecated! ---------------------------------------//
	//------------------------------ Deprecated! ---------------------------------------//
	//------------------------------ Deprecated! ---------------------------------------//
	/*
	 * Create a session proxy about auto-close session after call each method
	 */
	protected Session getSessionProxy(boolean isAutoCloseAfterOperation) {
		Session s = getSession();
		if(isAutoCloseAfterOperation) {
			return (Session) Proxy.newProxyInstance(
	                			s.getClass().getClassLoader(),
	                			s.getClass().getInterfaces(),
	                			new SessionInvocationHandler(s));
		} else {
			return s;
		}
	}
	
	private class SessionInvocationHandler implements InvocationHandler {
        private Session session = null;

        public SessionInvocationHandler(Session s) {
            this.session = s;
        }
        
        private void DBG(String msg) {
        	System.out.printf("--- %s: %s ---\n", getClass(), msg);
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Object result = null;
            Session s = this.session;
            Transaction tx = null;
            try {
            	DBG("session begin transaction");
    			tx = s.beginTransaction();
    			
    			//s.saveOrUpdate(entity);
    			result = method.invoke(s, args);
    			
    			tx.commit();
    		} catch(Exception e) {
    			e.printStackTrace();
    			if(tx != null) tx.rollback();
    		} finally {
    			if(s != null) {
    				DBG("session closed");
    				s.close();
    				s = null;
    			}
    		}
            return result;
        }
    }
}
