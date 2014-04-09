package storing;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import logic.Loggy;

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
		}
		return pictureId;
	}

	@SuppressWarnings("unchecked")
	public static String[] getTagsStartingWith(String tagStart) {
		List<TagDb> tagsFromDb = null;
		Session dbSession = HibernateUtil.getSessionFactory().openSession();
		Transaction dbTransaction = null;
		try {
			dbTransaction = dbSession.beginTransaction();
			Query query = dbSession
					.createQuery("FROM TagDb WHERE tag LIKE :tagStart");
			query.setParameter("tagStart", tagStart.toLowerCase() + "%");
			tagsFromDb = query.list();
			dbTransaction.commit();
		} catch (HibernateException e) {
			if (dbTransaction != null)
				dbTransaction.rollback();
		} finally {
			dbSession.close();
		}
		String[] tagStringList = convertTagDbToString(tagsFromDb);
		return tagStringList;
	}
	
	private static String[] convertTagDbToString(List<TagDb> tagsFromDb) {
		String[] tagStringList = new String[tagsFromDb.size()];
		for (int i = 0; i < tagStringList.length; i++) {
			tagStringList[i] = tagsFromDb.get(i).getTag();
		}
		return tagStringList;
	}

	public static int[] getPicturesBasedOnTag(String tag, int folderId) {
		List<PictureDb> picturesMatchingCriteria = new ArrayList<>();
		Session dbSession = HibernateUtil.getSessionFactory().openSession();
		Transaction dbTransaction = null;
		try {
			dbTransaction = dbSession.beginTransaction();
			List<PictureDb> pictureDbList = getPictureFolderSubfolderMetaData(
					folderId, dbSession);
			for (PictureDb picture: pictureDbList) {
				for (TagDb t: picture.getTags()) {
					if (t.getTag().equals(tag)) {
						picturesMatchingCriteria.add(picture);
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
		int[] pictureIdArray = idArrayFromPictureArray(picturesMatchingCriteria);
		return pictureIdArray;
	}

	private static int[] idArrayFromPictureArray(List<PictureDb> pictureList) {
		int[] pictureIdArray = new int[pictureList.size()];
		for (int i = 0; i < pictureIdArray.length; i++) {
			pictureIdArray[i] = pictureList.get(i).getId();
		}
		return pictureIdArray;
	}

	public static int[] getPicturesBasedOnManyTags(String[] tag,
			int folderId) {
		List<PictureDb> picturesMatchingCriteria = new ArrayList<>();
		Session dbSession = HibernateUtil.getSessionFactory().openSession();
		Transaction dbTransaction = null;
		try {
			dbTransaction = dbSession.beginTransaction();
			List<PictureDb> pictureDbList = getPictureFolderSubfolderMetaData(
					folderId, dbSession);
			for (PictureDb picture: pictureDbList) {
				for (TagDb t: picture.getTags()) {
					for (String tagString: tag) {
						if (t.getTag().equals(tagString)) {
							if (!picturesMatchingCriteria.contains(picture)) {
								picturesMatchingCriteria.add(picture);
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
		int[] pictureIdArray = idArrayFromPictureArray(picturesMatchingCriteria);
		return pictureIdArray;
	}

	public static int[] getPicturesBasedOnTitle(String title, int folderId) {
		List<PictureDb> picturesMatchingCriteria = new ArrayList<>();
		Session dbSession = HibernateUtil.getSessionFactory().openSession();
		Transaction dbTransaction = null;
		try {
			dbTransaction = dbSession.beginTransaction();
			List<PictureDb> pictureDbList = getPictureFolderSubfolderMetaData(
					folderId, dbSession);
			for (PictureDb picture: pictureDbList) {
				if (picture.getTitle() != null) {
					if (picture.getTitle().equals(title)) {
						picturesMatchingCriteria.add(picture);
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
		int[] pictureIdArray = idArrayFromPictureArray(picturesMatchingCriteria);
		return pictureIdArray;
	}

	public static int[] getPicturesBasedOnDesc(String description, int folderId) {
		List<PictureDb> picturesMatchingCriteria = new ArrayList<>();
		Session dbSession = HibernateUtil.getSessionFactory().openSession();
		Transaction dbTransaction = null;
		try {
			dbTransaction = dbSession.beginTransaction();
			List<PictureDb> pictureDbList = getPictureFolderSubfolderMetaData(
					folderId, dbSession);
			for (PictureDb picture: pictureDbList) {
				if (picture.getDescription() != null) {
					if (picture.getDescription().matches(".*" + description + ".*")) {
						picturesMatchingCriteria.add(picture);
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
		int[] pictureIdArray = idArrayFromPictureArray(picturesMatchingCriteria);
		return pictureIdArray;
	}

	public static int[] getPicturesBasedOnRating(int rating, int folderId) {
		List<PictureDb> picturesMatchingCriteria = new ArrayList<>();
		Session dbSession = HibernateUtil.getSessionFactory().openSession();
		Transaction dbTransaction = null;
		try {
			dbTransaction = dbSession.beginTransaction();
			List<PictureDb> pictureDbList = getPictureFolderSubfolderMetaData(
					folderId, dbSession);
			for (PictureDb picture : pictureDbList) {
				if (picture.getRating() >= rating) {
					picturesMatchingCriteria.add(picture);
				}
			}
			dbTransaction.commit();
		} catch (HibernateException e) {
			if (dbTransaction != null)
				dbTransaction.rollback();
		} finally {
			dbSession.close();
		}
		int[] pictureIdArray = idArrayFromPictureArray(picturesMatchingCriteria);
		return pictureIdArray;
	}

	public static int[] getPicturesBasedOnDate(String[] timeDate, int folderId) {
		List<PictureDb> picturesMatchingCriteria = new ArrayList<>();
		Session dbSession = HibernateUtil.getSessionFactory().openSession();
		Transaction dbTransaction = null;
		try {
			dbTransaction = dbSession.beginTransaction();			
			List<PictureDb> pictureDbList = getPictureFolderSubfolderMetaData(
					folderId, dbSession);
			for (PictureDb picture: pictureDbList) {
				if (picture.getDateTime() != null) {
					if (compareDates(picture.getDateTime(), timeDate[0], timeDate[1])) {
						picturesMatchingCriteria.add(picture);
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
		int[] pictureIdArray = idArrayFromPictureArray(picturesMatchingCriteria);
		return pictureIdArray;
	}
	
	private static boolean compareDates(String pictureDateString, String fromDateString, String toDateString) {
		Date fromDate = null;
		Date toDate = null;
		Date pictureDate = null;
			try {
				String fromOnlyDate = fromDateString.substring(0, 10);
				fromDate = new SimpleDateFormat("yyyy-MM-dd").parse(fromOnlyDate);
				String toOnlyDate = toDateString.substring(0, 10);
				toDate = new SimpleDateFormat("yyyy-MM-dd").parse(toOnlyDate);
				String pictureOnlyDate = pictureDateString.substring(0, 10);
				pictureDate = new SimpleDateFormat("yyyy-MM-dd").parse(pictureOnlyDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return !pictureDate.before(fromDate) && !pictureDate.after(toDate);
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
		List<PictureDb> pictureDbList = null;
		Session dbSession = HibernateUtil.getSessionFactory().openSession();
		Transaction dbTransaction = null;
		try {
			dbTransaction = dbSession.beginTransaction();
			Query query = dbSession
					.createQuery("FROM PictureDb ORDER BY dateTime DESC");
			pictureDbList = query.list();
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
		List<PictureDb> picturesFromDb = null;
		try {
			dbTransaction = dbSession.beginTransaction();
			Query query = dbSession
					.createQuery("FROM PictureDb WHERE parentFolderId=:parentId ORDER BY dateTime DESC");
			query.setParameter("parentId", folderId);
			picturesFromDb = query.list();
			dbTransaction.commit();
		} catch (HibernateException e) {
			if (dbTransaction != null)
				dbTransaction.rollback();
		} finally {
			dbSession.close();
		}
		int[] pictureIdArray = idArrayFromPictureArray(picturesFromDb);
		return pictureIdArray;
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
		ParentFolderDb parentFolderFromDb = null;
		try {
			dbTransaction = dbSession.beginTransaction();
			Query query = dbSession
					.createQuery("FROM ParentFolderDb WHERE folderId = :startFolderId");
			query.setParameter("startFolderId", startFolderId);
			parentFolderFromDb = (ParentFolderDb) query.uniqueResult();
			int parentLeftNumber = parentFolderFromDb.getLft();
			int parentRightNumber = parentFolderFromDb.getRgt();
			query = dbSession
					.createQuery("FROM ParentFolderDb WHERE lft BETWEEN :lftParent AND :rgtParent ORDER BY lft ASC");
			query.setParameter("lftParent", parentLeftNumber);
			query.setParameter("rgtParent", parentRightNumber);
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
		List<ParentFolderDb> foldersFromDb = null;
		ParentFolderDb parentFolderFromDb = null;
		try {
			dbTransaction = dbSession.beginTransaction();
			Query query = dbSession
					.createQuery("FROM ParentFolderDb WHERE folderId = :startFolderId");
			query.setParameter("startFolderId", startFolderId);
			parentFolderFromDb = (ParentFolderDb) query.uniqueResult();
			int parentLeftNumber = parentFolderFromDb.getLft();
			int parentRightNumber = parentFolderFromDb.getRgt();
			query = dbSession
					.createQuery("FROM ParentFolderDb WHERE lft BETWEEN :lftParent AND :rgtParent");
			query.setParameter("lftParent", parentLeftNumber);
			query.setParameter("rgtParent", parentRightNumber);
			foldersFromDb = query.list();
			dbTransaction.commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			if (dbTransaction != null)
				dbTransaction.rollback();
		} finally {
			dbSession.close();
		}
		int[] folderIds = convertFoldersFromDbToIntIdArray(foldersFromDb);
		return folderIds;
	}

	private static int[] convertFoldersFromDbToIntIdArray(List<ParentFolderDb> foldersFromDb) {
		int[] folderIds = new int[foldersFromDb.size()];
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
		List<PictureDb> tmpPictureList = null;
		try {
			dbTransaction = dbSession.beginTransaction();
			for (int i : folderIds) {
				Query query = dbSession
						.createQuery("FROM PictureDb WHERE parentId=:folderId ORDER BY dateTime DESC");
				query.setParameter("folderId", i);
				tmpPictureList = query.list();
				picturesFromDb.addAll(tmpPictureList);
			}
			dbTransaction.commit();
		} catch (HibernateException e) {
			if (dbTransaction != null)
				dbTransaction.rollback();
		} finally {
			dbSession.close();
		}
		int[] pictureIds = idArrayFromPictureArray(picturesFromDb);
		return pictureIds;
	}

	@SuppressWarnings("unchecked")
	private static List<PictureDb> getPictureFolderSubfolderMetaData(
			int startFolderId, Session dbSession) {
		ArrayList<PictureDb> picturesFromDb = new ArrayList<>();
		int[] folderIds = getFolderAndSubFolderId(startFolderId);
		List<PictureDb> tmpPictureList = null;
		try {
			for (int i : folderIds) {
				Query query = dbSession
						.createQuery("FROM PictureDb WHERE parentId=:folderId ORDER BY dateTime DESC");
				query.setParameter("folderId", i);
				tmpPictureList = query.list();
				picturesFromDb.addAll(tmpPictureList);
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
			int parentLeftNumber = st.getLft();
			int parentRightNumber = st.getRgt();
			query = dbSession
					.createQuery("FROM ParentFolderDb WHERE lft BETWEEN :lftParent AND :rgtParent");
			query.setParameter("lftParent", parentLeftNumber);
			query.setParameter("rgtParent", parentRightNumber);
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
		List<ParentFolderDb> foldersFromDb = ReadFromDatabase.getFolderAndSubFolderInfo(1);
		List<TreeMenuNode> treeNodeList = new ArrayList<>();
		for (ParentFolderDb pFolder : foldersFromDb) {
			TreeMenuNode t = new TreeMenuNode(pFolder);
			treeNodeList.add(t);
		}
		for (TreeMenuNode trN : treeNodeList) {
			addChildren(trN, treeNodeList);
		}
		return treeNodeList;
	}

	private static void addChildren(TreeMenuNode root,
			List<TreeMenuNode> nodeList) {
		ArrayList<TreeMenuNode> children = new ArrayList<>();
		for (TreeMenuNode treeNode: nodeList) {
			if (treeNode.getRoot().getParentId() == root.getRoot().getFolderId()) {
				children.add(treeNode);
			}
		}
		root.setChildren(children);
	}

	public static int getFolderId(String folderPath) {
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
		List<PictureDb> tmpPictureList = null;
		try {
			dbTransaction = dbSession.beginTransaction();
			for (int i : folderIds) {
				Query query = dbSession
						.createQuery("FROM PictureDb WHERE parentId=:folderId ORDER BY dateTime DESC");
				query.setParameter("folderId", i);
				tmpPictureList = query.list();
				picturesFromDb.addAll(tmpPictureList);
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

}
