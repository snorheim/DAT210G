package logic;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import net.coobird.thumbnailator.Thumbnails;
import storing.ImageHandler;
import storing.PictureDb;
import storing.ReadFromDatabase;
import storing.TreeMenuNode;
import storing.UpdateDatabase;
import storing.WriteExif;
import storing.WriteToDatabase;

public class RequestMethods {

	public static void getAllImages(RequestServer request, int id, String detail){
		int[] allid =  ReadFromDatabase.getAllPicIds();
		request.sendJsonResponse(new ResponseServer(true, allid));
	}
	public static void getSubFolders(RequestServer request, int id, String detail){
		List<TreeMenuNode> folders = ReadFromDatabase.getTreeForMenu();
		int[] subFolderId = null;
		String[] subFolderName = null;
		for (int i = 0; i < folders.size(); i++) {
			if (folders.get(i).getRoot().getFolderId() == id) {
				System.out.println("funnet rett folder: " + id);
				subFolderId = new int[folders.get(i).getChildren().size()];
				subFolderName = new String[folders.get(i).getChildren().size()];
				for (int j = 0; j < folders.get(i).getChildren().size(); j++) {
					System.out.println("subfolder nr: " + folders.get(i).getChildren().get(j));
					subFolderId[j] = folders.get(i).getChildren().get(j).getRoot().getFolderId();
					subFolderName[j] = folders.get(i).getChildren().get(j).getRoot().getName();
				}
			}
		}
		request.sendJsonResponse(new ResponseServer(true, subFolderId, subFolderName));
	}
	public static void getImagesInFolder(RequestServer request, int id, String detail){
		int[] pictureList = ReadFromDatabase.getImagesInAFolder(id);
		request.sendJsonResponse(new ResponseServer(true, pictureList));
	}
	public static void getImagesInFolderAndSubfolders(RequestServer request, int id, String detail){
		int[] pictureList = ReadFromDatabase.getPicturesInFolderAndSubFolderId(id);
		request.sendJsonResponse(new ResponseServer(true, pictureList));
	}
	public static void getThumbnail(RequestServer request, int id, String detail){
		PictureDb pictureDb = ReadFromDatabase.getPictureBasedOnId(id);
		String fileLocation = pictureDb.getThumbnailFileLocation();
		String[] filetypeSplit = fileLocation.split("\\.");
		BufferedImage thumbnailImage = ImageHandler.getInstance().load(fileLocation);

		request.sendJsonResponse(new ResponseServer(true));
		request.sendImageResponse(thumbnailImage, filetypeSplit[filetypeSplit.length - 1]);
	}
	public static void getThumbnailToAndroid(RequestServer request, int id, String detail){
		PictureDb pictureDb = ReadFromDatabase.getPictureBasedOnId(id);
		String fileLocation = pictureDb.getThumbnailFileLocation();
		String[] fileNameSplit = fileLocation.split("\\.");
		String[] fileType = {fileNameSplit[fileNameSplit.length - 1]};

		request.sendJsonResponse(new ResponseServer(true, id, fileType));
		request.sendImageResponseToAndroid(ImageHandler.getInstance().defaultPath + fileLocation);
	}
	public static void getLargeImage(RequestServer request, int id, String detail){
		PictureDb pictureDb = ReadFromDatabase.getPictureBasedOnId(id);
		String fileLocation = pictureDb.getMediumFileLocation();
		String[] filetypeSplit = fileLocation.split("\\.");
		BufferedImage thumbnailImage = ImageHandler.getInstance().load(fileLocation);

		request.sendJsonResponse(new ResponseServer(true));
		request.sendImageResponse(thumbnailImage, filetypeSplit[filetypeSplit.length - 1]);
	}
	public static void getLargeImageToAndroid(RequestServer request, int id, String detail){
		PictureDb pictureDb = ReadFromDatabase.getPictureBasedOnId(id);
		String fileLocation = pictureDb.getMediumFileLocation();
		String[] fileNameSplit = fileLocation.split("\\.");
		String[] fileType = {fileNameSplit[fileNameSplit.length - 1]};

		request.sendJsonResponse(new ResponseServer(true, id, fileType));
		request.sendImageResponseToAndroid(ImageHandler.getInstance().defaultPath + fileLocation);
	}
	public static void getFullImage(RequestServer request, int id, String detail){
		PictureDb pictureDb = ReadFromDatabase.getPictureBasedOnId(id);
		String fileLocation = pictureDb.getFileLocation();
		String[] filetypeSplit = fileLocation.split("\\.");
		BufferedImage fullImage = ImageHandler.getInstance().load(fileLocation);

		request.sendJsonResponse(new ResponseServer(true));
		request.sendImageResponse(fullImage, filetypeSplit[filetypeSplit.length - 1]);
	}
	public static void getFullImageWithDimensions(RequestServer request, int id, String detail){
		try {
			PictureDb pictureDb = ReadFromDatabase.getPictureBasedOnId(id);
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
	public static void getMetadata(RequestServer request, int id, String detail){
		PictureDb pictureDb = ReadFromDatabase.getPictureBasedOnId(id);
		String[] metadataStringArray = new String[5];
		metadataStringArray[0] = pictureDb.getTitle();
		metadataStringArray[1] = pictureDb.getDescription();
		metadataStringArray[2] = Integer.toString(pictureDb.getRating());
		metadataStringArray[3] = pictureDb.getDateTime();
		metadataStringArray[4] = pictureDb.getTagString();
		request.sendJsonResponse(new ResponseServer(true, metadataStringArray));
	}
	public static void getImagesWithTag(RequestServer request, int id, String detail){
		int[] idArray = ReadFromDatabase.getPicturesBasedOnTag(detail, id);
		request.sendJsonResponse(new ResponseServer(true, idArray));
	}
	public static void getImagesWithMinRating(RequestServer request, int id, String detail){
		int[] idArray = ReadFromDatabase.getPicturesBasedOnRating(Integer.parseInt(detail), id);
		request.sendJsonResponse(new ResponseServer(true, idArray));
	}
	public static void getImagesWithDateTime(RequestServer request, int id, String detail){
		int[] idArray = ReadFromDatabase.getPicturesBasedOnDate(detail, id);
		request.sendJsonResponse(new ResponseServer(true, idArray));
	}	
	public static void getTagsStartingWith(RequestServer request, int id, String detail){

	}
	public static void getNextid(RequestServer request, int id, String detail){
		int nextAvailableId = ReadFromDatabase.findNextPicId();
		System.out.println("Neste id er: " + nextAvailableId);
		request.sendJsonResponse(new ResponseServer(true, nextAvailableId));
	}

	//blir skrevet feil til DB her
	public static void addTag(RequestServer request, int id, String detail){
		PictureDb pictureDb = ReadFromDatabase.getPictureBasedOnId(id);
		String fileLocation = pictureDb.getFileLocation();
		String existingTags = pictureDb.getTagString();
		WriteExif writeExifInfo = new WriteExif(ImageHandler.getInstance().defaultPath + fileLocation);
		writeExifInfo.setExifTags(existingTags + detail + ";");
		writeExifInfo.writeToImage();
		WriteToDatabase.addTagToPic(id, detail);
		request.sendJsonResponse(new ResponseServer(true));
	}
	public static void modifyTitle(RequestServer request, int id, String detail){
		PictureDb pictureDb = ReadFromDatabase.getPictureBasedOnId(id);
		String fileLocation = pictureDb.getFileLocation();
		boolean setTitleinDb = UpdateDatabase.updatePictureTitle(id, detail);
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
	public static void modifyDescription(RequestServer request, int id, String detail){
		PictureDb pictureDb = ReadFromDatabase.getPictureBasedOnId(id);
		String fileLocation = pictureDb.getFileLocation();
		boolean setDescInDb = UpdateDatabase.updatePictureDescription(id, detail);
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
	public static void modifyRating(RequestServer request, int id, String detail){
		PictureDb pictureDb = ReadFromDatabase.getPictureBasedOnId(id);
		String fileLocation = pictureDb.getFileLocation();
		boolean setDescInDb = UpdateDatabase.updatePictureRating(id, Integer.parseInt(detail));
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
	public static void rotate90Clock(RequestServer request, int id, String detail){

	}
	public static void rotate90CounterClock(RequestServer request, int id, String detail){

	}
	public static void addNewImage(RequestServer request, int id, String detail){
		//		System.out.println("id til server: " + id);
		//		BufferedImage bufferedImage = request.receiveImage();
		//		//ImageHandler.IMAGE_HANDLER.createServerImage(id, detail, bufferedImage);
		//		boolean wasCreatedSuccesful = ImageHandler.getInstance().saveAndDispose(id, file);
		//
		//		System.out.println("IMAGE WAS SAVED: "+ wasCreatedSuccesful);
		//		if (wasCreatedSuccesful){
		//			String fullPath = ImageHandler.IMAGE_HANDLER.defaultPath + "\\full\\" + id + "." + detail;
		//			String medPath = ImageHandler.IMAGE_HANDLER.defaultPath + "\\medium\\"+ id + "." + detail;
		//			String thumbPath = ImageHandler.IMAGE_HANDLER.defaultPath + "\\thumb\\"+ id + "." + detail;
		//			PictureDb picDb = new PictureDb("test import", "test import", 1, "2014-02-02 10:10:10", fullPath, medPath, thumbPath);
		//			boolean dbWriteSuccesful = WriteToDatabase.writeOnePic(picDb);
		//			if (dbWriteSuccesful){
		//				request.sendJsonResponse(new ResponseServer(true));
		//				return;
		//			}
		//		}
		//		request.sendJsonResponse(new ResponseServer(false));
	}
	public static void addNewFullImage(RequestServer request, int id, String detail){
		System.out.println("id til server: " + id);
		File tempImage = null;
		try {
			tempImage = request.receiveFile(detail);
			ImageHandler.getInstance().directoryMonitor.ignore(tempImage);
		} catch (Exception e) {
			request.sendJsonResponse(new ResponseServer(false));
			return;
		}
		ReadExif exif = new ReadExif(tempImage.getPath());
		boolean writePictureToFile = ImageHandler.getInstance().save(tempImage,ImageHandler.getInstance().defaultPath);
		if (writePictureToFile){
			Path directory = ImageHandler.getInstance().defaultPath;
			int lio = detail.split("[.]").length;
			String mediumName = detail.split("[.]")[lio-2] + "_medium." + detail.split("[.]")[lio-1];
			String thumbName = detail.split("[.]")[lio-2] + "_thumb." + detail.split("[.]")[lio-1];
			System.out.println(thumbName);
			PictureDb pictureDb = new PictureDb(exif.getExifTitle(), exif.getExifComment(), 
					exif.getExifRating(), exif.getExifDateTimeTaken(), 
					directory + "\\" +detail, 
					directory + "\\" +mediumName, 
					directory + "\\" +thumbName,
					1);
			boolean writePictureToDb = WriteToDatabase.writeOnePic(pictureDb);
			if (writePictureToDb){
				if (!(exif.getExifTags() == null)) {
					String[] tagsInString = exif.getExifTags().split(";");
					for (int i = 0; i < tagsInString.length; i++) {
						boolean test = WriteToDatabase.addTagToPic(id, tagsInString[i]);
						System.out.println("tag skrevet: " + test);
					}
				}
				request.sendJsonResponse(new ResponseServer(true));
			}

		} 
	}
}
