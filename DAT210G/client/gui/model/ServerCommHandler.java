package gui.model;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Hashtable;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import logic.RequestClient;
import logic.ResponseClient;
import communication.JsonClient;


public class ServerCommHandler {

	public static int[] getAllImageIds() {
		int[] allImageId = null;
		JsonClient getAllImagesJson = new JsonClient(new RequestClient(
				"getAllImages"));
		if (getAllImagesJson.sendJsonToServer()) {
			ResponseClient getAllImagesResponse = getAllImagesJson
					.receiveJsonFromServer();
			if (getAllImagesResponse.getSuccess()) {
				allImageId = getAllImagesResponse.getImageIdArray();
			}
			getAllImagesJson.closeHttpConnection();
		} else {
			return null;
		}
		return allImageId;
	}



	public static boolean sendNewDirReqToServer(String dirName, int parentId) {
		boolean success = false;
		JsonClient sendDirName = new JsonClient(new RequestClient("addNewDirectory", parentId, dirName));
		if (sendDirName.sendJsonToServer()) {
			ResponseClient addDirResponse = sendDirName
					.receiveJsonFromServer();
			System.out.println("sent til server: " + addDirResponse.getSuccess());
			sendDirName.closeHttpConnection();
			success = true;
		}
		return success;
	}

	public static int[] searchTilePictures(String title, int parentId) {
		int[] pictureIdArray = null;
		JsonClient sendTitle = new JsonClient(new RequestClient("getImagesWithTitle", parentId, title));
		if (sendTitle.sendJsonToServer()) {
			ResponseClient searchTitleResponse = sendTitle.receiveJsonFromServer();
			sendTitle.closeHttpConnection();
			pictureIdArray = searchTitleResponse.getImageIdArray();
		}
		return pictureIdArray;		
	}

	public static int[] searchRatingPictures(String rating, int parentId) {
		int[] pictureIdArray = null;
		JsonClient sendRating = new JsonClient(new RequestClient("getImagesWithMinRating", parentId, rating));
		if (sendRating.sendJsonToServer()) {
			ResponseClient searchRatingResponse = sendRating.receiveJsonFromServer();
			sendRating.closeHttpConnection();
			pictureIdArray = searchRatingResponse.getImageIdArray();
		}
		return pictureIdArray;
	}

	public static int[] searchDescriptionPictures(String desc, int parentId) {
		int[] pictureIdArray = null;
		JsonClient sendDesc = new JsonClient(new RequestClient("getImagesWithDesc", parentId, desc));
		if (sendDesc.sendJsonToServer()) {
			ResponseClient searchDescResponse = sendDesc.receiveJsonFromServer();
			sendDesc.closeHttpConnection();
			pictureIdArray = searchDescResponse.getImageIdArray();
		}
		return pictureIdArray;
	}

	public static int[] searchDateTimePictures(String dateTime, int parentId) {
		int[] pictureIdArray = null;
		JsonClient sendDateTime = new JsonClient(new RequestClient("getImagesWithDateTime", parentId, dateTime));
		if (sendDateTime.sendJsonToServer()) {
			ResponseClient searchDescResponse = sendDateTime.receiveJsonFromServer();
			sendDateTime.closeHttpConnection();
			pictureIdArray = searchDescResponse.getImageIdArray();
		}
		return pictureIdArray;
	}

	public static int[] searchTagsPictures(String tag, int parentId) {
		int[] pictureIdArray = null;
		JsonClient sendTag = null;
		if (tag.contains(";")) {
			sendTag = new JsonClient(new RequestClient("getImagesWithManyTags", parentId, tag));
		} else {
			sendTag = new JsonClient(new RequestClient("getImagesWithTag", parentId, tag));
		}
		if (sendTag.sendJsonToServer()) {
			ResponseClient searchDescResponse = sendTag.receiveJsonFromServer();
			sendTag.closeHttpConnection();
			pictureIdArray = searchDescResponse.getImageIdArray();
		}
		return pictureIdArray;
	}

