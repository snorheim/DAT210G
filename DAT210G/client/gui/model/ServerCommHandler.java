package gui.model;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;

import communication.JsonClient;








import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;








import logic.RequestClient;
import logic.ResponseClient;

/**
 * Created by Ronnie on 12.02.14.
 *
 * Denne snakker med HttpClient og JsonClient
 * 
 */
public class ServerCommHandler {



	public int[] getAllImageIds() {
		int[] allImageId = null;
		JsonClient getAllImagesJson = new JsonClient(new RequestClient("getAllImages"));
		if (getAllImagesJson.sendJsonToServer()){
			ResponseClient getAllImagesResponse = getAllImagesJson.receiveJsonFromServer();
			if (getAllImagesResponse.getSuccess()){
				allImageId = getAllImagesResponse.getImageIdArray();
			}
			getAllImagesJson.closeHttpConnection();
		}
		return allImageId;
	}

	public Image getThumbnail(int imageID) {
		BufferedImage bufImage = null;
		JsonClient getThumbnailJson = new JsonClient(new RequestClient("getThumbnail", imageID));
		if (getThumbnailJson.sendJsonToServer()){
			ResponseClient getThumbnailResponse = getThumbnailJson.receiveJsonFromServer();
			if (getThumbnailResponse.getSuccess()){
				bufImage = getThumbnailJson.receiveImageFromServer();
			}
			getThumbnailJson.closeHttpConnection();
		}

		 

		Image image = null;
		
		if (bufImage != null) {
			// TODO: FOR DEBUG 

			Graphics2D g = (Graphics2D) bufImage.createGraphics();
			Font font = new Font("Verdana", Font.ITALIC, 24);
			g.setFont(font);
			g.setColor(Color.RED);
			g.drawString(String.valueOf(imageID), 15, 15);
			
			image = SwingFXUtils.toFXImage(bufImage, null);
		} 

		return image;
	}
	public void SendImageToServer(File fileToSend){
		int nextAvailableId = -1;
		BufferedImage image = null;
		String fileType = null;
		try {
			image = ImageIO.read(fileToSend);
		} catch (IOException e) {
			e.printStackTrace();
		}
		JsonClient sendImageJson1 = new JsonClient(new RequestClient("getNextImageId"));
		if (sendImageJson1.sendJsonToServer()){
			ResponseClient sendImageResponse1 = sendImageJson1.receiveJsonFromServer();
			nextAvailableId = sendImageResponse1.getImageId();
			System.out.println("Next available id: "+nextAvailableId);
			sendImageJson1.closeHttpConnection();
		}
		int i = fileToSend.getAbsolutePath().lastIndexOf('.');
		if (i > 0) {
			fileType = fileToSend.getAbsolutePath().substring(i+1);
			System.out.println("Filtype til nytt bilde: " + fileType + " id: " + nextAvailableId);
		}
		if (nextAvailableId != -1){
			boolean imageWasSentSuccessful = false;
			while (imageWasSentSuccessful == false){
				JsonClient sendImageJson = new JsonClient(new RequestClient("addNewFullImage", nextAvailableId, fileType));
				if (sendImageJson.sendJsonToServer()){ 
					//sendImageJson.sendImageToServer(image, fileType);
					try {
						System.out.println("Prøver å sende bilde: " + fileToSend.getPath());
						sendImageJson.sendFileToServer(fileToSend.getPath());
						ResponseClient response = sendImageJson.receiveJsonFromServer();
						imageWasSentSuccessful = response.getSuccess();
					} catch (Exception e) {
						e.printStackTrace();
					}
					sendImageJson.closeHttpConnection();
				}
			}
		}

	}

	public Image getLargeImage(int imageID) {
		BufferedImage bufImage = null;
		JsonClient getThumbnailJson = new JsonClient(new RequestClient("getFullImageWithDimensions", imageID, "800;600"));
		if (getThumbnailJson.sendJsonToServer()){
			ResponseClient getThumbnailResponse = getThumbnailJson.receiveJsonFromServer();
			if (getThumbnailResponse.getSuccess()){
				bufImage = getThumbnailJson.receiveImageFromServer();
			}
			getThumbnailJson.closeHttpConnection();
		}

		// TODO: FOR DEBUG 

		Graphics2D g = (Graphics2D) bufImage.createGraphics();
		Font font = new Font("Verdana", Font.ITALIC, 24);
		g.setFont(font);
		g.setColor(Color.RED);
		g.drawString(String.valueOf(imageID), 20, 20); 

		//TODO: Kommer avogtil nullpointerexception her. Kanskje fordi jeg blander swing og JavaFX?
		Image image = SwingFXUtils.toFXImage(bufImage, null);


		return image;
	}



	public String[] getMetaData(int imageID) {

		String[] metaData = null;	

		JsonClient getMetaDataJson = new JsonClient(new RequestClient("getMetadata", imageID));
		if (getMetaDataJson.sendJsonToServer()){
			ResponseClient getMetaDataResponse = getMetaDataJson.receiveJsonFromServer();
			if (getMetaDataResponse.getSuccess()){
				metaData = getMetaDataResponse.getStringArray();
			}
			getMetaDataJson.closeHttpConnection();
		}

		System.out.println(metaData[0] + " " + metaData[1] + " " + metaData[2] + " " + metaData[3] + " " + metaData[4] );


		return metaData;
	}





}
