package storing;

import java.util.ArrayList;
import java.util.List;

import logic.Loggy;

import org.hibernate.*;
import org.hibernate.criterion.Restrictions;

public class ReadFromDatabase {

	@SuppressWarnings("unchecked")
	public static List<TagDb> getAllTags() {
		List<TagDb> tagList = null;
		Session dbSession = HibernateUtil.getSessionFactory().openSession();
		Transaction dbTransaction = null;
		try {
			dbTransaction = dbSession.beginTransaction();
			Query query = dbSession.createQuery("FROM TagDb");
			tagList = query.list();
			dbTransaction.commit();
		} catch (HibernateException e) {
			if (dbTransaction != null)
				dbTransaction.rollback();
		} finally {
			dbSession.close();
		}
		return tagList;
	}

	public static int getNewestPicId() {
		int picId = -1;
		Session dbSession = HibernateUtil.getSessionFactory().openSession();
		Transaction dbTransaction = null;
		try {
			dbTransaction = dbSession.beginTransaction();
			Query query = dbSession
					.createQuery("SELECT MAX(id) FROM PictureDb");
			picId = (int) query.uniqueResult();
			dbTransaction.commit();
		} catch (HibernateException e) {
			if (dbTransaction != null)
				dbTransaction.rollback();
		} finally {
			dbSession.close();
		}
		return picId;
	}

	public static int getPictureFromPath(String path) {
		Session dbSession = HibernateUtil.getSessionFactory().openSession();
		Transaction dbTransaction = null;
		int pictureId = 0;
		try {
			dbTransaction = dbSession.beginTransaction();
			Query query = dbSession
					.createQuery("SELECT id FROM PictureDb WHERE fileLocation = :path");
			query.setParameter("path", path);
			pictureId = (int) query.uniqueResult();
			dbTransaction.commit();
		} catch (HibernateException e) {
			if (dbTransaction != null)
				dbTransaction.rollback();
		} finally {
			dbSession.close();
		}
		return pictureId;
	}

	@SuppressWarnings("unchecked")
	public static String[] getTagsStartingWith(String tagStart) {
		List<TagDb> tagsFromDb = null;
		Session dbSession = HibernateUtil.getSessionFactory().openSession();
		Transaction dbTransaction = null;
		String[] tagStringList = null;
		try {
			dbTransaction = dbSession.beginTransaction();
			Query query = dbSession
					.createQuery("FROM TagDb WHERE tag LIKE :tagStart");
			query.setParameter("tagStart", tagStart.toLowerCase() + "%");
			tagsFromDb = query.list();
		} catch (HibernateException e) {
			if (dbTransaction != null)
				dbTransaction.rollback();
		} finally {
			dbSession.close();
		}
		tagStringList = new String[tagsFromDb.size()];
		for (int i = 0; i < tagStringList.length; i++) {
			tagStringList[i] = tagsFromDb.get(i).getTag();
		}
		return tagStringList;
	}

	public static int findNextPicId() {
		int nextPicId = 0;
		Session dbSession = HibernateUtil.getSessionFactory().openSession();
		Transaction dbTransaction = null;
		try {
			dbTransaction = dbSession.beginTransaction();
			Query query = dbSession
					.createQuery("SELECT max(id) FROM PictureDb");
			if (!(query.uniqueResult() == null)) {
				nextPicId = (int) query.uniqueResult() + 1;
			}
			dbTransaction.commit();
		} catch (HibernateException e) {
			if (dbTransaction != null)
				dbTransaction.rollback();
		} finally {
			dbSession.close();
		}
		if (nextPicId == 0) {
			return 1;
		}
		return nextPicId;
	}

	@SuppressWarnings("unchecked")
	public static List<PictureDb> getAllPictures() {
		List<PictureDb> picList = null;
		Session dbSession = HibernateUtil.getSessionFactory().openSession();
		Transaction dbTransaction = null;
		try {
			dbTransaction = dbSession.beginTransaction();
			picList = dbSession.createQuery(
					"FROM PictureDb ORDER BY dateTime DESC").list();
			dbTransaction.commit();
		} catch (HibernateException e) {
			if (dbTransaction != null)
				dbTransaction.rollback();
		} finally {
			dbSession.close();
		}
		return picList;
	}