	public static boolean SendImageToServer(File fileToSend, int parentId) {

		boolean success = false;
		String fileName = null;

		fileName = fileToSend.getName();
		boolean imageWasSentSuccessful = false;
		while (imageWasSentSuccessful == false) {
			JsonClient sendImageJson = new JsonClient(new RequestClient(
					"addNewFullImage", parentId, fileName));
			if (sendImageJson.sendJsonToServer()) {
				try {
					System.out.println("Prøver å sende bilde: "
							+ fileToSend.getPath());
					sendImageJson.sendFileToServer(fileToSend.getPath());
					ResponseClient response = sendImageJson
							.receiveJsonFromServer();
					imageWasSentSuccessful = response.getSuccess();
					if (response.getSuccess()) {
						success = true;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				sendImageJson.closeHttpConnection();
			}
		}

		return success;
	}

	public static Hashtable<Integer, String> getSubFoldersIdAndName(int id) {

		int[] subFoldersId = null;
		String[] subFoldersName = null;
		Hashtable<Integer, String> idAndNames = new Hashtable<>();

		JsonClient json = new JsonClient(new RequestClient("getSubFolders", id,
				null));

		if (json.sendJsonToServer()) {

			ResponseClient response = json.receiveJsonFromServer();

			System.out.println(response.toString());

			if (response.getSuccess()) {
				System.out.println("sucess");
				subFoldersId = response.getImageIdArray();
				subFoldersName = response.getStringArray();

			}
			json.closeHttpConnection();
		} else {
			System.out.println("fail2");

		}

		for (int i = 0; i < subFoldersId.length; i++) {
			idAndNames.put(subFoldersId[i], subFoldersName[i]);
		}

		System.out.println(idAndNames.toString());

		return idAndNames;

	}



	public static int[] getAllImagesInFolder(int id) {

		int[] imageIdArray = null;

		JsonClient json = new JsonClient(new RequestClient("getImagesInFolder",
				id, null));

		if (json.sendJsonToServer()) {

			ResponseClient response = json.receiveJsonFromServer();

			System.out.println(response.toString());

			if (response.getSuccess()) {
				System.out.println("sucess");
				imageIdArray = response.getImageIdArray();

			}
			json.closeHttpConnection();
		} else {
			System.out.println("fail2");

		}

		return imageIdArray;

	}

	public static ImageView getFullImage(int imageID) {
		BufferedImage bufImage = null;
		JsonClient getThumbnailJson = new JsonClient(new RequestClient(
				"getFullImage", imageID));
		if (getThumbnailJson.sendJsonToServer()) {
			ResponseClient getThumbnailResponse = getThumbnailJson
					.receiveJsonFromServer();
			if (getThumbnailResponse.getSuccess()) {
				bufImage = getThumbnailJson.receiveImageFromServer();
			}
			getThumbnailJson.closeHttpConnection();
		}

		// TODO: FOR DEBUG

		Image image = null;

		if (bufImage != null) {
			// TODO: FOR DEBUG

			addDebugNumber(imageID, bufImage);

			image = SwingFXUtils.toFXImage(bufImage, null);
		}

		// For testing
		if (image == null) {
			image = new Image("testfull.png");
		}

		return new ImageView(image);
	}

	public static ImageView getMediumImage(int imageID) {
		BufferedImage bufImage = null;
		JsonClient getMediumJson = new JsonClient(new RequestClient(
				"getFullImageWithDimensions", imageID, "500;500"));
		if (getMediumJson.sendJsonToServer()) {
			ResponseClient getThumbnailResponse = getMediumJson
					.receiveJsonFromServer();
			if (getThumbnailResponse.getSuccess()) {
				bufImage = getMediumJson.receiveImageFromServer();
			}
			getMediumJson.closeHttpConnection();
		}

		Image image = null;

		if (bufImage != null) {
			// TODO: FOR DEBUG

			addDebugNumber(imageID, bufImage);

			image = SwingFXUtils.toFXImage(bufImage, null);
		}

		// For testing
		if (image == null) {
			image = new Image("testmedium.png");
		}

		return new ImageView(image);
	}

	public static ImageView getThumbnail(int imageID) {
	
		BufferedImage bufImage = null;
		JsonClient getThumbnailJson = new JsonClient(new RequestClient(
				"getThumbnail", imageID));
		if (getThumbnailJson.sendJsonToServer()) {
			ResponseClient getThumbnailResponse = getThumbnailJson
					.receiveJsonFromServer();
			if (getThumbnailResponse.getSuccess()) {
				bufImage = getThumbnailJson.receiveImageFromServer();
	
			}
			getThumbnailJson.closeHttpConnection();
		}
	
		Image image = null;
	
		if (bufImage != null) {
			// TODO: FOR DEBUG
	
			addDebugNumber(imageID, bufImage);
	
			image = SwingFXUtils.toFXImage(bufImage, null);
		}
	
		// For testing
		if (image == null) {
			image = new Image("testthumbnail.png");
		}
	
		return new ImageView(image);
	
	}
	
	private static void addDebugNumber(int imageID, BufferedImage bufImage) {
		Graphics2D g = (Graphics2D) bufImage.createGraphics();
		Font font = new Font("Verdana", Font.ITALIC, 24);
		g.setFont(font);
		g.setColor(Color.RED);
		g.drawString(String.valueOf(imageID), 15, 15);
		
	}



	public static String[] getMetaData(int imageID) {

		String[] metaData = null;

		JsonClient getMetaDataJson = new JsonClient(new RequestClient(
				"getMetadata", imageID));
		if (getMetaDataJson.sendJsonToServer()) {
			ResponseClient getMetaDataResponse = getMetaDataJson
					.receiveJsonFromServer();
			if (getMetaDataResponse.getSuccess()) {
				metaData = getMetaDataResponse.getStringArray();
			}
			getMetaDataJson.closeHttpConnection();
		}

		System.out.println(metaData[0] + " " + metaData[1] + " " + metaData[2]
				+ " " + metaData[3] + " " + metaData[4]);

		return metaData;
	}

	public static Boolean modifyTitle(int imageID, String string) {

		Boolean success = false;

		JsonClient modifyTitle = new JsonClient(new RequestClient(
				"modifyTitle", imageID, string));
		if (modifyTitle.sendJsonToServer()) {
			ResponseClient modifyTitleResponse = modifyTitle
					.receiveJsonFromServer();
			if (modifyTitleResponse.getSuccess()) {
				success = true;
			}
			modifyTitle.closeHttpConnection();
		}

		return success;

	}

	public static Boolean modifyDesc(int imageID, String string) {

		Boolean success = false;

		JsonClient modifyDescription = new JsonClient(new RequestClient(
				"modifyDescription", imageID, string));
		if (modifyDescription.sendJsonToServer()) {
			ResponseClient modifyTitleResponse = modifyDescription
					.receiveJsonFromServer();
			if (modifyTitleResponse.getSuccess()) {
				success = true;
			}
			modifyDescription.closeHttpConnection();
		}

		return success;

	}

	public static Boolean modifyRating(int imageID, String string) {

		Boolean success = false;

		JsonClient modifyRating = new JsonClient(new RequestClient(
				"modifyRating", imageID, string));
		if (modifyRating.sendJsonToServer()) {
			ResponseClient modifyTitleResponse = modifyRating
					.receiveJsonFromServer();
			if (modifyTitleResponse.getSuccess()) {
				success = true;
			}
			modifyRating.closeHttpConnection();
		}

		return success;

	}

	public static Boolean addTag(int imageID, String string) {

		Boolean success = false;

		JsonClient addTag = new JsonClient(new RequestClient("addTag", imageID,
				string));
		if (addTag.sendJsonToServer()) {
			ResponseClient modifyTitleResponse = addTag.receiveJsonFromServer();
			if (modifyTitleResponse.getSuccess()) {
				success = true;
			}
			addTag.closeHttpConnection();
		}

		return success;

	}

	public static ImageView getRotLeft(int imageID) {
		BufferedImage bufImage = null;
		JsonClient getThumbnailJson = new JsonClient(new RequestClient(
				"rotate90CounterClock", imageID));
		if (getThumbnailJson.sendJsonToServer()) {
			ResponseClient getThumbnailResponse = getThumbnailJson
					.receiveJsonFromServer();
			if (getThumbnailResponse.getSuccess()) {
				bufImage = getThumbnailJson.receiveImageFromServer();
			}
			getThumbnailJson.closeHttpConnection();
		}

		// TODO: FOR DEBUG

		// String text = String.valueOf(imageID)
		// + " rotated left: Ikkje klart på serveren ennå";
		Graphics2D g = null;
		try {
			g = (Graphics2D) bufImage.createGraphics();
		} catch (Exception e) {
			return null;
		}
		Font font = new Font("Verdana", Font.ITALIC, 24);
		g.setFont(font);
		g.setColor(Color.RED);
		// g.drawString(text, 20, 20);

		// TODO: Kommer avogtil nullpointerexception her. Kanskje fordi jeg
		// blander swing og JavaFX?
		Image image = SwingFXUtils.toFXImage(bufImage, null);

		return new ImageView(image);
	}

	public static ImageView getRotRight(int imageID) {
		BufferedImage bufImage = null;
		JsonClient getThumbnailJson = new JsonClient(new RequestClient(
				"rotate90Clock", imageID));
		if (getThumbnailJson.sendJsonToServer()) {
			ResponseClient getThumbnailResponse = getThumbnailJson
					.receiveJsonFromServer();
			if (getThumbnailResponse.getSuccess()) {
				bufImage = getThumbnailJson.receiveImageFromServer();
			}
			getThumbnailJson.closeHttpConnection();
		}

		// TODO: FOR DEBUG

		// String text = String.valueOf(imageID)
		// + " rotated right: Ikkje klart på serveren ennå";

		Graphics2D g;
		try {
			g = (Graphics2D) bufImage.createGraphics();
		} catch (Exception e) {
			return null;
		}
		Font font = new Font("Verdana", Font.ITALIC, 24);
		g.setFont(font);
		g.setColor(Color.RED);
		// g.drawString(text, 20, 20);

		// TODO: Kommer avogtil nullpointerexception her. Kanskje fordi jeg
		// blander swing og JavaFX?
		Image image = SwingFXUtils.toFXImage(bufImage, null);

		return new ImageView(image);
	}




}