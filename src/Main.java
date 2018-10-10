
import java.util.Date;
import java.util.List;

import my.dao.EventDao;
import my.dao.impl.EventDaoImpl;
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
		
		// query
		List<Event> list = eventDao.list(e, null, 0, 3);
		for(Event x : list) {
			System.out.println("Event: " + x.getName());
		}
	}
}
