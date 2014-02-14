package storing;


import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Transaction;
import org.hibernate.classic.Session;

public class testSkrivBilde {

	public static void main(String[] args) {
		// mange pics
//		PictureDb pic = new PictureDb("daasdsc","snotter", 3, "2001-04-04 12:12:12", "/skadedyr/rotter.jpg", "med/ska/rotter.jpg", "thumbrotter.jpg");
//		PictureDb pic2 = new PictureDb("testMangePics2", "snotter", 3, "2001-04-04 12:12:12", "/skadedyr/rotter.jpg", "med/ska/rotter.jpg", "thumbrotter.jpg");
//		PictureDb pic3 = new PictureDb("testMangePics3", "snotter", 3, "2001-04-04 12:12:12", "/skadedyr/rotter.jpg", "med/ska/rotter.jpg", "thumbrotter.jpg");
//		ArrayList<PictureDb> aLPic = new ArrayList<>();
//		aLPic.add(pic);
//		aLPic.add(pic2);
//		aLPic.add(pic3);
//		System.out.println(WriteToDatabase.writeManyPics(aLPic));
		
		// legge til tags
//		TagDb tag = new TagDb("sakdj");
//		TagDb tag2 = new TagDb("flls");
//		TagDb tag3 = new TagDb("ldsl");
//		ArrayList<TagDb> tagl = new ArrayList<>();
//		tagl.add(tag);
//		tagl.add(tag2);
//		tagl.add(tag3);
//		System.out.println(WriteToDatabase.writeManyTags(tagl));
		
		
		//legge tags til bilde
//		System.out.println(WriteToDatabase.addTagToPic(3, "he"));
		
		
		//legge mange tags til bilde
//		ArrayList<String> tagl = new ArrayList<>();
//		String tag1 = "tags4like";
//		String tag2 = "Julen 2014";
////		String tag3 = "Julen 2014";
//		tagl.add(tag1);
//		tagl.add(tag2);
////		tagl.add(tag3);
//		System.out.println(WriteToDatabase.addManyTagsToPic(15, tagl));
		
		//getAllTags
//		List<TagDb> l = ReadFromDatabase.getAllTags();
//		for (TagDb s: l) {
//			System.out.println(s.getTag());
//		}
		
		
		//legg en eller flere tags til flere bilde;
//		Integer pic1 = 4;
//		Integer pic2 = 11;
//		Integer pic3 = 5;
//		ArrayList<Integer> picL = new ArrayList<>();
//		picL.add(pic1);
//		picL.add(pic2);
//		picL.add(pic3);
//		
//		String tag1 = "Julen 2014";
//		String tag2 = "Sommer 2013";
//		String tag3 = "flls";
//		ArrayList<String> tagL = new ArrayList<>();
//		tagL.add(tag1);
//		tagL.add(tag2);
//		tagL.add(tag3);
//		System.out.println(WriteToDatabase.addManyTagsToManyPics(picL, tagL));
		
//		ArrayList<String> l = new ArrayList<>();
//		l.add("flls");
//		l.add("jul");
//		List<PictureDb> pl = ReadFromDatabase.getPicturesBasedOnManyTags(l);
//		for (PictureDb p: pl) {
//			System.out.println(p.getId() + ", " + p.getTitle() + ",   " + p.getFileLocation());
//		}
		
//		List<PictureDb> p = ReadFromDatabase.getPicturesBasedOnRating(5);
//		for (PictureDb t: p) {
//			System.out.println(t.getId() + ", " + t.getTitle() + ", " + t.getRating());
//		}
		
//		PictureDb pic = ReadFromDatabase.getPictureBasedOnId(17);
//		System.out.println(pic.getTitle() + ", file loc: " + pic.getFileLocation());
		
//		List<Integer> picIdList = ReadFromDatabase.getAllPicIds();
//		for (Integer p: picIdList) {
//			System.out.println(p);
//		}
		
		
//		List<TagDb> tag = ReadFromDatabase.getAllTagsForAPicture(1);
//		for (TagDb t: tag) {
//			System.out.println(t.getTag());
//		}
		
//		PictureDb pic = ReadFromDatabase.getPictureBasedOnId(1);
//		Desktop dk = Desktop.getDesktop();
//		try {
//			dk.open(new File(pic.getFileLocation()));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		PictureDb p = new PictureDb("test", "test", 3, "2014-02-02 10:10:10", "", "", "\\thumb\\3.png");
//		WriteToDatabase.writeOnePic(p);
//		PictureDb p = ReadFromDatabase.getPictureBasedOnId(1);
//		System.out.println(p.getThumbnailFileLocation());
	}

}
