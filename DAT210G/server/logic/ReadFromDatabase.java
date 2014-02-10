package logic;


import java.util.List;
import org.hibernate.Session;

public class ReadFromDatabase {
	
	//ok
	@SuppressWarnings("unchecked")
	public static List<TagDb> getAllTags() {
		Session dbSession = HibernateUtil.getSessionFactory().openSession();
		dbSession.beginTransaction();
		List<TagDb> tagList = dbSession.createQuery("FROM TagDb").list();
		dbSession.getTransaction().commit();
		HibernateUtil.shutdown();
		return tagList;
	}
	
	
	
	
}
