package test;

import java.io.File;

import logic.ReadExif;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import storing.PictureDb;
import storing.WriteToDatabase;

public class AllTests {
	
	@Test
	public void testWritePictureToDb() {
		PictureDb picture = new PictureDb("test", "test", 3, "", "test", "testmed", "testthmb", 1);
		assertEquals("Id should be 0", 0, picture.getId());
		assertTrue(WriteToDatabase.writeOnePic(picture));
		assertThat(picture.getId(), not(0));
	}
	
	@Test
	public void testGetMetaData() {
		File currentDirectory = new File(new File("").getAbsolutePath());
		String pathToTestPicture = currentDirectory.getAbsolutePath()  + "\\Testing\\test\\testbilde.jpg";
		ReadExif testMetaData = new ReadExif(pathToTestPicture);
		assertEquals("Expecting rating to be 4", 4, testMetaData.getExifRating());
	}
	
	
}
