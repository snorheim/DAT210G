package storing;

import java.util.ArrayList;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class WriteToDatabase {
	private static boolean successfulTransfer;
	
	public static boolean writeOnePic(PictureDb pic){
		Session dbSession = HibernateUtil.getSessionFactory().openSession();
		Transaction dbTransaction = null;
		try {
			dbTransaction = dbSession.beginTransaction();
			dbSession.save(pic);
			dbTransaction.commit();
			successfulTransfer = true;
		} catch (HibernateException e) {
			successfulTransfer = false;
			if (dbTransaction != null) dbTransaction.rollback();
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
			for (PictureDb pic: picList) {
				dbSession.save(pic);
			}
			dbTransaction.commit();
			successfulTransfer = true;
		} catch (HibernateException e) {
			successfulTransfer = false;
			if (dbTransaction != null) dbTransaction.rollback();
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
			if (dbTransaction != null) dbTransaction.rollback();
		} finally {
			dbSession.close();
			HibernateUtil.shutdown();
		}
		return successfulTransfer;
	}
	
	public static boolean writeManyTags(ArrayList<TagDb> tagList) {
		Session dbSession = HibernateUtil.getSessionFactory().openSession();
		Transaction dbTransaction = null;
		try {
			dbTransaction = dbSession.beginTransaction();
			for (TagDb tag: tagList) {
				dbSession.save(tag);
			}
			dbTransaction.commit();
			successfulTransfer = true;
		} catch (HibernateException e) {
			successfulTransfer = false;
			if (dbTransaction != null) dbTransaction.rollback();
		} finally {
			dbSession.close();
			HibernateUtil.shutdown();
		}
		return successfulTransfer;
	}
	
	//hvis picture allerede har taggen ikke gjor noe. hvis tag ikke finnes: lag den.
	//legg tag til bilde
	public static boolean addTagToPic(int picId, String tag) {
		Session dbSession = HibernateUtil.getSessionFactory().openSession();
		Transaction dbTransaction = null;
		try {
			dbTransaction = dbSession.beginTransaction();
			PictureDb picFromDb = (PictureDb) dbSession.load(PictureDb.class, picId);
			TagDb tagFromDb = (TagDb) dbSession.load(TagDb.class, tag);
			picFromDb.addTag(tagFromDb);
			dbTransaction.commit();
			successfulTransfer = true;
		} catch (HibernateException e) {
			successfulTransfer = false;
			if (dbTransaction != null) dbTransaction.rollback();
		} finally {
			dbSession.close();
			HibernateUtil.shutdown();
		}
		return successfulTransfer;
	}
	
	//sjekk hvilke tags bilde har: legg matchende tags til en ny liste, legg saa inn de nye tagsene her
	//tags fra database som ikke matchet noe i taglisten fjernes.
	//sjekk om tags finnes, hvis ikke: legg til db, og saa connect dem til bildet
	//
	//for metadata til fil: alt overskrives?
	//vil gjore det samme her, slette alle tags som bildet har, legg til det som er nytt?
	public static boolean addManyTagsToPic(int picId, ArrayList<String> tagList) {
		Session dbSession = HibernateUtil.getSessionFactory().openSession();
		Transaction dbTransaction = null;
		try {
			dbTransaction = dbSession.beginTransaction();
			PictureDb picFromDb = (PictureDb) dbSession.load(PictureDb.class, picId);
			for (String tag: tagList)  {
				TagDb tagFromDb = (TagDb) dbSession.load(TagDb.class, tag);
				picFromDb.addTag(tagFromDb);
			}
			dbTransaction.commit();
			successfulTransfer = true;
		} catch (HibernateException e) {
			successfulTransfer = false;
			if (dbTransaction != null) dbTransaction.rollback();
		} finally {
			dbSession.close();
			HibernateUtil.shutdown();
		}
		return successfulTransfer;
	}
	
	//on hold pga trenger gjerne ikke denne: trenger sjekking av eksisterende tags osv.
//	public static boolean addManyTagsToManyPics(ArrayList<Integer> picIdList, ArrayList<String> tagList) {
//		Session dbSession = HibernateUtil.getSessionFactory().openSession();
//		Transaction dbTransaction = null;
//		try {
//			dbTransaction = dbSession.beginTransaction();
//			for (Integer picId: picIdList) {
//				PictureDb picFromDb = (PictureDb) dbSession.load(PictureDb.class, picId);
//				for (String tag: tagList) {
//					TagDb tagFromDb = (TagDb) dbSession.load(TagDb.class, tag);
//					picFromDb.addTag(tagFromDb);
//				}
//			}
//			dbTransaction.commit();
//			successfulTransfer = true;
//		} catch (HibernateException e) {
//			successfulTransfer = false;
//			if (dbTransaction != null) dbTransaction.rollback();
//		} finally {
//			dbSession.close();
//			HibernateUtil.shutdown();
//		}
//		return successfulTransfer;
//	}
	
}
