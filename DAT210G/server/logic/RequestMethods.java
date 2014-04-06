package logic;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import net.coobird.thumbnailator.Thumbnails;
import storing.*;

public class RequestMethods {

	public static void getAllImages(RequestServer request, int id, String detail) {
		int[] allid = ReadFromDatabase.getAllPicIds();
		request.sendJsonResponse(new ResponseServer(true, allid));
	}

	public static void getSubFolders(RequestServer request, int id,
			String detail) {
		List<TreeMenuNode> folders = ReadFromDatabase.getTreeForMenu();
		int[] subFolderId = null;
		String[] subFolderName = null;
		for (int i = 0; i < folders.size(); i++) {
			if (folders.get(i).getRoot().getFolderId() == id) {
				subFolderId = new int[folders.get(i).getChildren().size()];
				subFolderName = new String[folders.get(i).getChildren().size()];
				for (int j = 0; j < folders.get(i).getChildren().size(); j++) {
					subFolderId[j] = folders.get(i).getChildren().get(j)
							.getRoot().getFolderId();
					subFolderName[j] = folders.get(i).getChildren().get(j)
							.getRoot().getName();
				}
			}
		}
		request.sendJsonResponse(new ResponseServer(true, subFolderId,
				subFolderName));
	}

	public static void getImagesInFolder(RequestServer request, int id,
			String detail) {
		int[] pictureList = ReadFromDatabase.getImagesInAFolder(id);
		request.sendJsonResponse(new ResponseServer(true, pictureList));
	}

	public static void getImagesInFolderAndSubfolders(RequestServer request,
			int id, String detail) {
		int[] pictureList = ReadFromDatabase
				.getPicturesInFolderAndSubFolderId(id);
		request.sendJsonResponse(new ResponseServer(true, pictureList));
	}

	public static void getThumbnail(RequestServer request, int id, String detail) {
		PictureDb pictureDb = ReadFromDatabase.getPictureBasedOnId(id);
		String fileLocation = pictureDb.getThumbnailFileLocation();
		String[] filetypeSplit = fileLocation.split("\\.");
		BufferedImage thumbnailImage = ImageHandler.load(fileLocation);

		request.sendJsonResponse(new ResponseServer(true));
		request.sendImageResponse(thumbnailImage,
				filetypeSplit[filetypeSplit.length - 1]);
	}

	public static void getLargeImage(RequestServer request, int id,
			String detail) {
		PictureDb pictureDb = ReadFromDatabase.getPictureBasedOnId(id);
		String fileLocation = pictureDb.getMediumFileLocation();
		String[] filetypeSplit = fileLocation.split("\\.");
		BufferedImage thumbnailImage = ImageHandler.load(fileLocation);

		request.sendJsonResponse(new ResponseServer(true));
		request.sendImageResponse(thumbnailImage,
				filetypeSplit[filetypeSplit.length - 1]);
	}

	public static void getFullImage(RequestServer request, int id, String detail) {
		PictureDb pictureDb = ReadFromDatabase.getPictureBasedOnId(id);
		String fileLocation = pictureDb.getFileLocation();
		String[] filetypeSplit = fileLocation.split("\\.");
		BufferedImage fullImage = ImageHandler.load(fileLocation);

		request.sendJsonResponse(new ResponseServer(true));
		request.sendImageResponse(fullImage,
				filetypeSplit[filetypeSplit.length - 1]);
	}

	public static void getFullImageWithDimensions(RequestServer request,
			int id, String detail) {
		try {
			PictureDb pictureDb = ReadFromDatabase.getPictureBasedOnId(id);
			String fileLocation = pictureDb.getFileLocation();
			String[] filetypeSplit = fileLocation.split("\\.");
			String[] dimensions = detail.split(";");
			int imageHeight = Integer.parseInt(dimensions[0]);
			int imageWidth = Integer.parseInt(dimensions[1]);
			BufferedImage fullImage = ImageHandler.load(fileLocation);
			BufferedImage scaledImage = Thumbnails.of(fullImage)
					.size(imageHeight, imageWidth).asBufferedImage();
			request.sendJsonResponse(new ResponseServer(true));
			request.sendImageResponse(scaledImage,
					filetypeSplit[filetypeSplit.length - 1]);
		} catch (IOException e) {
			request.sendJsonResponse(new ResponseServer(false));
		}
	}

	public static void getMetadata(RequestServer request, int id, String detail) {
		PictureDb pictureDb = ReadFromDatabase.getPictureBasedOnId(id);
		String[] metadataStringArray = new String[5];
		metadataStringArray[0] = pictureDb.getTitle();
		metadataStringArray[1] = pictureDb.getDescription();
		metadataStringArray[2] = Integer.toString(pictureDb.getRating());
		metadataStringArray[3] = pictureDb.getDateTime();
		metadataStringArray[4] = pictureDb.getTagString();
		request.sendJsonResponse(new ResponseServer(true, metadataStringArray));
	}

	public static void getImagesWithTag(RequestServer request, int id,
			String detail) {
		int[] idArray = ReadFromDatabase.getPicturesBasedOnTag(detail, id);
		request.sendJsonResponse(new ResponseServer(true, idArray));
	}

	public static void getImagesWithMinRating(RequestServer request, int id,
			String detail) {
		int[] idArray = ReadFromDatabase.getPicturesBasedOnRating(
				Integer.parseInt(detail), id);
		request.sendJsonResponse(new ResponseServer(true, idArray));
	}

