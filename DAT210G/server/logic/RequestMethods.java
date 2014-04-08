package logic;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
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
			log(fileLocation);
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

	public static void getImagesWithTitle(RequestServer request, int id, String detail) {
		int[] idArray = ReadFromDatabase.getPicturesBasedOnTitle(detail, id);
		request.sendJsonResponse(new ResponseServer(true, idArray));
	}

	public static void getImagesWithDesc(RequestServer request, int id, String detail) {
		int[] idArray = ReadFromDatabase.getPicturesBasedOnDesc(detail, id);
		request.sendJsonResponse(new ResponseServer(true, idArray));
	}

	public static void getImagesWithTag(RequestServer request, int id,
			String detail) {
		int[] idArray = ReadFromDatabase.getPicturesBasedOnTag(detail, id);
		request.sendJsonResponse(new ResponseServer(true, idArray));
	}

	public static void getImagesWithManyTags(RequestServer request, int id,
			String detail) {
		String[] tags = detail.split(";");
		int[] idArray = ReadFromDatabase.getPicturesBasedOnManyTags(tags, id);
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

	public static void addNewDirectory(RequestServer request, int id,
			String detail) {

		DirectoryPoop.addNewDirectory(detail, id);

		System.out.println("skulle lagt til dir n�, parentid: " + id
				+ ", foldername: " + detail);
		request.sendJsonResponse(new ResponseServer(true));
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
		File tempImage = null;
		try {
			tempImage = request.receiveFile(detail);
		} catch (Exception e) {
			request.sendJsonResponse(new ResponseServer(false));
			return;
		}

		ParentFolderDb pfdb = ReadFromDatabase.getFolderInfo(id);
		String path = Paths.get(pfdb.getPath()).toString() + "\\";
		boolean writePictureToFile = ImageHandler.saveAll(tempImage,
				Paths.get(pfdb.getPath()));

		ReadExif exif = new ReadExif(tempImage.getPath());

		log(path);
		if (path.startsWith("img\\"))
			path = path.substring(4, path.length());

		if (writePictureToFile) {
			String fullName = path + detail;
			String mediumName = path
					+ DirectoryPoop.getMediumName(tempImage.getName());
			String thumbName = path
					+ DirectoryPoop.getThumbnailName(tempImage.getName());
			PictureDb pictureDb = new PictureDb(exif.getExifTitle(),
					exif.getExifComment(), exif.getExifRating(),
					exif.getExifDateTimeTaken(), fullName, mediumName,
					thumbName, id);
			if (id != 1)
				tempImage.delete();

			int writePictureToDb = WriteToDatabase.writeOnePic(pictureDb);
			if (writePictureToDb != 0) {
				if (!(exif.getExifTags() == null)) {
					String[] tagsInString = exif.getExifTags().split(";");
					for (int i = 0; i < tagsInString.length; i++) {
						boolean addTagToPicture = WriteToDatabase.addTagToPic(writePictureToDb,
								tagsInString[i]);
						System.out.println("tag skrevet: " + addTagToPicture);
					}
				}
				request.sendJsonResponse(new ResponseServer(true));
			}

		}
	}

	private static void log(String s) {
		Loggy.log("RQM@ " + s);
	}
}
