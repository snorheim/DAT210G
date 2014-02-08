package storing;

import java.io.File;
import java.io.IOException;
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifIFD0Descriptor;
import com.drew.metadata.exif.ExifIFD0Directory;

public class ReadExif {
	private Metadata metaData;
	private File file;
	private ExifIFD0Directory exifDirectory;
	private ExifIFD0Descriptor exifDescriptor;
	
	public ReadExif (String fileLocation){
		try {
			file = new File(fileLocation);
			metaData = ImageMetadataReader.readMetadata(file);
			exifDirectory = metaData.getDirectory(ExifIFD0Directory.class);
			exifDescriptor = new ExifIFD0Descriptor(exifDirectory);
		} catch (ImageProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public String getExifTitle(){
		return exifDescriptor.getWindowsTitleDescription();
	}
	public String getExifComment(){
		return exifDescriptor.getWindowsCommentDescription();
	}
	public String[] getExifTags(){
		String str = exifDescriptor.getWindowsKeywordsDescription();
		return str.split(";");
	}

	public static void main(String[] args) {
		ReadExif exif = new ReadExif("C:\\dev\\image2.JPG");
		System.out.println(exif.getExifTitle());
		System.out.println(exif.getExifComment());
		
		String[] str = exif.getExifTags();
		for (int i=0;i<str.length;i++){
			System.out.println(str[i]);
		}

	}
}
