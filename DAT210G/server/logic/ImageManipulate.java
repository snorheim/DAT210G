package logic;

import javaxt.io.Image;
import org.apache.commons.imaging.formats.tiff.write.TiffOutputSet;
import storing.ImageHandler;
import storing.PictureDb;
import storing.WriteExif;

public class ImageManipulate {

	public static void RotateImage(PictureDb picDb, boolean antiClockWise){
		String relativePath = ImageHandler.getInstance().defaultPath.subpath(1, ImageHandler.getInstance().defaultPath.getNameCount()).toAbsolutePath().toString();

		String fileLocation = relativePath + "\\" + picDb.getFileLocation();
		System.out.println("Filplassering:" + fileLocation);
		ReadExif exif = new ReadExif(fileLocation);
		TiffOutputSet completeExif = exif.getMetadataOutputSet();
		Image imageHandlerFull = new Image(fileLocation);
		if (antiClockWise){
			imageHandlerFull.rotate(90);
		}
		else{
			imageHandlerFull.rotate(-90);
		}
		imageHandlerFull.saveAs(fileLocation);
		WriteExif writeExif = new WriteExif(fileLocation);
		writeExif.setMetaDataOutPutSet(completeExif);
		writeExif.writeToImage();

	}

}
