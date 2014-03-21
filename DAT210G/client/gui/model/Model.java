package gui.model;

import gui.MainController;

import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;


public class Model {

	private int[] imageIdArray;
	private ArrayList<OneImage> imageList;
	private int currentImageId;
	private int currentFolderId;
	private ServerCommHandler serverCommHandler;	
	private MainController mainController;


	public Model(MainController mainController) {

		this.mainController = mainController;
		serverCommHandler = new ServerCommHandler();

	}


	public boolean updateIdArray() {

		

		imageIdArray = serverCommHandler.getAllImageIds();

		if (imageIdArray == null) {
			return false;
		}


		imageList = new ArrayList<OneImage>();


		for (int i = 0; i < imageIdArray.length; i++) {


			OneImage oneImage = new OneImage(imageIdArray[i], serverCommHandler, this);


			imageList.add(i, oneImage);
		}


		if(imageList.isEmpty()) {

			System.out.println("imageList is empty");
		}
		System.out.println(imageList.toString());
		



		return true;

	}

	public void fillWithThumbnails() {
		System.out.println("fill thumbnails ++++++++++++++++++++++++++++++++++++++++++++++++++++");
		System.out.println(imageIdArray.length);
		System.out.println(imageList.size());

		for (int i = 0; i < imageIdArray.length; i++) {


			System.out.println("imageid: " + imageList.get(i).getImageId());
			System.out.println("i: " + i);
			
			
			imageList.get(i).setThumbnailImage();
			

		}


	}

	public void fillWithMediumImages() {

		for (int i = 0; i < imageIdArray.length; i++) {


			imageList.get(i).setMediumImage();


		}

	}



	public void sendImagesToServer(List<File> fileList) {

		// TODO: fix så ikke den sender evig mange bilder

		if (fileList != null) {
			for (File file : fileList) {

				serverCommHandler.SendImageToServer(file);

			}
		}

	}


	public ArrayList<OneImage> getImageList() {
		return imageList;
	}

	public OneImage getCurrentOneImage() {


		return imageList.get(currentImageId-1);
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
