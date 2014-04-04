package logic;

import javaxt.io.Image;
import org.apache.commons.imaging.formats.tiff.write.TiffOutputSet;
import storing.ImageHandler;
import storing.PictureDb;
import storing.WriteExif;

public class ImageManipulate {

	public static void rotateImage(PictureDb picDb, boolean antiClockWise){
		String relativePath = ImageHandler.getInstance().defaultPath.subpath(1, ImageHandler.getInstance().defaultPath.getNameCount()).toAbsolutePath().toString();

		String fileLocation = relativePath + "\\" + picDb.getFileLocation();
		String fileLocationMedium = relativePath + "\\" + picDb.getMediumFileLocation();
		String fileLocationThumb = relativePath + "\\" + picDb.getThumbnailFileLocation();

		ReadExif exif = new ReadExif(fileLocation);
		TiffOutputSet completeExif = exif.getMetadataOutputSet();

		Image imageHandlerFull = new Image(fileLocation);
		Image imageHandlerMedium = new Image(fileLocationMedium);
		Image imageHandlerThumb = new Image(fileLocationThumb);
		if (antiClockWise){
			imageHandlerFull.rotate(90);
			imageHandlerMedium.rotate(90);
			imageHandlerThumb.rotate(90);
		}
		else{
			imageHandlerFull.rotate(-90);
			imageHandlerMedium.rotate(-90);
			imageHandlerThumb.rotate(-90);
		}
		imageHandlerFull.saveAs(fileLocation);
		imageHandlerMedium.saveAs(fileLocationMedium);
		imageHandlerThumb.saveAs(fileLocationThumb);
		WriteExif writeExif = new WriteExif(fileLocation);
		writeExif.setMetaDataOutPutSet(completeExif);
		writeExif.writeToImage();

	}

}
