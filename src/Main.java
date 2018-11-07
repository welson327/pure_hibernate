
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Order;

import my.dao.EventDao;
import my.dao.impl.EventDaoImpl;
import my.hibernate.HibernateSessionFactory;
import my.model.Event;

public class Main {
	public static void main(String[] args) {
		test_hibernate();
	}
	
	private static void test_hibernate() {
		EventDao eventDao = new EventDaoImpl();
		
		// insert
		Date date = new Date();
		Event e = new Event();
		e.setName("name-" + date.getTime());
		e.setDatetime(date);
		eventDao.insert(e);
		System.out.println("Insert: id=" + e.getId());
		
		// find by id
		e = eventDao.findById(Event.class, e.getId());
		System.out.println("1. Event: " + e.getName());
				
		// query
		List<Event> list = eventDao.list(e, Order.desc("datetime"), 0, 3);
		for(Event x : list) {
			System.out.println("2. Event: " + x.getName());
		}
		
		// query by native sql
		List<Event> listBySql = eventDao.findBySql("SELECT events.* from events ORDER BY datetime DESC limit 3 offset 0", Event.class);
		for(Event x : listBySql) {
			System.out.println("3. Event: " + x.getName());
		}
		
		System.out.println("session open count: " + HibernateSessionFactory.getSessionOpenCount());
		HibernateSessionFactory.closeSession();
	}
}
