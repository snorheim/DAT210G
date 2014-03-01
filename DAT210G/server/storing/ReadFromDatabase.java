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
			picList = dbSession.createQuery("FROM PictureDb").list();
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
			criteria.addOrder(Order.asc("id"));
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
			Query query = dbSession.createQuery("FROM PictureDb WHERE rating>=:rat");
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
			Query query = dbSession.createQuery("SELECT id FROM PictureDb");
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
			Query query = dbSession.createQuery("FROM PictureDb WHERE parentFolderId=:parent");
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
	
	@SuppressWarnings("unchecked")
	public static int[] getFoldersInAFolder(int folderId) {
		int[] folderIdArray;
		Session dbSession = HibernateUtil.getSessionFactory().openSession();
		Transaction dbTransaction = null;
		List<ParentFolderDb> parentsIdList = null;
		try {
			dbTransaction = dbSession.beginTransaction();
			Query query = dbSession.createQuery("FROM ParentFolderDb WHERE parentId=:folderId");
			query.setParameter("folderId", folderId);
			parentsIdList = query.list();
			dbTransaction.commit();
		} catch (HibernateException e) {
			if (dbTransaction != null) dbTransaction.rollback();
		} finally {
			dbSession.close();
			HibernateUtil.shutdown();
		}
		folderIdArray = new int[parentsIdList.size()];
		for (int i = 0; i < folderIdArray.length; i++) {
			folderIdArray[i] = parentsIdList.get(i).getFolderId();
		}
		return folderIdArray;
	}
	
	public static ParentFolderDb getParentInfo(int pictureId) {
		Session dbSession = HibernateUtil.getSessionFactory().openSession();
		Transaction dbTransaction = null;
		ParentFolderDb parentFolderInfo = null;
		try {
			dbTransaction = dbSession.beginTransaction();
			Query query = dbSession.createQuery("FROM PictureDb WHERE id=:pictureId");
			query.setParameter("pictureId", pictureId);
			PictureDb pictureFromDb = (PictureDb) query.uniqueResult();
			query = dbSession.createQuery("FROM ParentFolderDb WHERE folderId=:parentId");
			query.setParameter("parentId", pictureFromDb.getParentFolderId());
			parentFolderInfo = (ParentFolderDb) query.uniqueResult();
			dbTransaction.commit();
		} catch (HibernateException e) {
			if (dbTransaction != null) dbTransaction.rollback();
		} finally {
			dbSession.close();
			HibernateUtil.shutdown();
		}
		return parentFolderInfo;
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
	
}
