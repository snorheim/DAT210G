package storing;

import logic.Loggy;

import org.hibernate.*;

public class UpdateDatabase {
	private static boolean successful;

	public static boolean updatePictureTitle(int picId, String title) {
		Session dbSession = HibernateUtil.getSessionFactory().openSession();
		Transaction dbTransaction = null;
		try {
			dbTransaction = dbSession.beginTransaction();
			Query query = dbSession
					.createQuery("UPDATE PictureDb SET title=:title WHERE id=:picId");
			query.setParameter("title", title);
			query.setParameter("picId", picId);
			query.executeUpdate();
			dbTransaction.commit();
			successful = true;
		} catch (HibernateException e) {
			if (dbTransaction != null)
				dbTransaction.rollback();
			successful = false;
		} finally {
			dbSession.close();
		}
		return successful;
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
			successful = true;
		} catch (HibernateException e) {
			if (dbTransaction != null)
				dbTransaction.rollback();
			successful = false;
		} finally {
			dbSession.close();
		}
		return successful;
	}

	public static boolean updatePictureRating(int picId, int rating) {
		Session dbSession = HibernateUtil.getSessionFactory().openSession();
		Transaction dbTransaction = null;
		try {
			dbTransaction = dbSession.beginTransaction();
			Query query = dbSession
					.createQuery("UPDATE PictureDb SET rating=:rating WHERE id=:picId");
			query.setParameter("rating", rating);
			query.setParameter("picId", picId);
			query.executeUpdate();
			dbTransaction.commit();
			successful = true;
		} catch (HibernateException e) {
			if (dbTransaction != null)
				dbTransaction.rollback();
			successful = false;
		} finally {
			dbSession.close();
		}
		return successful;
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
			successful = true;
		} catch (HibernateException e) {
			if (dbTransaction != null)
				dbTransaction.rollback();
			successful = false;
		} finally {
			dbSession.close();
		}
		return successful;
	}

	public static boolean updatePictureFileLocation(int picId,
			String fileLocation) {
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
			successful = true;
		} catch (HibernateException e) {
			if (dbTransaction != null)
				dbTransaction.rollback();
			successful = false;
		} finally {
			dbSession.close();
		}
		return successful;
	}

	public static boolean updatePictureMediumFileLocation(int picId,
			String medFileLocation) {
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
			successful = true;
		} catch (HibernateException e) {
			if (dbTransaction != null)
				dbTransaction.rollback();
			successful = false;
		} finally {
			dbSession.close();
		}
		return successful;
	}

	public static boolean updatePictureThumbnailLocation(int picId,
			String thumbnailLocation) {
		Session dbSession = HibernateUtil.getSessionFactory().openSession();
		Transaction dbTransaction = null;
		try {
			dbTransaction = dbSession.beginTransaction();
			Query query = dbSession
					.createQuery("UPDATE PictureDb SET "
							+ "thumbnailFileLocation=:thumbnailLocation WHERE id=:picId");
			query.setParameter("thumbnailLocation", thumbnailLocation);
			query.setParameter("picId", picId);
			query.executeUpdate();
			dbTransaction.commit();
			successful = true;
		} catch (HibernateException e) {
			if (dbTransaction != null)
				dbTransaction.rollback();
			successful = false;
		} finally {
			dbSession.close();
		}
		return successful;
	}

	// TODO: husk update paths hvis parent updates
	// hvis bildet flyttes saa slettes det fra DB og legges inn paa ny?
	// public static boolean updatePictureParent(int pictureId, int parentId) {
	// Session dbSession = HibernateUtil.getSessionFactory().openSession();
	// Transaction dbTransaction = null;
	// try {
	// dbTransaction = dbSession.beginTransaction();
	// Query query =
	// dbSession.createQuery("UPDATE PictureDb SET parentFolderId=:parentId "
	// + "WHERE id=:id");
	// query.setParameter("parentId", parentId);
	// query.setParameter("id", pictureId);
	// query.executeUpdate();
	// dbTransaction.commit();
	// successful = true;
	// } catch (HibernateException e) {
	// if(dbTransaction != null) dbTransaction.rollback();
	// successful = false;
	// } finally {
	// dbSession.close();
	// }
	// return successful;
	// }

	private static void log(String message) {
		Loggy.log(message, Loggy.DB_UPDATE);
	}

}
