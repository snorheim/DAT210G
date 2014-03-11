package gui.model;

import gui.MainController;

import java.io.File;
import java.util.Hashtable;
import java.util.List;


public class Model {

	private Hashtable<Integer, OneImage> imageHashtable;
	private int currentImageId;
	private int currentFolderId;
	private ServerCommHandler serverCommHandler;	
	private MainController mainController;

	public Model(MainController mainController) {

		this.mainController = mainController;
		serverCommHandler = new ServerCommHandler();
		updateImageHashMap();

	}

	
	public boolean updateImageHashMap() {
		
		
		
		int[] imageIdArray = serverCommHandler.getAllImageIds();
			
		if (imageIdArray == null) {
			return false;
		}
		
		
		imageHashtable = new Hashtable<Integer, OneImage>();
		
		
		for (int i = 0; i < imageIdArray.length; i++) {
												
			
			OneImage oneImage = new OneImage(imageIdArray[i], serverCommHandler, this);
			
			
			imageHashtable.put(imageIdArray[i], oneImage);
		}
								
		
		if(imageHashtable.isEmpty()) {
			
			System.out.println("hashmap is empty");
		}
		System.out.println(imageHashtable.toString());
		
		
		
		return true;
		
	}

	

	public void sendImagesToServer(List<File> fileList) {
		
		// TODO: fix så ikke den sender evig mange bilder
		
		if (fileList != null) {
			for (File file : fileList) {

				serverCommHandler.SendImageToServer(file);

			}
		}
		
	}
		
	
	public Hashtable<Integer, OneImage> getImageHashtable() {
		return imageHashtable;
	}
	
	public OneImage getCurrentOneImage() {
		
		
		return imageHashtable.get(currentImageId);
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
