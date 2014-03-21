package gui.model;

import java.util.ArrayList;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class OneImage {


	private int imageId;
	private int folderId;
	private ServerCommHandler serverCommHandler;
	private String titleMeta;
	private String descMeta;
	private String ratingMeta;
	private String dateMeta;
	private String tagsMeta;
	private FolderTree folderTreeModel;

	public OneImage(int imageId, int folderId, ServerCommHandler serverComm, FolderTree folderTreeModel) {

		this.imageId = imageId;	
		this.folderId = folderId;
		this.serverCommHandler = serverComm;
		this.folderTreeModel = folderTreeModel;
					

	}

	public int getImageId() {
		return imageId;
	}
	
	public ArrayList<ImageView> getThumbnailsFromThisFolderDown() {

		ArrayList<ImageView> imageViewArray = new ArrayList<>();

		int[] imageIdArray = serverCommHandler
				.getImagesInFolderAndSubfolders(folderId);

		for (int i = 0; i < imageIdArray.length; i++) {

			ImageView tempImage = serverCommHandler
					.getThumbnail(imageIdArray[i]);
			
			tempImage.setOnMouseClicked(new EventHandler<Event>() {

				@Override
				public void handle(Event ev) {
					System.out.println("Clicked image: " + imageId);
					
					
				}
				
			});
			
			imageViewArray.add(tempImage);

		}

		return imageViewArray;

	}

	public ArrayList<ImageView> getMediumFromThisFolderDown() {

		ArrayList<ImageView> imageViewArray = new ArrayList<>();

		int[] imageIdArray = serverCommHandler
				.getImagesInFolderAndSubfolders(folderId);

		for (int i = 0; i < imageIdArray.length; i++) {

			ImageView tempImage = serverCommHandler
					.getMediumImage(imageIdArray[i]);

			tempImage.setOnMouseClicked(new EventHandler<Event>() {

				@Override
				public void handle(Event ev) {
					System.out.println("Clicked image: " + imageId);
					
					
				}
				
			});
			

			imageViewArray.add(tempImage);

		}

		return imageViewArray;

	}



	public ImageView getFullImage() {


		return serverCommHandler.getFullImage(imageId);		
		

	}
	/*
	public ImageView getRotLeft() {


		thumbnailImage = serverCommHandler.getRotLeft(imageId);

		return thumbnailImage;

	}
	
	public ImageView getRotRight() {


		thumbnailImage = serverCommHandler.getRotRight(imageId);

		return thumbnailImage;

	}

	public String getTitleMeta() {

		titleMeta = serverCommHandler.getMetaData(imageId)[0];
		
		return titleMeta;

	}

	public String getDescMeta() {
		descMeta = serverCommHandler.getMetaData(imageId)[1];
		
		return descMeta;
	}

	public String getRatingMeta() {
		ratingMeta = serverCommHandler.getMetaData(imageId)[2];
		
		return ratingMeta;
	}

	public String getDateMeta() {
		dateMeta = serverCommHandler.getMetaData(imageId)[3];
		
		return dateMeta;
	}

	public String getTagsMeta() {
		tagsMeta = serverCommHandler.getMetaData(imageId)[4];
		
		return tagsMeta;
	}
	
	public void modifyTitle(String string) {
		Boolean success = serverCommHandler.modifyTitle(imageId, string);
		
		if (success) {
			//System.out.println("sucess");
		}
	}
	
	public void modifyDesc(String string) {
		Boolean success = serverCommHandler.modifyDesc(imageId, string);
		
		if (success) {
			//System.out.println("sucess");
		}
	}
	
	public void modifyRating(String string) {
		Boolean success = serverCommHandler.modifyRating(imageId, string);
		
		if (success) {
			//System.out.println("sucess");
		}
	}
	
	public void addTag(String string) {
		Boolean success = serverCommHandler.addTag(imageId, string);
		
		if (success) {
			//System.out.println("sucess");
		}
	}

*/


	public int getFolderId() {
		return folderId;
	}
	
	

	
	
	



}
