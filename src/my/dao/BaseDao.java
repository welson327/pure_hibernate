package my.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.Order;

public interface BaseDao<T> {
	public boolean insert(T entity);
	
	/*
	 * Find by entity
	 * @param exampleEntity - entity with specific value to query
	 * @param order - order by field
	 * @param offset - start index
	 * @param limit - return length
	 * @note entity.id will be ignore to 
	 */
	public List<T> list(T entity, Order order, int offset, int limit);
	
	/*
	 * Find by id
	 * @param cls - class for return specific ORM object
	 * @param id - id
	 */
	public T findById(Class<T> cls, Serializable id);
	
	/*
	 * Find by SQL
	 * @param queryStr - ex: String query = "select id,name from City where stateId=?";
	 * @param cls - class for return specific ORM object
	 */
	public List<T> findBySql(String sqlStr, Class cls);
}
