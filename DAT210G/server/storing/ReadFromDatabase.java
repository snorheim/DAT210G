package storing;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class ReadFromDatabase {

	@SuppressWarnings("unchecked")
	public static List<TagDb> getAllTags() {
		List<TagDb> tagList = null;
		Session dbSession = HibernateUtil.getSessionFactory().openSession();
		Transaction dbTransaction = null;
		try {
			dbTransaction = dbSession.beginTransaction();
			tagList = dbSession.createQuery("FROM TagDb").list();
			dbTransaction.commit();
		} catch (HibernateException e) {
			if (dbTransaction != null) dbTransaction.rollback();
		} finally {
			dbSession.close();
			HibernateUtil.shutdown();
		}
		return tagList;
	}
	
	@SuppressWarnings("unchecked")
	public static String[] getTagsStartingWith(String tagStart) {
		List<TagDb> tagsFromDb = null;
		Session dbSession = HibernateUtil.getSessionFactory().openSession();
		Transaction dbTransaction = null;
		String[] tagStringList = null;
		try {
			dbTransaction = dbSession.beginTransaction();
			Query query = dbSession.createQuery("FROM TagDb WHERE tag LIKE :tagStart");
			query.setParameter("tagStart", tagStart.toLowerCase() + "%");
			tagsFromDb = query.list();
		} catch (HibernateException e) {
			if (dbTransaction != null) dbTransaction.rollback();
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
			Query query = dbSession.createQuery("SELECT max(id) FROM PictureDb");
			if (!(query.uniqueResult() == null)) {
				nextPicId = (int) query.uniqueResult() + 1;
			}
			dbTransaction.commit();
		} catch (HibernateException e) {
			if (dbTransaction != null) dbTransaction.rollback();
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
			picList = dbSession.createQuery("FROM PictureDb ORDER BY dateTime DESC").list();
			dbTransaction.commit();
		} catch (HibernateException e) {
			if (dbTransaction != null) dbTransaction.rollback();
		} finally {
			dbSession.close();
			HibernateUtil.shutdown();
		}
		return picList;
	}


	@SuppressWarnings("unchecked")
	public static int[] getPicturesBasedOnTag(String tag) {
		List<PictureDb> picList = null;
		Session dbSession = HibernateUtil.getSessionFactory().openSession();
		Transaction dbTransaction = null;
		try {
			dbTransaction = dbSession.beginTransaction();
			Criteria criteria = dbSession.createCriteria(PictureDb.class);
			criteria.createAlias("tags", "tag");
			criteria.add(Restrictions.eq("tag.tag", tag));
			criteria.addOrder(Order.desc("dateTime"));
			picList = criteria.list();
			dbTransaction.commit();
		} catch (HibernateException e) {
			if (dbTransaction != null) dbTransaction.rollback();
		} finally {
			dbSession.close();
			HibernateUtil.shutdown();
		}   
		int[] imageIdArray = new int[picList.size()];
		for (int i = 0; i < imageIdArray.length; i++) {
			imageIdArray[i] = picList.get(i).getId();
		}
		return imageIdArray;
	}


	@SuppressWarnings("unchecked")
	public static List<PictureDb> getPicturesBasedOnManyTags(ArrayList<String> tagList) {
		List<PictureDb> picList = null;
		Session dbSession = HibernateUtil.getSessionFactory().openSession();
		Transaction dbTransaction = null;
		try {
			dbTransaction = dbSession.beginTransaction();		
			Criteria criteria = dbSession.createCriteria(PictureDb.class);
			criteria.createAlias("tags", "tag");
			criteria.add(Restrictions.in("tag.tag", tagList));
			criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			criteria.addOrder(Order.desc("dateTime"));
			picList = criteria.list();
			dbTransaction.commit();
		} catch (HibernateException e) {
			if (dbTransaction != null) dbTransaction.rollback();
		} finally {
			dbSession.close();
			HibernateUtil.shutdown();
		}
		return picList;
	}


	@SuppressWarnings("unchecked")
	public static int[] getPicturesBasedOnRating(int rating) {
		List<PictureDb> picList = null;
		Session dbSession = HibernateUtil.getSessionFactory().openSession();
		Transaction dbTransaction = null;
		int[] pictureIdArray = null;
		try {
			dbTransaction = dbSession.beginTransaction();
			Query query = dbSession.createQuery("FROM PictureDb WHERE rating>=:rat ORDER BY rating DESC");
			query.setParameter("rat", rating);
			picList = query.list();
			dbTransaction.commit();
		} catch (HibernateException e) {
			if (dbTransaction != null) dbTransaction.rollback();
		} finally {
			dbSession.close();
			HibernateUtil.shutdown();
		}
		pictureIdArray = new int[picList.size()];
		for (int i = 0; i < pictureIdArray.length; i++) {
			pictureIdArray[i] = picList.get(i).getId();
		}
		return pictureIdArray;
	}

	public static PictureDb getPictureBasedOnId(int picId) {
		PictureDb picture = null;
		Session dbSession = HibernateUtil.getSessionFactory().openSession();
		Transaction dbTransaction = null;
		try {
			dbTransaction = dbSession.beginTransaction();
			Query query = dbSession.createQuery("FROM PictureDb WHERE id=:picId");
			query.setParameter("picId", picId);
			picture = (PictureDb) query.uniqueResult();
			dbTransaction.commit();
		} catch (HibernateException e) {
			if (dbTransaction != null) dbTransaction.rollback();
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
			Query query = dbSession.createQuery("SELECT id FROM PictureDb ORDER BY dateTime DESC");
			picIdList = query.list();
			dbTransaction.commit();
		} catch (HibernateException e) {
			if (dbTransaction != null) dbTransaction.rollback();
		} finally {
			dbSession.close();
			HibernateUtil.shutdown();
		}
		int[] tempArray = new int[picIdList.size()];
		for (int i = 0; i < tempArray.length; i ++){
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
			if (dbTransaction != null) dbTransaction.rollback();
		} finally {
			dbSession.close();
			HibernateUtil.shutdown();
		}
		String allTagsAsString = "";
		for (TagDb tag: tagList) {
			allTagsAsString += tag.getTag() + ";";
		}
		return allTagsAsString;
	}
	
	@SuppressWarnings("unchecked")
	public static int[] getPicturesBasedOnDate(String timeDate) {
		List<PictureDb> pictureList = null;
		int[] pictureIdArray;
		Session dbSession = HibernateUtil.getSessionFactory().openSession();
		Transaction dbTransaction = null;
		try {
			dbTransaction = dbSession.beginTransaction();
			Criteria criteria = dbSession.createCriteria(PictureDb.class);
			criteria.add(Restrictions.like("dateTime", "%" + timeDate + "%"));
			criteria.addOrder(Order.desc("dateTime"));
			pictureList = criteria.list();
			dbTransaction.commit();
		} catch (HibernateException e) {
			if (dbTransaction != null) dbTransaction.rollback();
		} finally {
			dbSession.close();
			HibernateUtil.shutdown();
		}
		pictureIdArray = new int[pictureList.size()];
		for (int i = 0; i < pictureIdArray.length; i++) {
			pictureIdArray[i] = pictureList.get(i).getId();
		}
		return pictureIdArray;
	}
	
	@SuppressWarnings("unchecked")
	public static int[] getImagesInAFolder(int folderId) {
		int[] imageIdArray;
		Session dbSession = HibernateUtil.getSessionFactory().openSession();
		Transaction dbTransaction = null;
		List<PictureDb> pictureFromDb = null;
		try {
			dbTransaction = dbSession.beginTransaction();
			Query query = dbSession.createQuery("FROM PictureDb WHERE parentFolderId=:parent ORDER BY dateTime DESC");
			query.setParameter("parent", folderId);
			pictureFromDb = query.list();
			dbTransaction.commit();
		} catch (HibernateException e) {
			if (dbTransaction != null) dbTransaction.rollback();
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
			Query query = dbSession.createQuery("FROM ParentFolderDb WHERE folderId=:folderId");
			query.setParameter("folderId", folderId);
			folder = (ParentFolderDb) query.uniqueResult();
			dbTransaction.commit();
		} catch (HibernateException e) {
			if (dbTransaction != null) dbTransaction.rollback();
		} finally {
			dbSession.close();
			HibernateUtil.shutdown();
		}
		return folder;
	}
	
	
	@SuppressWarnings("unchecked")
	public static List<ParentFolderDb> getFolderAndSubFolderInfo(int startFolderId) {
		Session dbSession = HibernateUtil.getSessionFactory().openSession();
		Transaction dbTransaction = null;
		List<ParentFolderDb> foldersFromDb = null;
		ParentFolderDb st = null;
		try {
			dbTransaction = dbSession.beginTransaction();
			Query query = dbSession.createQuery("FROM ParentFolderDb WHERE folderId = :startFolderId");
			query.setParameter("startFolderId", startFolderId);
			st = (ParentFolderDb) query.uniqueResult();
			int leftParent = st.getLft();
			int rightParent = st.getRgt();
			query = dbSession.createQuery("FROM ParentFolderDb WHERE lft BETWEEN :lftParent AND :rgtParent");
			query.setParameter("lftParent", leftParent);
			query.setParameter("rgtParent", rightParent);
			foldersFromDb = query.list();
			dbTransaction.commit();
		} catch (HibernateException e) {
			if (dbTransaction != null) dbTransaction.rollback();
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
			Query query = dbSession.createQuery("FROM ParentFolderDb WHERE folderId = :startFolderId");
			query.setParameter("startFolderId", startFolderId);
			st = (ParentFolderDb) query.uniqueResult();
			int leftParent = st.getLft();
			int rightParent = st.getRgt();
			query = dbSession.createQuery("FROM ParentFolderDb WHERE lft BETWEEN :lftParent AND :rgtParent");
			query.setParameter("lftParent", leftParent);
			query.setParameter("rgtParent", rightParent);
			foldersFromDb = query.list();
			dbTransaction.commit();
		} catch (HibernateException e) {
			if (dbTransaction != null) dbTransaction.rollback();
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
	public static int[] getPicturesInFolderAndSubFolder(int startFolderId) {
		ArrayList<PictureDb> picturesFromDb = new ArrayList<>();
		int[] folderIds = getFolderAndSubFolderId(startFolderId);
		Session dbSession = HibernateUtil.getSessionFactory().openSession();
		Transaction dbTransaction = null;
		List<PictureDb> tmp = null;
		try {
			dbTransaction = dbSession.beginTransaction();
			Query query = null;
			for (int i: folderIds) {
				query = dbSession.createQuery("FROM PictureDb WHERE parentId=:folderId ORDER BY dateTime DESC");
				query.setParameter("folderId", i);
				tmp = query.list();
				picturesFromDb.addAll(tmp);
			}
			dbTransaction.commit();
		} catch (HibernateException e) {
			if (dbTransaction != null) dbTransaction.rollback();
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
	private static List<ParentFolderDb> folderAndSubFolder(int startFolderId) {
		Session dbSession = HibernateUtil.getSessionFactory().openSession();
		Transaction dbTransaction = null;
		List<ParentFolderDb> foldersFromDb = null;
		ParentFolderDb st = null;
		try {
			dbTransaction = dbSession.beginTransaction();
			Query query = dbSession.createQuery("FROM ParentFolderDb WHERE folderId = :startFolderId");
			query.setParameter("startFolderId", startFolderId);
			st = (ParentFolderDb) query.uniqueResult();
			int leftParent = st.getLft();
			int rightParent = st.getRgt();
			query = dbSession.createQuery("FROM ParentFolderDb WHERE lft BETWEEN :lftParent AND :rgtParent");
			query.setParameter("lftParent", leftParent);
			query.setParameter("rgtParent", rightParent);
			foldersFromDb = query.list();
			dbTransaction.commit();
		} catch (HibernateException e) {
			if (dbTransaction != null) dbTransaction.rollback();
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
	
	public static class IsNotOnlyChildObject {
		int leftChildId;
		boolean isOnlyChild;
		
		public IsNotOnlyChildObject(int leftChildId, boolean isNotOnlyChild) {
			this.leftChildId = leftChildId;
			this.isOnlyChild = isNotOnlyChild;
		}
		
		public IsNotOnlyChildObject() {}
		
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
