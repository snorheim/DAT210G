package storing;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import logic.Loggy;

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

	public WriteExif(String imageFileString) {
		imageFile = new File(imageFileString);
		String destFileString = DirectoryMethods.getFilenameWithSuffix(
				imageFileString, "_TEMP");
		destImageFile = new File(destFileString);
		System.out.println("ImageFile: " + imageFileString);
		System.out.println("destImageFile: " + destFileString);
		outPutStream = null;
		canThrow = false;
		metaDataOutPutSet = null;
		try {
			IImageMetadata metadata = Imaging.getMetadata(imageFile);
			JpegImageMetadata jpegMetadata = (JpegImageMetadata) metadata;
			if (jpegMetadata != null) {
				TiffImageMetadata exif = jpegMetadata.getExif();
				if (exif != null) {
					metaDataOutPutSet = exif.getOutputSet();
					exifDirectory = metaDataOutPutSet
							.getOrCreateExifDirectory();
				}
			}
			if (metaDataOutPutSet == null) {
				metaDataOutPutSet = new TiffOutputSet();
				exifDirectory = metaDataOutPutSet.getOrCreateExifDirectory();
			}
		} catch (ImageReadException e) {
			e.printStackTrace();
		} catch (ImageWriteException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Not able to open file: " + imageFileString);
		}
	}

	public void setMetaDataOutPutSet(TiffOutputSet metadaOutputSet) {
		this.metaDataOutPutSet = metadaOutputSet;
	}

	public void setExifTitle(String title) {
		try {
			exifDirectory.removeField(MicrosoftTagConstants.EXIF_TAG_XPTITLE);
			exifDirectory.add(MicrosoftTagConstants.EXIF_TAG_XPTITLE, title);
		} catch (ImageWriteException e) {
			e.printStackTrace();
		}
	}

	public void setExifComment(String comment) {
		try {
			exifDirectory.removeField(MicrosoftTagConstants.EXIF_TAG_XPCOMMENT);
			exifDirectory
					.add(MicrosoftTagConstants.EXIF_TAG_XPCOMMENT, comment);

			exifDirectory.removeField(ExifTagConstants.EXIF_TAG_USER_COMMENT);
			exifDirectory.add(ExifTagConstants.EXIF_TAG_USER_COMMENT, comment);
		} catch (ImageWriteException e) {
			e.printStackTrace();
		}
	}

	public void setExifRating(int rating) {
		try {
			short ratingShort = Short.parseShort(Integer.toString(rating));
			exifDirectory.removeField(MicrosoftTagConstants.EXIF_TAG_RATING);
			exifDirectory.add(MicrosoftTagConstants.EXIF_TAG_RATING,
					ratingShort);
		} catch (ImageWriteException e) {
			e.printStackTrace();
		}
	}

	public void setExifTags(String tags) {
		try {
			exifDirectory
					.removeField(MicrosoftTagConstants.EXIF_TAG_XPKEYWORDS);
			exifDirectory.add(MicrosoftTagConstants.EXIF_TAG_XPKEYWORDS, tags);
		} catch (ImageWriteException e) {
			e.printStackTrace();
		}
	}

	public void setExifAuthor(String author) {
		try {
			exifDirectory.removeField(MicrosoftTagConstants.EXIF_TAG_XPAUTHOR);
			exifDirectory.add(MicrosoftTagConstants.EXIF_TAG_XPAUTHOR, author);
		} catch (ImageWriteException e) {
			e.printStackTrace();
		}
	}

	public void setExifSubject(String subject) {
		try {
			exifDirectory.removeField(MicrosoftTagConstants.EXIF_TAG_XPSUBJECT);
			exifDirectory
					.add(MicrosoftTagConstants.EXIF_TAG_XPSUBJECT, subject);
		} catch (ImageWriteException e) {
			e.printStackTrace();
		}
	}

	public void setExifDateTimeTaken(String dateTime) {
		try {
			Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
					.parse(dateTime);
			dateTime = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss").format(date);
			exifDirectory
					.removeField(ExifTagConstants.EXIF_TAG_DATE_TIME_ORIGINAL);
			exifDirectory.add(ExifTagConstants.EXIF_TAG_DATE_TIME_ORIGINAL,
					dateTime);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void writeToImage() {
		try {
			outPutStream = new FileOutputStream(destImageFile);
			bufferedOutPutStream = new BufferedOutputStream(outPutStream);
			// ImageHandler.getInstance().directoryMonitor.ignore(destImageFile);
			// ImageHandler.getInstance().directoryMonitor.ignore(imageFile);
			new ExifRewriter().updateExifMetadataLossless(destImageFile,
					bufferedOutPutStream, metaDataOutPutSet);
			imageFile.delete();
			destImageFile.renameTo(imageFile);
			canThrow = true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ImageReadException e) {
		} catch (ImageWriteException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				IoUtils.closeQuietly(canThrow, outPutStream);
				destImageFile.delete();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static void log(String message) {
		Loggy.log(message, Loggy.EXIF_WRITE);
	}
}
