package logic;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import net.coobird.thumbnailator.Thumbnails;
import storing.ImageHandler;
import storing.PictureDb;
import storing.ReadFromDatabase;
import storing.UpdateDatabase;
import storing.WriteExif;
import storing.WriteToDatabase;

public class RequestMethods {

	public static void getAllImages(RequestServer request, int imageId, String detail){
		int[] allImageId =  ReadFromDatabase.getAllPicIds();
		request.sendJsonResponse(new ResponseServer(true, allImageId));
	}
	public static void getThumbnail(RequestServer request, int imageId, String detail){
		PictureDb pictureDb = ReadFromDatabase.getPictureBasedOnId(imageId);
		String fileLocation = pictureDb.getThumbnailFileLocation();
		String[] filetypeSplit = fileLocation.split("\\.");
		BufferedImage thumbnailImage = ImageHandler.getInstance().load(fileLocation);

		request.sendJsonResponse(new ResponseServer(true));
		request.sendImageResponse(thumbnailImage, filetypeSplit[filetypeSplit.length - 1]);
	}
	public static void getThumbnailToAndroid(RequestServer request, int imageId, String detail){
		PictureDb pictureDb = ReadFromDatabase.getPictureBasedOnId(imageId);
		String fileLocation = pictureDb.getThumbnailFileLocation();
		String[] fileNameSplit = fileLocation.split("\\.");
		String[] fileType = {fileNameSplit[fileNameSplit.length - 1]};

		request.sendJsonResponse(new ResponseServer(true, imageId, fileType));
		request.sendImageResponseToAndroid(ImageHandler.getInstance().defaultPath + fileLocation);
	}
	public static void getLargeImage(RequestServer request, int imageId, String detail){
		PictureDb pictureDb = ReadFromDatabase.getPictureBasedOnId(imageId);
		String fileLocation = pictureDb.getMediumFileLocation();
		String[] filetypeSplit = fileLocation.split("\\.");
		BufferedImage thumbnailImage = ImageHandler.getInstance().load(fileLocation);

		request.sendJsonResponse(new ResponseServer(true));
		request.sendImageResponse(thumbnailImage, filetypeSplit[filetypeSplit.length - 1]);
	}
	public static void getLargeImageToAndroid(RequestServer request, int imageId, String detail){
		PictureDb pictureDb = ReadFromDatabase.getPictureBasedOnId(imageId);
		String fileLocation = pictureDb.getMediumFileLocation();
		String[] fileNameSplit = fileLocation.split("\\.");
		String[] fileType = {fileNameSplit[fileNameSplit.length - 1]};

		request.sendJsonResponse(new ResponseServer(true, imageId, fileType));
		request.sendImageResponseToAndroid(ImageHandler.getInstance().defaultPath + fileLocation);
	}
	public static void getFullImage(RequestServer request, int imageId, String detail){
		PictureDb pictureDb = ReadFromDatabase.getPictureBasedOnId(imageId);
		String fileLocation = pictureDb.getFileLocation();
		String[] filetypeSplit = fileLocation.split("\\.");
		BufferedImage fullImage = ImageHandler.getInstance().load(fileLocation);

		request.sendJsonResponse(new ResponseServer(true));
		request.sendImageResponse(fullImage, filetypeSplit[filetypeSplit.length - 1]);
	}
	public static void getFullImageWithDimensions(RequestServer request, int imageId, String detail){
		try {
			PictureDb pictureDb = ReadFromDatabase.getPictureBasedOnId(imageId);
			String fileLocation = pictureDb.getFileLocation();
			String[] filetypeSplit = fileLocation.split("\\.");
			String[] dimensions = detail.split(";");
			int imageHeight = Integer.parseInt(dimensions[0]);
			int imageWidth = Integer.parseInt(dimensions[1]);
			BufferedImage fullImage = ImageHandler.getInstance().load(fileLocation);
			BufferedImage scaledImage = Thumbnails.of(fullImage).size(imageHeight, imageWidth).asBufferedImage();
			request.sendJsonResponse(new ResponseServer(true));
			request.sendImageResponse(scaledImage, filetypeSplit[filetypeSplit.length - 1]);
		} catch (IOException e) {
			request.sendJsonResponse(new ResponseServer(false));
		}
	}
	public static void getMetadata(RequestServer request, int imageId, String detail){
		PictureDb pictureDb = ReadFromDatabase.getPictureBasedOnId(imageId);
		String[] metadataStringArray = new String[5];
		metadataStringArray[0] = pictureDb.getTitle();
		metadataStringArray[1] = pictureDb.getDescription();
		metadataStringArray[2] = Integer.toString(pictureDb.getRating());
		metadataStringArray[3] = pictureDb.getDateTime();
		metadataStringArray[4] = pictureDb.getTagString();
		request.sendJsonResponse(new ResponseServer(true, metadataStringArray));
	}
	public static void getImagesWithTag(RequestServer request, int imageId, String detail){
		int[] imageIdArray = ReadFromDatabase.getPicturesBasedOnTag(detail);
		request.sendJsonResponse(new ResponseServer(true, imageIdArray));
	}
	public static void getImagesWithMinRating(RequestServer request, int imageId, String detail){
		int[] imageIdArray = ReadFromDatabase.getPicturesBasedOnRating(Integer.parseInt(detail));
		request.sendJsonResponse(new ResponseServer(true, imageIdArray));
	}
	public static void getImagesWithDateTime(RequestServer request, int imageId, String detail){
		int[] imageIdArray = ReadFromDatabase.getPicturesBasedOnDate(detail);
		request.sendJsonResponse(new ResponseServer(true, imageIdArray));
	}	
	public static void getTagsStartingWith(RequestServer request, int imageId, String detail){

	}
	public static void getNextImageId(RequestServer request, int imageId, String detail){
		int nextAvailableId = ReadFromDatabase.findNextPicId();
		System.out.println("Neste id er: " + nextAvailableId);
		request.sendJsonResponse(new ResponseServer(true, nextAvailableId));
	}
	public static void addTag(RequestServer request, int imageId, String detail){
		PictureDb pictureDb = ReadFromDatabase.getPictureBasedOnId(imageId);
		String fileLocation = pictureDb.getFileLocation();
		String existingTags = pictureDb.getTagString();
		WriteExif writeExifInfo = new WriteExif(ImageHandler.getInstance().defaultPath + fileLocation);
		writeExifInfo.setExifTags(existingTags + detail + ";");
		writeExifInfo.writeToImage();
		WriteToDatabase.addTagToPic(imageId, detail);
		request.sendJsonResponse(new ResponseServer(true));
	}
	public static void modifyTitle(RequestServer request, int imageId, String detail){
		PictureDb pictureDb = ReadFromDatabase.getPictureBasedOnId(imageId);
		String fileLocation = pictureDb.getFileLocation();
		boolean setTitleinDb = UpdateDatabase.updatePictureTitle(imageId, detail);
		if (setTitleinDb){
			WriteExif writeExifInfo = new WriteExif(ImageHandler.getInstance().defaultPath + fileLocation);
			writeExifInfo.setExifTitle(detail);
			writeExifInfo.writeToImage();
			request.sendJsonResponse(new ResponseServer(true));
		}
		else{
			request.sendJsonResponse(new ResponseServer(false));	
		}
	}
	public static void modifyDescription(RequestServer request, int imageId, String detail){
		PictureDb pictureDb = ReadFromDatabase.getPictureBasedOnId(imageId);
		String fileLocation = pictureDb.getFileLocation();
		boolean setDescInDb = UpdateDatabase.updatePictureDescription(imageId, detail);
		if (setDescInDb){
			WriteExif writeExifInfo = new WriteExif(ImageHandler.getInstance().defaultPath + fileLocation);
			writeExifInfo.setExifComment(detail);
			writeExifInfo.writeToImage();
			request.sendJsonResponse(new ResponseServer(true));
		}
		else{
			request.sendJsonResponse(new ResponseServer(false));	
		}
	}
	public static void modifyRating(RequestServer request, int imageId, String detail){
		PictureDb pictureDb = ReadFromDatabase.getPictureBasedOnId(imageId);
		String fileLocation = pictureDb.getFileLocation();
		boolean setDescInDb = UpdateDatabase.updatePictureRating(imageId, Integer.parseInt(detail));
		if (setDescInDb){
			WriteExif writeExifInfo = new WriteExif(ImageHandler.getInstance().defaultPath + fileLocation);
			writeExifInfo.setExifRating(Integer.parseInt(detail));
			writeExifInfo.writeToImage();
			request.sendJsonResponse(new ResponseServer(true));
		}
		else{
			request.sendJsonResponse(new ResponseServer(false));	
		}
	}
	public static void rotate90Clock(RequestServer request, int imageId, String detail){

	}
	public static void rotate90CounterClock(RequestServer request, int imageId, String detail){

	}
	public static void addNewImage(RequestServer request, int imageId, String detail){
		//		System.out.println("ImageID til server: " + imageId);
		//		BufferedImage bufferedImage = request.receiveImage();
		//		//ImageHandler.IMAGE_HANDLER.createServerImage(imageId, detail, bufferedImage);
		//		boolean wasCreatedSuccesful = ImageHandler.getInstance().saveAndDispose(id, file);
		//
		//		System.out.println("IMAGE WAS SAVED: "+ wasCreatedSuccesful);
		//		if (wasCreatedSuccesful){
		//			String fullPath = ImageHandler.IMAGE_HANDLER.defaultPath + "\\full\\" + imageId + "." + detail;
		//			String medPath = ImageHandler.IMAGE_HANDLER.defaultPath + "\\medium\\"+ imageId + "." + detail;
		//			String thumbPath = ImageHandler.IMAGE_HANDLER.defaultPath + "\\thumb\\"+ imageId + "." + detail;
		//			PictureDb picDb = new PictureDb("test import", "test import", 1, "2014-02-02 10:10:10", fullPath, medPath, thumbPath);
		//			boolean dbWriteSuccesful = WriteToDatabase.writeOnePic(picDb);
		//			if (dbWriteSuccesful){
		//				request.sendJsonResponse(new ResponseServer(true));
		//				return;
		//			}
		//		}
		//		request.sendJsonResponse(new ResponseServer(false));
	}
	public static void addNewFullImage(RequestServer request, int imageId, String detail){
		System.out.println("ImageID til server: " + imageId);
		File tempImage = null;
		try {
			tempImage = request.receiveFile(detail);
		} catch (Exception e) {
			request.sendJsonResponse(new ResponseServer(false));
			return;
		}
		ReadExif exif = new ReadExif(tempImage.getPath());
		boolean writePictureToFile = ImageHandler.getInstance().saveAndDispose(imageId, tempImage);
		if (writePictureToFile){
			PictureDb pictureDb = new PictureDb(exif.getExifTitle(), exif.getExifComment(), 
					exif.getExifRating(), exif.getExifDateTimeTaken(), 
					"\\full\\"+imageId+"."+detail, 
					"\\medium\\"+imageId+"."+detail, 
					"\\thumb\\"+imageId+"."+detail);
			boolean writePictureToDb = WriteToDatabase.writeOnePic(pictureDb);
			if (writePictureToDb){
				String[] tagsInString = exif.getExifTags().split(";");
				for (int i = 0; i < tagsInString.length; i++) {
					WriteToDatabase.addTagToPic(imageId, tagsInString[i]);
				}
				request.sendJsonResponse(new ResponseServer(true));
			}
		}
	}
}
