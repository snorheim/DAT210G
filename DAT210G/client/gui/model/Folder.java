package gui.model;

import java.util.ArrayList;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;

public class Folder {

	private ServerCommHandler serverCommHandler;

	private int folderId;
	private String folderName;
	private FolderTree folderTreeModel;
	
	

	public Folder(ServerCommHandler serverCommHandler, int folderId,
			String folderName, FolderTree folderTreeModel) {

		this.serverCommHandler = serverCommHandler;
		this.folderId = folderId;

		this.folderName = folderName;
		this.folderTreeModel = folderTreeModel;

		
	}
	
	

	public void setFolderTreeModel(FolderTree folderTreeModel) {
		this.folderTreeModel = folderTreeModel;
	}

	

	public String getFolderName() {
		return folderName;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}

	public int getFolderId() {
		return folderId;
	}

	public void setFolderId(int folderId) {
		this.folderId = folderId;
	}

	public String toString() {

		return folderName + "    (" + folderId + ")    ";

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
					System.out.println("Clicked image: ");
					
					
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
					System.out.println("Clicked image: ");
					
					
				}
				
			});
			

			imageViewArray.add(tempImage);

		}

		return imageViewArray;

	}

}
