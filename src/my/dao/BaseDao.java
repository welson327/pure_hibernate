package my.dao;

import java.util.List;
import org.hibernate.criterion.Order;

public interface BaseDao<T> {
	public boolean insert(T entity);
	
	/*
	 * find by entity
	 * @param exampleEntity - entity with specific value
	 * @param order - order by field
	 * @param offset - start index
	 * @param limit - return length
	 */
	public List<T> list(T entity, Order order, int offset, int limit);
	
	/*
	 * find by SQL
	 * @param queryStr - ex: String query = "select id,name from City where stateId=?";
	 * @param cls - class for return specific ORM object
	 */
	public List<T> findBySql(String sqlStr, Class cls);
}
