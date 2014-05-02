package logic;

import java.io.File;

import javaxt.io.Image;

import org.apache.commons.imaging.formats.tiff.write.TiffOutputSet;

import storing.ImageHandler;
import storing.PictureDb;
import storing.WriteExif;

public class ImageManipulate {

	public static void rotateImage(PictureDb picDb, boolean counterClockWise) {
		String relativePath = ImageHandler.getRelativePathString();

		String fileLocation = relativePath + picDb.getFileLocation();
		String mediumFile = relativePath + picDb.getMediumFileLocation();
		String thumbFile = relativePath + picDb.getThumbnailFileLocation();

		ReadExif exif = new ReadExif(fileLocation);
		TiffOutputSet completeExif = exif.getMetadataOutputSet();

		Image imageHandlerFull = new Image(fileLocation);

		ImageHandler.hideImageFile(new File(mediumFile), false);
		ImageHandler.hideImageFile(new File(thumbFile), false);

		Image imageHandlerMedium = new Image(mediumFile);
		Image imageHandlerThumb = new Image(thumbFile);
		if (counterClockWise) {
			imageHandlerFull.rotate(-90);

			imageHandlerMedium.rotate(-90);

			imageHandlerThumb.rotate(-90);

		} else {
			imageHandlerFull.rotate(90);
			imageHandlerMedium.rotate(90);
			imageHandlerThumb.rotate(90);
		}

		imageHandlerFull.saveAs(fileLocation);
		imageHandlerMedium.saveAs(mediumFile);
		imageHandlerThumb.saveAs(thumbFile);

		ImageHandler.hideImageFile(new File(mediumFile), true);
		ImageHandler.hideImageFile(new File(thumbFile), true);

		WriteExif writeExif = new WriteExif(fileLocation);
		writeExif.setMetaDataOutPutSet(completeExif);
		writeExif.writeToImage();

	}

}
