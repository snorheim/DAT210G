package logic;

import java.util.ArrayList;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class WriteToDatabase {
	
	//ok
	public static void writeOnePic(PictureDb pic){
		Session dbSession = HibernateUtil.getSessionFactory().openSession();
		Transaction dbTransaction = dbSession.beginTransaction();
		dbSession.save(pic);
		dbTransaction.commit();
		dbSession.close();
		HibernateUtil.shutdown();
	}
	
	//ok
	public static void writeManyPics(ArrayList<PictureDb> picList) {
		Session dbSession = HibernateUtil.getSessionFactory().openSession();
		Transaction dbTransaction = dbSession.beginTransaction();
		for (PictureDb pic: picList) {
			dbSession.save(pic);
		}
		dbTransaction.commit();
		dbSession.close();
		HibernateUtil.shutdown();
	}
	
	//ok
	public static void writeTag(TagDb tag) {
		Session dbSession = HibernateUtil.getSessionFactory().openSession();
		Transaction dbTransaction = dbSession.beginTransaction();
		dbSession.save(tag);
		dbTransaction.commit();
		dbSession.close();
		HibernateUtil.shutdown();
	}
	
	//ok
	public static void writeManyTags(ArrayList<TagDb> tagList) {
		Session dbSession = HibernateUtil.getSessionFactory().openSession();
		Transaction dbTransaction = dbSession.beginTransaction();
		for (TagDb tag: tagList) {
			dbSession.save(tag);
		}
		dbTransaction.commit();
		dbSession.close();
		HibernateUtil.shutdown();
	}
	
	//ok
	public static void addTagToPic(int picId, String tag) {
		Session dbSession = HibernateUtil.getSessionFactory().openSession();
		dbSession.beginTransaction();
		PictureDb picFromDb = (PictureDb) dbSession.load(PictureDb.class, picId);
		TagDb tagFromDb = (TagDb) dbSession.load(TagDb.class, tag);
		picFromDb.addTag(tagFromDb);
		dbSession.getTransaction().commit();
		HibernateUtil.shutdown();
	}
	
	//ok
	public static void addManyTagsToPic(int picId, ArrayList<String> tagList) {
		Session dbSession = HibernateUtil.getSessionFactory().openSession();
		dbSession.beginTransaction();
		PictureDb picFromDb = (PictureDb) dbSession.load(PictureDb.class, picId);
		for (String tag: tagList)  {
			TagDb tagFromDb = (TagDb) dbSession.load(TagDb.class, tag);
			picFromDb.addTag(tagFromDb);
		}
		dbSession.getTransaction().commit();
		HibernateUtil.shutdown();
	}
	
	//ok
	public static void addManyTagsToManyPics(ArrayList<Integer> picIdList, ArrayList<String> tagList) {
		Session dbSession = HibernateUtil.getSessionFactory().openSession();
		dbSession.beginTransaction();
		for (Integer picId: picIdList) {
			PictureDb picFromDb = (PictureDb) dbSession.load(PictureDb.class, picId);
			for (String tag: tagList) {
				TagDb tagFromDb = (TagDb) dbSession.load(TagDb.class, tag);
				picFromDb.addTag(tagFromDb);
			}
		}
		dbSession.getTransaction().commit();
		HibernateUtil.shutdown();
	}
	
}