	public static int[] getPicturesBasedOnTag(String tag, int folderId) {
		List<PictureDb> pictureList = new ArrayList<>();
		Session dbSession = HibernateUtil.getSessionFactory().openSession();
		Transaction dbTransaction = null;
		try {
			dbTransaction = dbSession.beginTransaction();
			List<PictureDb> picList = getPictureFolderSubfolderMetaData(
					folderId, dbSession);
			for (PictureDb picture : picList) {
				for (TagDb t : picture.getTags()) {
					if (t.getTag().equals(tag)) {
						pictureList.add(picture);
					}
				}
			}
			dbTransaction.commit();
		} catch (HibernateException e) {
			if (dbTransaction != null)
				dbTransaction.rollback();
		} finally {
			dbSession.close();
		}
		int[] pictureIdArray = idArrayFromPictureArray(pictureList);
		return pictureIdArray;
	}

	private static int[] idArrayFromPictureArray(List<PictureDb> pictureList) {
		int[] pictureIdArray = new int[pictureList.size()];
		for (int i = 0; i < pictureIdArray.length; i++) {
			pictureIdArray[i] = pictureList.get(i).getId();
		}
		return pictureIdArray;
	}

	public static int[] getPicturesBasedOnManyTags(String[] tag, int folderId) {
		List<PictureDb> tmpPictureList = new ArrayList<>();
		Session dbSession = HibernateUtil.getSessionFactory().openSession();
		Transaction dbTransaction = null;
		try {
			dbTransaction = dbSession.beginTransaction();
			List<PictureDb> picList = getPictureFolderSubfolderMetaData(
					folderId, dbSession);
			for (PictureDb picture : picList) {
				for (TagDb t : picture.getTags()) {
					for (String tagString : tag) {
						if (t.getTag().equals(tagString)) {
							if (!tmpPictureList.contains(picture)) {
								tmpPictureList.add(picture);
							}
						}
					}
				}
			}
			dbTransaction.commit();
		} catch (HibernateException e) {
			if (dbTransaction != null)
				dbTransaction.rollback();
		} finally {
			dbSession.close();
		}
		int[] imageIdArray = idArrayFromPictureArray(tmpPictureList);
		return imageIdArray;
	}

	public static int[] getPicturesBasedOnTitle(String title, int folderId) {
		List<PictureDb> picList = new ArrayList<>();
		Session dbSession = HibernateUtil.getSessionFactory().openSession();
		Transaction dbTransaction = null;
		try {
			dbTransaction = dbSession.beginTransaction();
			List<PictureDb> pictureDbList = getPictureFolderSubfolderMetaData(
					folderId, dbSession);
			for (PictureDb picture : pictureDbList) {
				if (picture.getTitle() != null) {
					if (picture.getTitle().equals(title)) {
						picList.add(picture);
					}
				}
			}
			dbTransaction.commit();
		} catch (HibernateException e) {
			if (dbTransaction != null)
				dbTransaction.rollback();
		} finally {
			dbSession.close();
		}
		int[] pictureIdArray = idArrayFromPictureArray(picList);
		return pictureIdArray;
	}

	public static int[] getPicturesBasedOnDesc(String description, int folderId) {
		List<PictureDb> returnList = new ArrayList<>();
		Session dbSession = HibernateUtil.getSessionFactory().openSession();
		Transaction dbTransaction = null;
		try {
			dbTransaction = dbSession.beginTransaction();
			List<PictureDb> picList = getPictureFolderSubfolderMetaData(
					folderId, dbSession);
			for (PictureDb picture : picList) {
				if (picture.getDescription() != null) {
					if (picture.getDescription().matches(
							".*" + description + ".*")) {
						returnList.add(picture);
					}
				}
			}
			dbTransaction.commit();
		} catch (HibernateException e) {
			if (dbTransaction != null)
				dbTransaction.rollback();
		} finally {
			dbSession.close();
		}
		int[] pictureIdArray = idArrayFromPictureArray(returnList);
		return pictureIdArray;
	}

	public static int[] getPicturesBasedOnRating(int rating, int folderId) {
		List<PictureDb> pictureDbList = new ArrayList<>();
		Session dbSession = HibernateUtil.getSessionFactory().openSession();
		Transaction dbTransaction = null;
		try {
			dbTransaction = dbSession.beginTransaction();
			List<PictureDb> picList = getPictureFolderSubfolderMetaData(
					folderId, dbSession);
			for (PictureDb picture : picList) {
				if (picture.getRating() >= rating) {
					pictureDbList.add(picture);
				}
			}
			dbTransaction.commit();
		} catch (HibernateException e) {
			if (dbTransaction != null)
				dbTransaction.rollback();
		} finally {
			dbSession.close();
		}
		int[] pictureIdArray = idArrayFromPictureArray(pictureDbList);
		return pictureIdArray;
	}

