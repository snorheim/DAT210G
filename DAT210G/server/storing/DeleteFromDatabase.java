package storing;

import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class DeleteFromDatabase {
	private static boolean succesful;
	
	
	public static boolean deletePicture(int picId) {
		Session dbSession = HibernateUtil.getSessionFactory().openSession();
		Transaction dbTransaction = null;
		try {
			dbTransaction = dbSession.beginTransaction();
			Query query = dbSession.createQuery("FROM PictureDb WHERE id=:picId");
			query.setParameter("picId", picId);
			PictureDb picFromDb = (PictureDb) query.uniqueResult();
			Set<TagDb> tagsToPic = removeTagOnPictureDelete(picFromDb);
			picFromDb.getTags().removeAll(tagsToPic);
			dbSession.delete(picFromDb);
			dbTransaction.commit();
			succesful = true;
		} catch (HibernateException e) {
			if (dbTransaction != null) dbTransaction.rollback();
			succesful = false;
		} finally {
 			dbSession.close();
			HibernateUtil.shutdown();
		}
		return succesful;
	}


	private static Set<TagDb> removeTagOnPictureDelete(PictureDb picFromDb) {
		Set<TagDb> tagsToPic = picFromDb.getTags();
		for (TagDb tag: tagsToPic) {
			tag.getPics().remove(picFromDb);
		}
		return tagsToPic;
	}
	
	public static boolean deleteTagFromPicture(int pictureId, String tag) {
		Session dbSession = HibernateUtil.getSessionFactory().openSession();
		Transaction dbTransaction = null;
		try {
			dbTransaction = dbSession.beginTransaction();
			PictureDb pictureFromDb = (PictureDb) dbSession.load(PictureDb.class, pictureId);
			pictureFromDb.getTags().remove(tag);
			TagDb tagFromDb = (TagDb) dbSession.load(TagDb.class, tag);
			tagFromDb.getPics().remove(pictureFromDb);
			dbTransaction.commit();
			succesful = true;
		} catch (HibernateException e) {
			if (dbTransaction != null) dbTransaction.rollback();
			succesful = false;
		} finally {
 			dbSession.close();
			HibernateUtil.shutdown();
		}
		return succesful;
	}
	
//	public static boolean deleteTagsFromPic(int picId) {
//		Session dbSession = HibernateUtil.getSessionFactory().openSession();
//		Transaction dbTransaction = null;
//		try {
//			dbTransaction = dbSession.beginTransaction();
//			PictureDb picFromDb = (PictureDb) dbSession.load(PictureDb.class, picId);
////			picFromDb.getTags().removeAll(picFromDb.getTags());
//			dbTransaction.commit();
//			succesful = true;
//		} catch (HibernateException e) {
//			if (dbTransaction != null) dbTransaction.rollback();
//			succesful = false;
//		} finally {
// 			dbSession.close();
//			HibernateUtil.shutdown();
//		}
//		return succesful;
//	}
	
	
}
