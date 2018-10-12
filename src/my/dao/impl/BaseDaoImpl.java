package my.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import my.dao.BaseDao;

public class BaseDaoImpl<T> extends HibernateSessionManager implements BaseDao<T> {
	@Override
	public boolean insert(T entity) {
		//getSession().saveOrUpdate(entity);
		
		execTemplate(new HibernateCallback<Object>() {
			@Override
			public Object doInHibernate(Session session) {
				session.saveOrUpdate(entity);
				return null;
			}
		});
		
		return true;
	}

	@Override
	public List<T> list(T entity, Order order, int offset, int limit) {
		List<T> ret = null;

		ret = execTemplate(new HibernateCallback<List<T>>() {
			@Override
			public List<T> doInHibernate(Session session) {
				Criteria c = session.createCriteria(entity.getClass());
				
				// hibernate 5.2+
				//CriteriaBuilder builder = getSession().getCriteriaBuilder();
				//CriteriaQuery c = builder.createQuery(entity.getClass());
				
				//c.add(Restrictions.gt("fieldname", new Integer(20)));
				//c.add(Restrictions.like("name", "just%"));
				
				if(order != null) {
					c.addOrder(order);
				}
				if(offset > -1) {
					c.setFirstResult(offset);
				}
				if(limit > -1) {
					c.setMaxResults(limit);
				}
				return c.list();
			}
		});
		
		return ret;
	}
	
	public List<T> findBySql(String sqlStr, Class cls) {
		return execTemplate(new HibernateCallback<List<T>>() {
			@Override
			public List<T> doInHibernate(Session session) {
				return queryBySql(getSession(), sqlStr, cls);
			}
		});
	}
	
	protected List<T> queryBySql(Session session, String sqlStr, Class cls) {
		SQLQuery q = session.createSQLQuery(sqlStr);
		//q.setParameterList("targetAdIdList", targetAdIdList);

		if(cls != null) {
			//q.addEntity(YourClass.class);
			q.addEntity(cls); // if no entity defined, it will return rows data in Object[]
		}
		
		//List rows = q.list(); // if no cls defined
		List<T> resultList = q.list();

		return resultList;
	}
}
