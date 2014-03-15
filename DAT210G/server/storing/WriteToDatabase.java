package storing;

import java.util.ArrayList;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class WriteToDatabase {
	private static boolean successfulTransfer;

	public static boolean writeOnePic(PictureDb pic) {
		Session dbSession = HibernateUtil.getSessionFactory().openSession();
		Transaction dbTransaction = null;
		try {
			dbTransaction = dbSession.beginTransaction();
			dbSession.save(pic);
			dbTransaction.commit();
			successfulTransfer = true;
		} catch (HibernateException e) {
			successfulTransfer = false;
			if (dbTransaction != null)
				dbTransaction.rollback();
		} finally {
			dbSession.close();
			HibernateUtil.shutdown();
		}
		return successfulTransfer;
	}

	public static boolean writeManyPics(ArrayList<PictureDb> picList) {
		Session dbSession = HibernateUtil.getSessionFactory().openSession();
		Transaction dbTransaction = null;
		try {
			dbTransaction = dbSession.beginTransaction();
			for (PictureDb pic : picList) {
				dbSession.save(pic);
			}
			dbTransaction.commit();
			successfulTransfer = true;
		} catch (HibernateException e) {
			successfulTransfer = false;
			if (dbTransaction != null)
				dbTransaction.rollback();
		} finally {
			dbSession.close();
			HibernateUtil.shutdown();
		}
		return successfulTransfer;
	}

	public static boolean writeTag(TagDb tag) {
		Session dbSession = HibernateUtil.getSessionFactory().openSession();
		Transaction dbTransaction = null;
		try {
			dbTransaction = dbSession.beginTransaction();
			dbSession.save(tag);
			dbTransaction.commit();
			successfulTransfer = true;
		} catch (HibernateException e) {
			successfulTransfer = false;
			if (dbTransaction != null)
				dbTransaction.rollback();
		} finally {
			dbSession.close();
			HibernateUtil.shutdown();
		}
		return successfulTransfer;
	}

	public static boolean addTagToPic(int picId, String tag) {
		TagDb tagDb = new TagDb(tag);
		writeTag(tagDb);
		Session dbSession = HibernateUtil.getSessionFactory().openSession();
		Transaction dbTransaction = null;
		try {
			dbTransaction = dbSession.beginTransaction();
			PictureDb picFromDb = (PictureDb) dbSession.load(PictureDb.class,
					picId);
			TagDb tagFromDb = (TagDb) dbSession.load(TagDb.class, tag);
			picFromDb.addTag(tagFromDb);
			dbTransaction.commit();
			successfulTransfer = true;
		} catch (HibernateException e) {
			successfulTransfer = false;
			if (dbTransaction != null)
				dbTransaction.rollback();
		} finally {
			dbSession.close();
			HibernateUtil.shutdown();
		}

		return successfulTransfer;
	}

	// TODO: brukes ved ensureFolder, snakk med Kjelli
	public static boolean ensureImgFolderDatabase() {
		Session dbSession = HibernateUtil.getSessionFactory().openSession();
		Transaction dbTransaction = null;
		int[] allPicIds = ReadFromDatabase.getAllPicIds();
		for (int i : allPicIds) {
			DeleteFromDatabase.deletePicture(i);
		}
		ParentFolderDb newImageFolder = new ParentFolderDb("img", "\\img\\", 1,
				2);
		try {
			dbTransaction = dbSession.beginTransaction();
			Query query = dbSession.createQuery("DELETE FROM TagDb");
			query.executeUpdate();
			query = dbSession.createQuery("DELETE FROM ParentFolderDb");
			query.executeUpdate();
			dbSession.save(newImageFolder);
			dbTransaction.commit();
			successfulTransfer = true;
		} catch (HibernateException e) {
			successfulTransfer = false;
			if (dbTransaction != null)
				dbTransaction.rollback();
		} finally {
			dbSession.close();
			HibernateUtil.shutdown();
		}
		return successfulTransfer;
	}

	public static boolean addFolderInAFolderWithOtherChildren(
			ParentFolderDb folder, int leftFolderId) {
		Session dbSession = HibernateUtil.getSessionFactory().openSession();
		Transaction dbTransaction = null;
		try {
			dbTransaction = dbSession.beginTransaction();
			Query query = dbSession
					.createQuery("SELECT rgt FROM ParentFolderDb WHERE folderId=:leftFolderId");
			query.setParameter("leftFolderId", leftFolderId);
			int right = (int) query.uniqueResult();

			query = dbSession
					.createQuery("UPDATE ParentFolderDb SET rgt = rgt + 2 WHERE rgt > :rgtRight");
			query.setParameter("rgtRight", right);
			query.executeUpdate();

			query = dbSession
					.createQuery("UPDATE ParentFolderDb SET lft = lft + 2 WHERE lft > :rgtRight");
			query.setParameter("rgtRight", right);
			query.executeUpdate();

			folder.setLft(right + 1);
			folder.setRgt(right + 2);
			dbSession.save(folder);
			dbTransaction.commit();
			successfulTransfer = true;
		} catch (HibernateException e) {
			successfulTransfer = false;
			if (dbTransaction != null)
				dbTransaction.rollback();
		} finally {
			dbSession.close();
			HibernateUtil.shutdown();
		}
		return successfulTransfer;
	}

	public static boolean addFolderAsAnOnlyChildToFolder(ParentFolderDb folder,
			int parentId) {
		Session dbSession = HibernateUtil.getSessionFactory().openSession();
		Transaction dbTransaction = null;
		try {
			dbTransaction = dbSession.beginTransaction();
			Query query = dbSession
					.createQuery("SELECT lft FROM ParentFolderDb WHERE folderId=:parentId");
			query.setParameter("parentId", parentId);
			int left = (int) query.uniqueResult();

			query = dbSession
					.createQuery("UPDATE ParentFolderDb SET rgt = rgt + 2 WHERE rgt > :lftLeft");
			query.setParameter("lftLeft", left);
			query.executeUpdate();

			query = dbSession
					.createQuery("UPDATE ParentFolderDb SET lft = lft + 2 WHERE lft > :lftLeft");
			query.setParameter("lftLeft", left);
			query.executeUpdate();

			folder.setLft(left + 1);
			folder.setRgt(left + 2);
			dbSession.save(folder);
			dbTransaction.commit();
			successfulTransfer = true;
		} catch (HibernateException e) {
			successfulTransfer = false;
			if (dbTransaction != null)
				dbTransaction.rollback();
		} finally {
			dbSession.close();
			HibernateUtil.shutdown();
		}
		return successfulTransfer;
	}

}