	public static int[] getPicturesBasedOnDate(String timeDate, int folderId) {
		List<PictureDb> returnList = new ArrayList<>();
		Session dbSession = HibernateUtil.getSessionFactory().openSession();
		Transaction dbTransaction = null;
		try {
			dbTransaction = dbSession.beginTransaction();
			List<PictureDb> picList = getPictureFolderSubfolderMetaData(
					folderId, dbSession);
			for (PictureDb picture : picList) {
				if (picture.getDateTime() != null) {
					if (picture.getDateTime().matches(".*" + timeDate + ".*")) {
						returnList.add(picture);
					}
				}
			}
			dbTransaction.commit();
		} catch (HibernateException e) {
			if (dbTransaction != null)
				dbTransaction.rollback();
		} finally {
			dbSession.close();
		}
		int[] pictureIdArray = idArrayFromPictureArray(returnList);
		return pictureIdArray;
	}

	public static PictureDb getPictureBasedOnId(int picId) {
		PictureDb picture = null;
		Session dbSession = HibernateUtil.getSessionFactory().openSession();
		Transaction dbTransaction = null;
		try {
			dbTransaction = dbSession.beginTransaction();
			Query query = dbSession
					.createQuery("FROM PictureDb WHERE id=:picId");
			query.setParameter("picId", picId);
			picture = (PictureDb) query.uniqueResult();
			dbTransaction.commit();
		} catch (HibernateException e) {
			if (dbTransaction != null)
				dbTransaction.rollback();
		} finally {
			dbSession.close();
		}
		return picture;
	}

	@SuppressWarnings("unchecked")
	public static int[] getAllPicIds() {
		List<PictureDb> picIdList = null;
		Session dbSession = HibernateUtil.getSessionFactory().openSession();
		Transaction dbTransaction = null;
		try {
			dbTransaction = dbSession.beginTransaction();
			Query query = dbSession
					.createQuery("FROM PictureDb ORDER BY dateTime DESC");
			picIdList = query.list();
			dbTransaction.commit();
		} catch (HibernateException e) {
			if (dbTransaction != null)
				dbTransaction.rollback();
		} finally {
			dbSession.close();
		}
		int[] tempArray = idArrayFromPictureArray(picIdList);
		return tempArray;
	}

	@SuppressWarnings("unchecked")
	public static String getAllTagsForAPicture(int picId) {
		List<TagDb> tagList = null;
		Session dbSession = HibernateUtil.getSessionFactory().openSession();
		Transaction dbTransaction = null;
		try {
			dbTransaction = dbSession.beginTransaction();
			Criteria criteria = dbSession.createCriteria(TagDb.class);
			criteria.createAlias("pics", "pic");
			criteria.add(Restrictions.eq("pic.id", picId));
			tagList = criteria.list();
			dbTransaction.commit();
		} catch (HibernateException e) {
			if (dbTransaction != null)
				dbTransaction.rollback();
		} finally {
			dbSession.close();
		}
		String allTagsAsString = "";
		for (TagDb tag : tagList) {
			allTagsAsString += tag.getTag() + ";";
		}
		return allTagsAsString;
	}

	@SuppressWarnings("unchecked")
	public static int[] getImagesInAFolder(int folderId) {
		Session dbSession = HibernateUtil.getSessionFactory().openSession();
		Transaction dbTransaction = null;
		List<PictureDb> pictureFromDb = null;
		try {
			dbTransaction = dbSession.beginTransaction();
			Query query = dbSession
					.createQuery("FROM PictureDb WHERE parentFolderId=:parent ORDER BY dateTime DESC");
			query.setParameter("parent", folderId);
			pictureFromDb = query.list();
			dbTransaction.commit();
		} catch (HibernateException e) {
			if (dbTransaction != null)
				dbTransaction.rollback();
		} finally {
			dbSession.close();
		}
		int[] imageIdArray = idArrayFromPictureArray(pictureFromDb);
		return imageIdArray;
	}

	public static ParentFolderDb getFolderInfo(int folderId) {
		Session dbSession = HibernateUtil.getSessionFactory().openSession();
		Transaction dbTransaction = null;
		ParentFolderDb folder = null;
		try {
			dbTransaction = dbSession.beginTransaction();
			Query query = dbSession
					.createQuery("FROM ParentFolderDb WHERE folderId=:folderId");
			query.setParameter("folderId", folderId);
			folder = (ParentFolderDb) query.uniqueResult();
			dbTransaction.commit();
		} catch (HibernateException e) {
			if (dbTransaction != null)
				dbTransaction.rollback();
		} finally {
			dbSession.close();
		}
		return folder;
	}

