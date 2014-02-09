package storing;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.ImageWriteException;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.common.IImageMetadata;
import org.apache.commons.imaging.formats.jpeg.JpegImageMetadata;
import org.apache.commons.imaging.formats.jpeg.exif.ExifRewriter;
import org.apache.commons.imaging.formats.tiff.TiffImageMetadata;
import org.apache.commons.imaging.formats.tiff.constants.ExifTagConstants;
import org.apache.commons.imaging.formats.tiff.constants.MicrosoftTagConstants;
import org.apache.commons.imaging.formats.tiff.write.TiffOutputDirectory;
import org.apache.commons.imaging.formats.tiff.write.TiffOutputSet;
import org.apache.commons.imaging.util.IoUtils;

public class WriteExif {
	private File imageFile;
	private File destImageFile;
	private OutputStream outPutStream;
	private BufferedOutputStream bufferedOutPutStream;
	private TiffOutputSet metaDataOutPutSet;
	private TiffOutputDirectory exifDirectory;
	private boolean canThrow;

	public WriteExif(String imageFileString){	
		imageFile = new File(imageFileString);
		String[] destFileStringSplit = imageFileString.split("\\.");
		String destFileString = destFileStringSplit[0] + "METADATATEMP." + destFileStringSplit[1];
		destImageFile = new File(destFileString);
		outPutStream = null;
		canThrow = false;
		metaDataOutPutSet = null;
		try {
			IImageMetadata metadata = Imaging.getMetadata(imageFile);
			JpegImageMetadata jpegMetadata = (JpegImageMetadata) metadata;
			if (null != jpegMetadata) {
				TiffImageMetadata exif = jpegMetadata.getExif();				
				if (null != exif) {
					metaDataOutPutSet = exif.getOutputSet();
					exifDirectory = metaDataOutPutSet.getOrCreateExifDirectory();
				}
			}
			if (null == metaDataOutPutSet) {
				metaDataOutPutSet = new TiffOutputSet();
				exifDirectory = metaDataOutPutSet.getOrCreateExifDirectory();
			}
		} catch (ImageReadException e) {
			e.printStackTrace();
		} catch (ImageWriteException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void setExifTitle(String title){
		try {
			exifDirectory.removeField(MicrosoftTagConstants.EXIF_TAG_XPTITLE);
			exifDirectory.add(MicrosoftTagConstants.EXIF_TAG_XPTITLE, title);
		} catch (ImageWriteException e) {
			e.printStackTrace();
		}
	}
	public void setExifComment(String comment){
		try {
			exifDirectory.removeField(MicrosoftTagConstants.EXIF_TAG_XPCOMMENT);
			exifDirectory.add(MicrosoftTagConstants.EXIF_TAG_XPCOMMENT, comment);
			
			exifDirectory.removeField(ExifTagConstants.EXIF_TAG_USER_COMMENT);
			exifDirectory.add(ExifTagConstants.EXIF_TAG_USER_COMMENT, comment);
		} catch (ImageWriteException e) {
			e.printStackTrace();
		}
	}
	public void setExifRating(short rating){
		try {
			exifDirectory.removeField(MicrosoftTagConstants.EXIF_TAG_RATING);
			exifDirectory.add(MicrosoftTagConstants.EXIF_TAG_RATING, rating);
		} catch (ImageWriteException e) {
			e.printStackTrace();
		}
	}
	public void setExifTags(String tags){
		try {
			exifDirectory.removeField(MicrosoftTagConstants.EXIF_TAG_XPKEYWORDS);
			exifDirectory.add(MicrosoftTagConstants.EXIF_TAG_XPKEYWORDS, tags);
		} catch (ImageWriteException e) {
			e.printStackTrace();
		}
	}
	public void setExifAuthor(String author){
		try {
			exifDirectory.removeField(MicrosoftTagConstants.EXIF_TAG_XPAUTHOR);
			exifDirectory.add(MicrosoftTagConstants.EXIF_TAG_XPAUTHOR, author);
		} catch (ImageWriteException e) {
			e.printStackTrace();
		}
	}
	public void setExifSubject(String subject){
		try {
			exifDirectory.removeField(MicrosoftTagConstants.EXIF_TAG_XPSUBJECT);
			exifDirectory.add(MicrosoftTagConstants.EXIF_TAG_XPSUBJECT, subject);
		} catch (ImageWriteException e) {
			e.printStackTrace();
		}
	}
	public void writeToImage(){
		try {
			outPutStream = new FileOutputStream(destImageFile);
			bufferedOutPutStream = new BufferedOutputStream(outPutStream);
			new ExifRewriter().updateExifMetadataLossless(imageFile, bufferedOutPutStream, metaDataOutPutSet);
			imageFile.delete();
			destImageFile.renameTo(imageFile);
			canThrow = true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ImageReadException e) {
			e.printStackTrace();
		} catch (ImageWriteException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				IoUtils.closeQuietly(canThrow, outPutStream);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {

		WriteExif exif = new WriteExif("C:\\dev\\image2.JPG");
		exif.setExifTitle("Ny tittel");
		exif.setExifComment("Ny kommentar!");
		exif.setExifTags("USA;Norge;julen2010");
		exif.setExifAuthor("Ny author!");
		exif.setExifSubject("Nytt subject!!");
		short shrt = 2;
		exif.setExifRating(shrt);
		exif.writeToImage();

	}
}
