package logic;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import storing.ImageHandler;
import storing.PictureDb;
import storing.ReadFromDatabase;
import storing.WriteToDatabase;

public class RequestMethods {

	public static void getAllImages(RequestServer request, int imageId, String detail){
		ArrayList<Integer> allImageId = (ArrayList<Integer>) ReadFromDatabase.getAllPicIds();
		int[] tempArray = new int[allImageId.size()];
		for (int i = 0; i < tempArray.length; i ++){
			tempArray[i] = allImageId.get(i);
		}
		request.sendJsonResponse(new ResponseServer(true, tempArray));
	}
	public static void getThumbnail(RequestServer request, int imageId, String detail){
		PictureDb pictureDb = ReadFromDatabase.getPictureBasedOnId(imageId);
		String fileLocation = pictureDb.getThumbnailFileLocation();
		String[] filetypeSplit = fileLocation.split("\\.");
		BufferedImage thumbnailImage = ImageHandler.IMAGE_HANDLER.load(fileLocation);

		request.sendJsonResponse(new ResponseServer(true));
		request.sendImageResponse(thumbnailImage, filetypeSplit[filetypeSplit.length - 1]);
	}
	public static void getLargeImage(RequestServer request, int imageId, String detail){
		PictureDb pictureDb = ReadFromDatabase.getPictureBasedOnId(imageId);
		String fileLocation = pictureDb.getMediumFileLocation();
		String[] filetypeSplit = fileLocation.split("\\.");
		BufferedImage thumbnailImage = ImageHandler.IMAGE_HANDLER.load(fileLocation);

		request.sendJsonResponse(new ResponseServer(true));
		request.sendImageResponse(thumbnailImage, filetypeSplit[filetypeSplit.length - 1]);
	}
	public static void getImagesWithTag(RequestServer request, int imageId, String detail){

	}
	public static void getTagsStartingWith(RequestServer request, int imageId, String detail){

	}
	public static void getNextImageId(RequestServer request, int imageId, String detail){
		int nextAvailableId = ReadFromDatabase.findNextPicId();
		request.sendJsonResponse(new ResponseServer(true, nextAvailableId));
	}
	public static void addTag(RequestServer request, int imageId, String detail){

	}
	public static void modifyTitle(RequestServer request, int imageId, String detail){

	}
	public static void modifyDescription(RequestServer request, int imageId, String detail){

	}
	public static void rotate90Clock(RequestServer request, int imageId, String detail){

	}
	public static void rotate90CounterClock(RequestServer request, int imageId, String detail){

	}
	public static void addNewImage(RequestServer request, int imageId, String detail){
		System.out.println("ImageID til server: " + imageId);
		BufferedImage bufferedImage = request.receiveImage();
		ImageHandler.IMAGE_HANDLER.createServerImage(imageId, detail, bufferedImage);
		boolean wasCreatedSuccesful = ImageHandler.IMAGE_HANDLER.saveAll();

		System.out.println("IMAGE WAS SAVED: "+ wasCreatedSuccesful);
		if (wasCreatedSuccesful){
			String fullPath = ImageHandler.IMAGE_HANDLER.defaultPath + "\\full\\" + imageId + "." + detail;
			String medPath = ImageHandler.IMAGE_HANDLER.defaultPath + "\\medium\\"+ imageId + "." + detail;
			String thumbPath = ImageHandler.IMAGE_HANDLER.defaultPath + "\\thumb\\"+ imageId + "." + detail;
			PictureDb picDb = new PictureDb("test import", "test import", 1, "2014-02-02 10:10:10", fullPath, medPath, thumbPath);
			boolean dbWriteSuccesful = WriteToDatabase.writeOnePic(picDb);
			if (dbWriteSuccesful){
				request.sendJsonResponse(new ResponseServer(true));
				return;
			}
		}
		request.sendJsonResponse(new ResponseServer(false));
	}
	public static void addNewFullImage(RequestServer request, int imageId, String detail){
		System.out.println("ImageID til server: " + imageId);
		try {
			request.receiveFile(detail);
		} catch (Exception e) {
			request.sendJsonResponse(new ResponseServer(false));
			return;
		}
		request.sendJsonResponse(new ResponseServer(true));
	}
}
