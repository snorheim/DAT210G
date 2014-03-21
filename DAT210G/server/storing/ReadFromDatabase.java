package storing;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
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
			HibernateUtil.shutdown();
		}
		return tagList;
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
			HibernateUtil.shutdown();
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
			HibernateUtil.shutdown();
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
			HibernateUtil.shutdown();
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
			HibernateUtil.shutdown();
		}
		return picList;
	}

	public static int[] getPicturesBasedOnTag(String tag, int folderId) {
		List<PictureDb> returnList = new ArrayList<>();
		Session dbSession = HibernateUtil.getSessionFactory().openSession();
		Transaction dbTransaction = null;
		try {
			dbTransaction = dbSession.beginTransaction();
			List<PictureDb> picList = getPictureFolderSubfolderMetaData(
					folderId, dbSession);
			for (PictureDb picture : picList) {
				for (TagDb t : picture.getTags()) {
					if (t.getTag().equals(tag)) {
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
			HibernateUtil.shutdown();
		}
		int[] pictureIdArray = new int[returnList.size()];
		for (int i = 0; i < pictureIdArray.length; i++) {
			pictureIdArray[i] = returnList.get(i).getId();
		}
		return pictureIdArray;
	}

	public static List<PictureDb> getPicturesBasedOnManyTags(String[] tag,
			int folderId) {
		List<PictureDb> returnList = new ArrayList<>();
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
							if (!returnList.contains(picture)) {
								returnList.add(picture);
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
			HibernateUtil.shutdown();
		}
		return returnList;
	}

	public static int[] getPicturesBasedOnRating(int rating, int folderId) {
		List<PictureDb> returnList = new ArrayList<>();
		Session dbSession = HibernateUtil.getSessionFactory().openSession();
		Transaction dbTransaction = null;
		try {
			dbTransaction = dbSession.beginTransaction();
			List<PictureDb> picList = getPictureFolderSubfolderMetaData(
					folderId, dbSession);
			for (PictureDb picture : picList) {
				if (picture.getRating() >= rating) {
					returnList.add(picture);
				}
			}
			dbTransaction.commit();
		} catch (HibernateException e) {
			if (dbTransaction != null)
				dbTransaction.rollback();
		} finally {
			dbSession.close();
			HibernateUtil.shutdown();
		}
		int[] pictureIdArray = new int[returnList.size()];
		for (int i = 0; i < pictureIdArray.length; i++) {
			pictureIdArray[i] = returnList.get(i).getId();
		}
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
			HibernateUtil.shutdown();
		}
		int[] pictureIdArray = new int[returnList.size()];
		for (int i = 0; i < pictureIdArray.length; i++) {
			pictureIdArray[i] = returnList.get(i).getId();
		}
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
			HibernateUtil.shutdown();
		}
		return picture;
	}

	@SuppressWarnings("unchecked")
	public static int[] getAllPicIds() {
		List<Integer> picIdList = null;
		Session dbSession = HibernateUtil.getSessionFactory().openSession();
		Transaction dbTransaction = null;
		try {
			dbTransaction = dbSession.beginTransaction();
			Query query = dbSession
					.createQuery("SELECT id FROM PictureDb ORDER BY dateTime DESC");
			picIdList = query.list();
			dbTransaction.commit();
		} catch (HibernateException e) {
			if (dbTransaction != null)
				dbTransaction.rollback();
		} finally {
			dbSession.close();
			HibernateUtil.shutdown();
		}
		int[] tempArray = new int[picIdList.size()];
		for (int i = 0; i < tempArray.length; i++) {
			tempArray[i] = picIdList.get(i);
		}
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
			HibernateUtil.shutdown();
		}
		String allTagsAsString = "";
		for (TagDb tag : tagList) {
			allTagsAsString += tag.getTag() + ";";
		}
		return allTagsAsString;
	}

	@SuppressWarnings("unchecked")
	public static int[] getImagesInAFolder(int folderId) {
		int[] imageIdArray;
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
			HibernateUtil.shutdown();
		}
		imageIdArray = new int[pictureFromDb.size()];
		for (int i = 0; i < imageIdArray.length; i++) {
			imageIdArray[i] = pictureFromDb.get(i).getId();
		}
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
			HibernateUtil.shutdown();
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
			HibernateUtil.shutdown();
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
			if (dbTransaction != null)
				dbTransaction.rollback();
		} finally {
			dbSession.close();
			HibernateUtil.shutdown();
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
		List<PictureDb> tmp = null;
		try {
			dbTransaction = dbSession.beginTransaction();
			Query query = null;
			for (int i : folderIds) {
				query = dbSession
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
			HibernateUtil.shutdown();
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
		dbSession = HibernateUtil.getSessionFactory().openSession();
		List<PictureDb> tmp = null;
		try {
			Query query = null;
			for (int i : folderIds) {
				query = dbSession
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
			HibernateUtil.shutdown();
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
			HibernateUtil.shutdown();
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
			Query query = null;
			for (int i : folderIds) {
				query = dbSession
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
			HibernateUtil.shutdown();
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

}
