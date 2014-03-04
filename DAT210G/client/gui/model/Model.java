package gui.model;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class Model {

	private HashMap<Integer, ImageView> imageHashMap;
	private int currentImageId;
	private int currentFolderId;
	private ServerCommHandler serverCommHandler;	

	public Model() {

		serverCommHandler = new ServerCommHandler();

	}

	
	public boolean updateImageHashMap() {
		
		
		
		int[] imageIdArray = serverCommHandler.getAllImageIds();
			
		if (imageIdArray == null) {
			return false;
		}
		
		
		imageHashMap = new HashMap<Integer, ImageView>();
		
		
		for (int i = 0; i < imageIdArray.length; i++) {
			
			ImageView image = getThumbnail(imageIdArray[i]);
						
			
			image.setOnMouseClicked(new EventHandler<Event>() {

				@Override
				public void handle(Event event) {
					System.out.println("clicked image" + event.getSource().hashCode());
					
				}
			});
			
			
			imageHashMap.put(imageIdArray[i], image);
		}
								
		
		if(imageHashMap.isEmpty()) {
			
			System.out.println("hashmap is empty");
		}
		System.out.println(imageHashMap.toString());
		
		return true;
		
	}

	public ImageView getThumbnail(int imageId) {
		return serverCommHandler.getThumbnail(imageId);
	}

	// TODO: getMediumImage

	public Image getLargeImage(int imageId) {
		return serverCommHandler.getLargeImage(imageId);
	}

	public void sendImagesToServer(List<File> fileList) {
		
		// TODO: fix så ikke den sender evig mange bilder
		
		if (fileList != null) {
			for (File file : fileList) {

				serverCommHandler.SendImageToServer(fileList.get(0));

			}
		}
		
	}
	
	
	
	
	
	public HashMap<Integer, ImageView> getImageHashMap() {
		return imageHashMap;
	}


	public String getTitleMeta(int imageId) {
		
		return serverCommHandler.getMetaData(imageId)[0];
		
		
	}
	
	public String getDescMeta(int imageId) {
		return serverCommHandler.getMetaData(imageId)[1];
	}
	
	public String getRatingMeta(int imageId) {
		return serverCommHandler.getMetaData(imageId)[2];
	}
	
	public String getDateMeta(int imageId) {
		return serverCommHandler.getMetaData(imageId)[3];
	}
	
	public String getTagsMeta(int imageId) {
		return serverCommHandler.getMetaData(imageId)[4];
	}

	public int getCurrentImageId() {
		return currentImageId;
	}

	public void setCurrentImageId(int currentImageId) {
		this.currentImageId = currentImageId;
	}

	public int getCurrentFolderId() {
		return currentFolderId;
	}

	public void setCurrentFolderId(int currentFolderId) {
		this.currentFolderId = currentFolderId;
	}

}
