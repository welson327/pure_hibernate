package my.dao;

import java.util.List;
import org.hibernate.criterion.Order;

public interface BaseDao<T> {
	public boolean insert(T entity);
	public List<T> list(T entity, Order order, int offset, int limit);
}
