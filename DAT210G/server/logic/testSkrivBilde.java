package logic;


import java.util.ArrayList;
import java.util.List;

import org.hibernate.Transaction;
import org.hibernate.classic.Session;

public class testSkrivBilde {

	public static void main(String[] args) {
		// mange pics
//		PictureDb pic = new PictureDb("testMangePics", "snotter", 3, "2001-04-04 12:12:12", "/skadedyr/rotter.jpg", "med/ska/rotter.jpg", "thumbrotter.jpg");
//		PictureDb pic2 = new PictureDb("testMangePics2", "snotter", 3, "2001-04-04 12:12:12", "/skadedyr/rotter.jpg", "med/ska/rotter.jpg", "thumbrotter.jpg");
//		PictureDb pic3 = new PictureDb("testMangePics3", "snotter", 3, "2001-04-04 12:12:12", "/skadedyr/rotter.jpg", "med/ska/rotter.jpg", "thumbrotter.jpg");
//		ArrayList<PictureDb> aLPic = new ArrayList<>();
//		aLPic.add(pic);
//		aLPic.add(pic2);
//		aLPic.add(pic3);
//		WriteToDatabase.writeManyPics(aLPic);
		
		// legge til tags
//		TagDb tag = new TagDb("he");
//		TagDb tag2 = new TagDb("test flere tags 2");
//		TagDb tag3 = new TagDb("test flere tags 3");
//		ArrayList<TagDb> tagl = new ArrayList<>();
//		tagl.add(tag);
//		tagl.add(tag2);
//		tagl.add(tag3);
//		WriteToDatabase.writeTag(tag);
		
		
		//legge tags til bilde
//		WriteToDatabase.addTagToPic(10, "2013");
		
		
		//legge mange tags til bilde
//		ArrayList<String> tagl = new ArrayList<>();
//		String tag1 = "2013";
//		String tag2 = "tags4like";
//		String tag3 = "Julen 2014";
//		tagl.add(tag1);
//		tagl.add(tag2);
//		tagl.add(tag3);
//		WriteToDatabase.addManyTagsToPic(12, tagl);
		
		//getAllTags
//		List<TagDb> l = ReadFromDatabase.getAllTags();
//		for (TagDb s: l) {
//			System.out.println(s.getTag());
//		}
		
		
		//legg en eller flere tags til flere bilde;
//		Integer pic1 = 7;
//		Integer pic2 = 8;
//		Integer pic3 = 9;
//		ArrayList<Integer> picL = new ArrayList<>();
//		picL.add(pic1);
//		picL.add(pic2);
//		picL.add(pic3);
//		
//		String tag1 = "jul";
//		String tag2 = "2013";
//		String tag3 = "mormor";
//		ArrayList<String> tagL = new ArrayList<>();
//		tagL.add(tag1);
//		tagL.add(tag2);
//		tagL.add(tag3);
//		WriteToDatabase.addManyTagsToManyPics(picL, tagL);
		
		
		
//		//legge bilde eller tags til DB:
//		PictureDb p = new PictureDb("9", "3", 9, "2001-04-04 12:12:12", "test/lol", "moretest", "testmest");
////		PictureDb p2 = new PictureDb("HeisannSveisannpaadeisann", "testbilde21dw", 5, "2001-04-04 12:12:12", "test/lol", "moretest", "testmest");
////		
//////		TagDb t = new TagDb("tags4like");
//		Session session = HibernateUtil.getSessionFactory().openSession();
//		Transaction tx = session.beginTransaction();
//		session.save(p);
////		session.save(p2);
//		tx.commit();
//		session.close();
//		HibernateUtil.shutdown();

		
		//legge tags til bilde:
//		Session session = HibernateUtil.getSessionFactory().openSession();
//		session.beginTransaction();
//		PictureDb pic = (PictureDb) session.load(PictureDb.class, 7);
//		TagDb tag = (TagDb) session.load(TagDb.class, "2013");
//		
//		pic.addTag(tag);
//		session.getTransaction().commit();
		
	}

}
