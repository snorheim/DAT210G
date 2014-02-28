package storing;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class UpdateDatabase {
	private static boolean succesful;
	
	public static boolean updatePictureTitle(int picId, String title) {
		Session dbSession = HibernateUtil.getSessionFactory().openSession();
		Transaction dbTransaction = null;
		try {
			dbTransaction = dbSession.beginTransaction();
			Query query = dbSession.createQuery("UPDATE PictureDb SET title=:title WHERE id=:picId");
			query.setParameter("title", title);
			query.setParameter("picId", picId);
			query.executeUpdate();
			dbTransaction.commit();
			succesful = true;
		} catch (HibernateException e) {
			if(dbTransaction != null) dbTransaction.rollback();
			succesful = false;
		} finally {
			dbSession.close();
			HibernateUtil.shutdown();
		}
		return succesful;
	}
	
	public static boolean updatePictureDescription(int picId, String description) {
		Session dbSession = HibernateUtil.getSessionFactory().openSession();
		Transaction dbTransaction = null;
		try {
			dbTransaction = dbSession.beginTransaction();
			Query query = dbSession.createQuery("UPDATE PictureDb SET "
					+ "description=:description WHERE id=:picId");
			query.setParameter("description", description);
			query.setParameter("picId", picId);
			query.executeUpdate();
			dbTransaction.commit();
			succesful = true;
		} catch (HibernateException e) {
			if(dbTransaction != null) dbTransaction.rollback();
			succesful = false;
		} finally {
			dbSession.close();
			HibernateUtil.shutdown();
		}
		return succesful;
	}
	
	public static boolean updatePictureRating(int picId, int rating) {
		Session dbSession = HibernateUtil.getSessionFactory().openSession();
		Transaction dbTransaction = null;
		try {
			dbTransaction = dbSession.beginTransaction();
			Query query = dbSession.createQuery("UPDATE PictureDb SET rating=:rating WHERE id=:picId");
			query.setParameter("rating", rating);
			query.setParameter("picId", picId);
			query.executeUpdate();
			dbTransaction.commit();
			succesful = true;
		} catch (HibernateException e) {
			if(dbTransaction != null) dbTransaction.rollback();
			succesful = false;
		} finally {
			dbSession.close();
			HibernateUtil.shutdown();
		}
		return succesful;
	}
	
	public static boolean updatePictureTimeCreated(int picId, String timeCreated) {
		Session dbSession = HibernateUtil.getSessionFactory().openSession();
		Transaction dbTransaction = null;
		try {
			dbTransaction = dbSession.beginTransaction();
			Query query = dbSession.createQuery("UPDATE PictureDb SET "
					+ "dateTime=:timeCreated WHERE id=:picId");
			query.setParameter("timeCreated", timeCreated);
			query.setParameter("picId", picId);
			query.executeUpdate();
			dbTransaction.commit();
			succesful = true;
		} catch (HibernateException e) {
			if(dbTransaction != null) dbTransaction.rollback();
			succesful = false;
		} finally {
			dbSession.close();
			HibernateUtil.shutdown();
		}
		return succesful;
	}
	
	public static boolean updatePictureFileLocation(int picId, String fileLocation) {
		Session dbSession = HibernateUtil.getSessionFactory().openSession();
		Transaction dbTransaction = null;
		try {
			dbTransaction = dbSession.beginTransaction();
			Query query = dbSession.createQuery("UPDATE PictureDb SET "
					+ "fileLocation=:fileLocation WHERE id=:picId");
			query.setParameter("fileLocation", fileLocation);
			query.setParameter("picId", picId);
			query.executeUpdate();
			dbTransaction.commit();
			succesful = true;
		} catch (HibernateException e) {
			if(dbTransaction != null) dbTransaction.rollback();
			succesful = false;
		} finally {
			dbSession.close();
			HibernateUtil.shutdown();
		}
		return succesful;
	}
	
	public static boolean updatePictureMediumFileLocation(int picId, String medFileLocation) {
		Session dbSession = HibernateUtil.getSessionFactory().openSession();
		Transaction dbTransaction = null;
		try {
			dbTransaction = dbSession.beginTransaction();
			Query query = dbSession.createQuery("UPDATE PictureDb SET "
					+ "mediumFileLocation=:mediumFileLocation WHERE id=:picId");
			query.setParameter("mediumFileLocation", medFileLocation);
			query.setParameter("picId", picId);
			query.executeUpdate();
			dbTransaction.commit();
			succesful = true;
		} catch (HibernateException e) {
			if(dbTransaction != null) dbTransaction.rollback();
			succesful = false;
		} finally {
			dbSession.close();
			HibernateUtil.shutdown();
		}
		return succesful;
	}
	
	public static boolean updatePictureThumbnailLocation(int picId, String thumbnailLocation) {
		Session dbSession = HibernateUtil.getSessionFactory().openSession();
		Transaction dbTransaction = null;
		try {
			dbTransaction = dbSession.beginTransaction();
			Query query = dbSession.createQuery("UPDATE PictureDb SET "
					+ "thumbnailFileLocation=:thumbnailLocation WHERE id=:picId");
			query.setParameter("thumbnailLocation", thumbnailLocation);
			query.setParameter("picId", picId);
			dbTransaction.commit();
			succesful = true;
		} catch (HibernateException e) {
			if(dbTransaction != null) dbTransaction.rollback();
			succesful = false;
		} finally {
			dbSession.close();
			HibernateUtil.shutdown();
		}
		return succesful;
	}
	
	
	
}