	public static void getImagesWithDateTime(RequestServer request, int id,
			String detail) {
		int[] idArray = ReadFromDatabase.getPicturesBasedOnDate(detail, id);
		request.sendJsonResponse(new ResponseServer(true, idArray));
	}

	public static void getTagsStartingWith(RequestServer request, int id,
			String detail) {
		String[] tagArray = ReadFromDatabase.getTagsStartingWith(detail);
		request.sendJsonResponse(new ResponseServer(true, tagArray));
	}

	public static void getNextImageId(RequestServer request, int id,
			String detail) {
		int nextAvailableId = ReadFromDatabase.findNextPicId();
		System.out.println("Neste id er: " + nextAvailableId);
		request.sendJsonResponse(new ResponseServer(true, nextAvailableId));
	}

	public static void addTag(RequestServer request, int id, String detail) {
		PictureDb pictureDb = ReadFromDatabase.getPictureBasedOnId(id);
		String fileLocation = pictureDb.getFileLocation();
		String existingTags = pictureDb.getTagString();
		WriteExif writeExifInfo = new WriteExif(
				ImageHandler.getDefaultPathString() + fileLocation);
		writeExifInfo.setExifTags(existingTags + detail + ";");
		writeExifInfo.writeToImage();
		WriteToDatabase.addTagToPic(id, detail);
		request.sendJsonResponse(new ResponseServer(true));
	}

	public static void modifyTitle(RequestServer request, int id, String detail) {
		PictureDb pictureDb = ReadFromDatabase.getPictureBasedOnId(id);
		String fileLocation = pictureDb.getFileLocation();
		boolean setTitleinDb = UpdateDatabase.updatePictureTitle(id, detail);
		if (setTitleinDb) {
			WriteExif writeExifInfo = new WriteExif(
					ImageHandler.getDefaultPathString() + fileLocation);
			writeExifInfo.setExifTitle(detail);
			writeExifInfo.writeToImage();
			request.sendJsonResponse(new ResponseServer(true));
		} else {
			request.sendJsonResponse(new ResponseServer(false));
		}
	}

	public static void modifyDescription(RequestServer request, int id,
			String detail) {
		PictureDb pictureDb = ReadFromDatabase.getPictureBasedOnId(id);
		String fileLocation = pictureDb.getFileLocation();
		boolean setDescInDb = UpdateDatabase.updatePictureDescription(id,
				detail);
		if (setDescInDb) {
			WriteExif writeExifInfo = new WriteExif(
					ImageHandler.getDefaultPathString() + fileLocation);
			writeExifInfo.setExifComment(detail);
			writeExifInfo.writeToImage();
			request.sendJsonResponse(new ResponseServer(true));
		} else {
			request.sendJsonResponse(new ResponseServer(false));
		}
	}

	public static void modifyRating(RequestServer request, int id, String detail) {
		PictureDb pictureDb = ReadFromDatabase.getPictureBasedOnId(id);
		String fileLocation = pictureDb.getFileLocation();
		boolean setDescInDb = UpdateDatabase.updatePictureRating(id,
				Integer.parseInt(detail));
		if (setDescInDb) {
			WriteExif writeExifInfo = new WriteExif(
					ImageHandler.getDefaultPathString() + fileLocation);
			writeExifInfo.setExifRating(Integer.parseInt(detail));
			writeExifInfo.writeToImage();
			request.sendJsonResponse(new ResponseServer(true));
		} else {
			request.sendJsonResponse(new ResponseServer(false));
		}
	}

	public static void rotate90Clock(RequestServer request, int id,
			String detail) {
		PictureDb pictureDb = ReadFromDatabase.getPictureBasedOnId(id);
		ImageManipulate.rotateImage(pictureDb, false);
		request.sendJsonResponse(new ResponseServer(true));
	}

	public static void rotate90CounterClock(RequestServer request, int id,
			String detail) {
		PictureDb pictureDb = ReadFromDatabase.getPictureBasedOnId(id);
		ImageManipulate.rotateImage(pictureDb, true);
		request.sendJsonResponse(new ResponseServer(true));
	}

	public static void addNewFullImage(RequestServer request, int id,
			String detail) {
		System.out.println("id til server: " + id);
		File tempImage = null;
		try {
			tempImage = request.receiveFile(detail);
		} catch (Exception e) {
			request.sendJsonResponse(new ResponseServer(false));
			return;
		}
		ReadExif exif = new ReadExif(tempImage.getPath());
		boolean writePictureToFile = ImageHandler.saveAll(tempImage);
		System.out.println(writePictureToFile + " " + detail);
		if (writePictureToFile) {

			int lio = detail.split("[.]").length;
			String mediumName = detail.split("[.]")[lio - 2] + "_medium."
					+ detail.split("[.]")[lio - 1];
			String thumbName = detail.split("[.]")[lio - 2] + "_thumb."
					+ detail.split("[.]")[lio - 1];
			PictureDb pictureDb = new PictureDb(exif.getExifTitle(),
					exif.getExifComment(), exif.getExifRating(),
					exif.getExifDateTimeTaken(), detail, mediumName, thumbName,
					// TODO Endre 1 til mappe id.
					1);
			boolean writePictureToDb = WriteToDatabase.writeOnePic(pictureDb);
			if (writePictureToDb) {
				if (!(exif.getExifTags() == null)) {
					String[] tagsInString = exif.getExifTags().split(";");
					for (int i = 0; i < tagsInString.length; i++) {
						boolean test = WriteToDatabase.addTagToPic(id,
								tagsInString[i]);
						System.out.println("tag skrevet: " + test);
					}
				}
				request.sendJsonResponse(new ResponseServer(true));
			}

		}
	}
}
