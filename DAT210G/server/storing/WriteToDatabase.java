package storing;

import java.util.ArrayList;

import org.hibernate.*;

public class WriteToDatabase {
	private static boolean successfulTransfer;

	public static int writeOnePic(PictureDb picture) {
		Session dbSession = HibernateUtil.getSessionFactory().openSession();
		Transaction dbTransaction = null;
		int pictureId = 0;
		try {
			dbTransaction = dbSession.beginTransaction();
			dbSession.save(picture);
			pictureId = picture.getId();
			dbTransaction.commit();
		} catch (HibernateException e) {
			if (dbTransaction != null)
				dbTransaction.rollback();
		} finally {
			dbSession.close();
		}
		System.out.println(pictureId + " <----------------------------------------------------");
		return pictureId;
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
			Query query = dbSession
					.createQuery("FROM PictureDb WHERE id = :picId");
			query.setParameter("picId", picId);
			PictureDb picFromDb = (PictureDb) query.uniqueResult();
			query = dbSession.createQuery("FROM TagDb WHERE tag = :tag");
			query.setParameter("tag", tag);
			TagDb tagFromDb = (TagDb) query.uniqueResult();
			picFromDb.addTag(tagFromDb);
			dbTransaction.commit();
			successfulTransfer = true;
		} catch (HibernateException e) {
			successfulTransfer = false;
			if (dbTransaction != null)
				dbTransaction.rollback();
		} finally {
			dbSession.close();
		}

		return successfulTransfer;
	}

	public static boolean ensureImgFolderDatabase() {
		Session dbSession = HibernateUtil.getSessionFactory().openSession();
		Transaction dbTransaction = null;
		int[] allPicIds = ReadFromDatabase.getAllPicIds();
		for (int i : allPicIds) {
			DeleteFromDatabase.deletePicture(i);
		}
		ParentFolderDb newImageFolder = new ParentFolderDb("img", "img\\", 1, 2);
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
			e.printStackTrace();
			successfulTransfer = false;
			if (dbTransaction != null)
				dbTransaction.rollback();
		} finally {
			dbSession.close();
		}
		return successfulTransfer;
	}

}
