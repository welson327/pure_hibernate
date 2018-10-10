package my.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;

import my.dao.BaseDao;

public class BaseDaoImpl<T> extends HibernateBaseDao implements BaseDao<T> {
	@Override
	public boolean insert(T entity) {
		getSession().saveOrUpdate(entity);
		return true;
	}

	@Override
	public List list(T entity, Order order, int offset, int limit) {
		Criteria c = getSession().createCriteria(entity.getClass());
		
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
	
}
