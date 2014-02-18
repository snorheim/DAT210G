package storing;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.common.IImageMetadata;
import org.apache.commons.imaging.formats.jpeg.JpegImageMetadata;
import org.apache.commons.imaging.formats.tiff.TiffImageMetadata;
import org.apache.commons.imaging.formats.tiff.constants.ExifTagConstants;
import org.apache.commons.imaging.formats.tiff.constants.MicrosoftTagConstants;
import org.apache.commons.imaging.formats.tiff.write.TiffOutputSet;

public class ReadExif {
	private File imageFile;
	private TiffImageMetadata exifMetaData;
	private TiffOutputSet metaDataOutPutSet;

	public ReadExif(String imageLocation){
		imageFile = new File(imageLocation);
		try {
			IImageMetadata metadata = Imaging.getMetadata(imageFile);
			JpegImageMetadata jpegMetadata = (JpegImageMetadata) metadata;
			if (jpegMetadata != null) {
				exifMetaData = jpegMetadata.getExif();
				metaDataOutPutSet = exifMetaData.getOutputSet();
			}
			if (metaDataOutPutSet == null) {
				metaDataOutPutSet = new TiffOutputSet();
			}
		} catch (Exception e) {
			System.err.println("Not able to open file: " + imageLocation);
			return;
		}
	}
	public TiffOutputSet getMetadataOutputSet(){
		return metaDataOutPutSet;
	}
	public String getExifTitle(){
		String str = null;
		try {
			str = exifMetaData.getFieldValue(MicrosoftTagConstants.EXIF_TAG_XPTITLE);
		} catch (Exception e) {
			return null;
		}
		return str;
	}
	public int getExifRating(){
		short[] shortRating = null;
		try {
			shortRating = exifMetaData.getFieldValue(MicrosoftTagConstants.EXIF_TAG_RATING);
		} catch (Exception e) {
			return -1;
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
		} catch (Exception e) {
			return null;
		}
		return str;
	}
	public String getExifAuthor(){
		String str = null;
		try {
			str = exifMetaData.getFieldValue(MicrosoftTagConstants.EXIF_TAG_XPAUTHOR);
		} catch (Exception e) {
			return null;
		}
		return str;
	}
	public String getExifSubject(){
		String str = null;
		try {
			str = exifMetaData.getFieldValue(MicrosoftTagConstants.EXIF_TAG_XPSUBJECT);
		} catch (Exception e) {
			return null;
		}
		return str;
	}
	public String getExifTags(){
		String str = null;
		try {
			str = exifMetaData.getFieldValue(MicrosoftTagConstants.EXIF_TAG_XPKEYWORDS);
		} catch (Exception e) {
			return null;
		}
		return str;
	}
	public String getExifDateTimeTaken(){
		String[] str = null;
		try {
			str = exifMetaData.getFieldValue(ExifTagConstants.EXIF_TAG_DATE_TIME_ORIGINAL);
		} catch (Exception e) {
			return null;
		}
		if (str == null){
			return null;
		}
		try {
			Date date = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss").parse(str[0]);
			str[0] = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
		} catch (ParseException e) {
			return null;
		}
		return str[0];
	}
}
