package storing;

import java.io.File;
import java.io.IOException;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.common.IImageMetadata;
import org.apache.commons.imaging.formats.jpeg.JpegImageMetadata;
import org.apache.commons.imaging.formats.tiff.TiffImageMetadata;
import org.apache.commons.imaging.formats.tiff.constants.MicrosoftTagConstants;

public class ReadExif {
	private File imageFile;
	private TiffImageMetadata exifMetaData;

	public ReadExif(String imageLocation){
		imageFile = new File(imageLocation);
		try {
			IImageMetadata metadata = Imaging.getMetadata(imageFile);
			JpegImageMetadata jpegMetadata = (JpegImageMetadata) metadata;
			if (jpegMetadata != null) {
				exifMetaData = jpegMetadata.getExif();				
			}
		} catch (ImageReadException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
	public String getExifTitle(){
		String str = null;
		try {
			str = exifMetaData.getFieldValue(MicrosoftTagConstants.EXIF_TAG_XPTITLE);
		} catch (ImageReadException e) {
			e.printStackTrace();
		}
		return str;
	}
	public int getExifRating(){
		short[] shortRating = null;
		try {
			shortRating = exifMetaData.getFieldValue(MicrosoftTagConstants.EXIF_TAG_RATING);
		} catch (ImageReadException e) {
			e.printStackTrace();
		}
		if (shortRating == null){
			return -1;
		}
		return shortRating[0];
	}
	public String getExifComment(){
		String str = null;
		try {
			str = exifMetaData.getFieldValue(MicrosoftTagConstants.EXIF_TAG_XPCOMMENT);
		} catch (ImageReadException e) {
			e.printStackTrace();
		}
		return str;
	}
	public String getExifAuthor(){
		String str = null;
		try {
			str = exifMetaData.getFieldValue(MicrosoftTagConstants.EXIF_TAG_XPAUTHOR);
		} catch (ImageReadException e) {
			e.printStackTrace();
		}
		return str;
	}
	public String getExifSubject(){
		String str = null;
		try {
			str = exifMetaData.getFieldValue(MicrosoftTagConstants.EXIF_TAG_XPSUBJECT);
		} catch (ImageReadException e) {
			e.printStackTrace();
		}
		return str;
	}
	public String[] getExifTags(){
		String str = null;
		try {
			str = exifMetaData.getFieldValue(MicrosoftTagConstants.EXIF_TAG_XPKEYWORDS);
		} catch (ImageReadException e) {
			e.printStackTrace();
		}
		if (str == null){
			return null;
		}
		return str.split(";");
	}

	public static void main(String[] args) {
		ReadExif read = new ReadExif("C:\\dev\\image2.JPG");
		System.out.println("Title: "+read.getExifTitle());
		System.out.println("Comment: "+read.getExifComment());
		System.out.println("Rating: "+read.getExifRating());
		System.out.println("Subject: "+read.getExifSubject());
		System.out.println("Author: "+read.getExifAuthor());

		String[] str = read.getExifTags();
		if (str != null){
			for (int i=0;i<str.length;i++){
				System.out.println("Tag: " + str[i]);
			}
		}

	}
}
