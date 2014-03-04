package gui.model;

import gui.MainController;

import java.io.File;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;




import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class Model {

	private Hashtable<Integer, OneImage> imageHashtable;
	private int currentImageId;
	private int currentFolderId;
	private ServerCommHandler serverCommHandler;	
	private MainController mainController;

	public Model(MainController mainController) {

		this.mainController = mainController;
		serverCommHandler = new ServerCommHandler();

	}

	
	public boolean updateImageHashMap() {
		
		
		
		int[] imageIdArray = serverCommHandler.getAllImageIds();
			
		if (imageIdArray == null) {
			return false;
		}
		
		
		imageHashtable = new Hashtable<Integer, OneImage>();
		
		
		for (int i = 0; i < imageIdArray.length; i++) {
												
			
			OneImage oneImage = new OneImage(imageIdArray[i], getThumbnail(imageIdArray[i]), this);
			
			
			imageHashtable.put(imageIdArray[i], oneImage);
		}
								
		
		if(imageHashtable.isEmpty()) {
			
			System.out.println("hashmap is empty");
		}
		System.out.println(imageHashtable.toString());
		
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
	
	
	
	
	
	public Hashtable<Integer, OneImage> getImageHashtable() {
		return imageHashtable;
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
		mainController.showSingleMode();
	}

	public int getCurrentFolderId() {
		return currentFolderId;
	}

	public void setCurrentFolderId(int currentFolderId) {
		this.currentFolderId = currentFolderId;
	}
	
	

}
