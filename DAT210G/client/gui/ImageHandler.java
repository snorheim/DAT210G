package gui;

import javax.imageio.ImageIO;

import communication.JsonClient;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import logic.RequestClient;
import logic.ResponseClient;

/**
 * Created by Ronnie on 12.02.14.
 *
 *
 * Bare en simulering. Legger inn kode her for å snakke med server.
 */
public class ImageHandler {

	private int[] imageIdArray;

	public ImageHandler() {

		imageIdArray = getAllImages();
	}

	public int[] getAllImages() {
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

	public BufferedImage getThumbnail(int imageID) {
		BufferedImage image = null;
		JsonClient getThumbnailJson = new JsonClient(new RequestClient("getThumbnail", imageID));
		if (getThumbnailJson.sendJsonToServer()){
			ResponseClient getThumbnailResponse = getThumbnailJson.receiveJsonFromServer();
			if (getThumbnailResponse.getSuccess()){
				image = getThumbnailJson.receiveImageFromServer();
			}
			getThumbnailJson.closeHttpConnection();
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

			JsonClient sendImageJson = new JsonClient(new RequestClient("addNewImage", nextAvailableId, fileType));
			if (sendImageJson.sendJsonToServer()){ 
				sendImageJson.sendImageToServer(image, fileType);
				sendImageJson.closeHttpConnection();
			}
		}

	}

	public BufferedImage getLargeImage(int imageID) {
		BufferedImage image = null;
		JsonClient getThumbnailJson = new JsonClient(new RequestClient("getFullImageWithDimensions", imageID, "800;600"));
		if (getThumbnailJson.sendJsonToServer()){
			ResponseClient getThumbnailResponse = getThumbnailJson.receiveJsonFromServer();
			if (getThumbnailResponse.getSuccess()){
				image = getThumbnailJson.receiveImageFromServer();
			}
			getThumbnailJson.closeHttpConnection();
		}
		return image;
	}
	
	
	public String[] getMetaData(int imageID) {
		
		String[] metaData = null;	
		
		JsonClient getMetaDataJson = new JsonClient(new RequestClient("getMetaData", imageID));
		if (getMetaDataJson.sendJsonToServer()){
			ResponseClient getMetaDataResponse = getMetaDataJson.receiveJsonFromServer();
			if (getMetaDataResponse.getSuccess()){
				metaData = getMetaDataResponse.getStringArray();
			}
			getMetaDataJson.closeHttpConnection();
		}
		return metaData;
	}
	
	


}