	@SuppressWarnings("unchecked")
	public static List<ParentFolderDb> getFolderAndSubFolderInfo(
			int startFolderId) {
		Session dbSession = HibernateUtil.getSessionFactory().openSession();
		Transaction dbTransaction = null;
		List<ParentFolderDb> foldersFromDb = null;
		ParentFolderDb st = null;
		try {
			dbTransaction = dbSession.beginTransaction();
			Query query = dbSession
					.createQuery("FROM ParentFolderDb WHERE folderId = :startFolderId");
			query.setParameter("startFolderId", startFolderId);
			st = (ParentFolderDb) query.uniqueResult();
			int leftParent = st.getLft();
			int rightParent = st.getRgt();
			query = dbSession
					.createQuery("FROM ParentFolderDb WHERE lft BETWEEN :lftParent AND :rgtParent ORDER BY lft ASC");
			query.setParameter("lftParent", leftParent);
			query.setParameter("rgtParent", rightParent);
			foldersFromDb = query.list();
			dbTransaction.commit();
		} catch (HibernateException e) {
			if (dbTransaction != null)
				dbTransaction.rollback();
		} finally {
			dbSession.close();
		}
		return foldersFromDb;
	}

	@SuppressWarnings("unchecked")
	public static int[] getFolderAndSubFolderId(int startFolderId) {
		Session dbSession = HibernateUtil.getSessionFactory().openSession();
		Transaction dbTransaction = null;
		int[] folderIds = null;
		List<ParentFolderDb> foldersFromDb = null;
		ParentFolderDb st = null;
		try {
			dbTransaction = dbSession.beginTransaction();
			Query query = dbSession
					.createQuery("FROM ParentFolderDb WHERE folderId = :startFolderId");
			query.setParameter("startFolderId", startFolderId);
			st = (ParentFolderDb) query.uniqueResult();
			int leftParent = st.getLft();
			int rightParent = st.getRgt();
			query = dbSession
					.createQuery("FROM ParentFolderDb WHERE lft BETWEEN :lftParent AND :rgtParent");
			query.setParameter("lftParent", leftParent);
			query.setParameter("rgtParent", rightParent);
			foldersFromDb = query.list();
			dbTransaction.commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			if (dbTransaction != null)
				dbTransaction.rollback();
		} finally {
			dbSession.close();
		}
		folderIds = new int[foldersFromDb.size()];
		for (int i = 0; i < folderIds.length; i++) {
			folderIds[i] = foldersFromDb.get(i).getFolderId();
		}
		return folderIds;
	}

	@SuppressWarnings("unchecked")
	public static int[] getPicturesInFolderAndSubFolderId(int startFolderId) {
		ArrayList<PictureDb> picturesFromDb = new ArrayList<>();
		int[] folderIds = getFolderAndSubFolderId(startFolderId);
		Session dbSession = HibernateUtil.getSessionFactory().openSession();
		Transaction dbTransaction = null;
		List<PictureDb> tmpList = null;
		try {
			dbTransaction = dbSession.beginTransaction();
			for (int i : folderIds) {
				Query query = dbSession
						.createQuery("FROM PictureDb WHERE parentId=:folderId ORDER BY dateTime DESC");
				query.setParameter("folderId", i);
				tmpList = query.list();
				picturesFromDb.addAll(tmpList);
			}
			dbTransaction.commit();
		} catch (HibernateException e) {
			if (dbTransaction != null)
				dbTransaction.rollback();
		} finally {
			dbSession.close();
		}
		int[] pictureIds = new int[picturesFromDb.size()];
		for (int i = 0; i < pictureIds.length; i++) {
			pictureIds[i] = picturesFromDb.get(i).getId();
		}
		return pictureIds;
	}

	@SuppressWarnings("unchecked")
	private static List<PictureDb> getPictureFolderSubfolderMetaData(
			int startFolderId, Session dbSession) {
		ArrayList<PictureDb> picturesFromDb = new ArrayList<>();
		int[] folderIds = getFolderAndSubFolderId(startFolderId);
		List<PictureDb> tmp = null;
		try {

			for (int i : folderIds) {
				Query query = dbSession
						.createQuery("FROM PictureDb WHERE parentId=:folderId ORDER BY dateTime DESC");
				query.setParameter("folderId", i);
				tmp = query.list();
				picturesFromDb.addAll(tmp);
			}
		} catch (HibernateException e) {

		}
		return picturesFromDb;
	}

	@SuppressWarnings("unchecked")
	private static List<ParentFolderDb> folderAndSubFolder(int startFolderId) {
		Session dbSession = HibernateUtil.getSessionFactory().openSession();
		Transaction dbTransaction = null;
		List<ParentFolderDb> foldersFromDb = null;
		ParentFolderDb st = null;
		try {
			dbTransaction = dbSession.beginTransaction();
			Query query = dbSession
					.createQuery("FROM ParentFolderDb WHERE folderId = :startFolderId");
			query.setParameter("startFolderId", startFolderId);
			st = (ParentFolderDb) query.uniqueResult();
			int leftParent = st.getLft();
			int rightParent = st.getRgt();
			query = dbSession
					.createQuery("FROM ParentFolderDb WHERE lft BETWEEN :lftParent AND :rgtParent");
			query.setParameter("lftParent", leftParent);
			query.setParameter("rgtParent", rightParent);
			foldersFromDb = query.list();
			dbTransaction.commit();
		} catch (HibernateException e) {
			if (dbTransaction != null)
				dbTransaction.rollback();
		} finally {
			dbSession.close();
		}
		return foldersFromDb;
	}

	public static IsNotOnlyChildObject isFolderOnlyChild(int parentFolderId) {
		IsNotOnlyChildObject child = new IsNotOnlyChildObject();
		child.setOnlyChild(true);
		List<ParentFolderDb> folders = folderAndSubFolder(parentFolderId);
		if (folders.size() > 1) {
			child.setOnlyChild(false);
			child.setLeftChildId(folders.get(1).getFolderId());
		}
		return child;
	}

	public static List<TreeMenuNode> getTreeForMenu() {
		List<ParentFolderDb> li = ReadFromDatabase.getFolderAndSubFolderInfo(1);
		List<TreeMenuNode> tList = new ArrayList<>();
		for (ParentFolderDb pFolder : li) {
			TreeMenuNode t = new TreeMenuNode(pFolder);
			tList.add(t);
		}
		for (TreeMenuNode trN : tList) {
			addChildren(trN, tList);
		}
		return tList;
	}

	private static void addChildren(TreeMenuNode root,
			List<TreeMenuNode> nodeList) {
		ArrayList<TreeMenuNode> children = new ArrayList<>();
		for (TreeMenuNode db : nodeList) {
			if (db.getRoot().getParentId() == root.getRoot().getFolderId()) {
				children.add(db);
			}
		}
		root.setChildren(children);
	}

	public static int getFolderID(String folderPath) {
		System.out.println("søker på denne pathen: " + folderPath);
		Session dbSession = HibernateUtil.getSessionFactory().openSession();
		Transaction dbTransaction = null;
		int folderId = 0;
		try {
			dbTransaction = dbSession.beginTransaction();
			Query query = dbSession
					.createQuery("SELECT folderId FROM ParentFolderDb WHERE path=:path");
			query.setParameter("path", folderPath);
			folderId = (int) query.uniqueResult();
		} catch (HibernateException e) {
			if (dbTransaction != null)
				dbTransaction.rollback();
		} finally {
			dbSession.close();
		}
		return folderId;
	}

	@SuppressWarnings("unchecked")
	public static List<PictureDb> getPicturesInFolderAndSubFolderInfo(
			int startFolderId) {
		ArrayList<PictureDb> picturesFromDb = new ArrayList<>();
		int[] folderIds = getFolderAndSubFolderId(startFolderId);
		Session dbSession = HibernateUtil.getSessionFactory().openSession();
		Transaction dbTransaction = null;
		List<PictureDb> tmp = null;
		try {
			dbTransaction = dbSession.beginTransaction();
			for (int i : folderIds) {
				Query query = dbSession
						.createQuery("FROM PictureDb WHERE parentId=:folderId ORDER BY dateTime DESC");
				query.setParameter("folderId", i);
				tmp = query.list();
				picturesFromDb.addAll(tmp);
			}
			dbTransaction.commit();
		} catch (HibernateException e) {
			if (dbTransaction != null)
				dbTransaction.rollback();
		} finally {
			dbSession.close();
		}
		return picturesFromDb;
	}

	public static class IsNotOnlyChildObject {
		int leftChildId;
		boolean isOnlyChild;

		public IsNotOnlyChildObject(int leftChildId, boolean isNotOnlyChild) {
			this.leftChildId = leftChildId;
			this.isOnlyChild = isNotOnlyChild;
		}

		public IsNotOnlyChildObject() {
		}

		public int getLeftChildId() {
			return leftChildId;
		}

		public void setLeftChildId(int leftChildId) {
			this.leftChildId = leftChildId;
		}

		public boolean isOnlyChild() {
			return isOnlyChild;
		}

		public void setOnlyChild(boolean isOnlyChild) {
			this.isOnlyChild = isOnlyChild;
		}

	}

	private static void log(String message) {
		Loggy.log(message, Loggy.DB_READ);
	}

}
