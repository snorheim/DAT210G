package gui;

import java.io.IOException;
import java.util.ArrayList;

import gui.MainController;
import gui.model.OneImage;
import gui.model.ServerCommHandlerTesting;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;

public class MainController extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;
	private ServerCommHandlerTesting serverCommHandler;
	private ArrayList<OneImage> imageList = new ArrayList<>();
	private int currentImageId;

	public MainController() {
		serverCommHandler = new ServerCommHandlerTesting();
		updateImageList();
	}

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("DAT210G Photo Gallery");

		try {
			// Load the root layout from the fxml file
			FXMLLoader loader = new FXMLLoader(
					MainController.class.getResource("view/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			// Exception if fxml file isn't loaded
			e.printStackTrace();
		}

		showThumbnailsMode();
		
		
	}

	/**
	 * Shows the thumbnails scene.
	 */
	public void showThumbnailsMode() {
		try {
			// Load the fxml file and set into the center of the main layout
			FXMLLoader loader = new FXMLLoader(
					MainController.class
					.getResource("view/ThumbnailsMode.fxml"));
			AnchorPane thumbnailsMode = (AnchorPane) loader.load();
			rootLayout.setCenter(thumbnailsMode);

			// Give the controller access to the mainController
			ThumbnailsModeController thumbnailsController = loader.getController();
			thumbnailsController.setMain(this);

		} catch (IOException e) {
			// Exception gets thrown if the fxml file could not be loaded
			e.printStackTrace();
		}
	}
	
	/**
	 * Shows the single image scene.
	 */
	public void showSingleMode() {
		try {
			// Load the fxml file and set into the center of the main layout
			FXMLLoader loader = new FXMLLoader(
					MainController.class
					.getResource("view/SingleMode.fxml"));
			AnchorPane singleMode = (AnchorPane) loader.load();
			rootLayout.setCenter(singleMode);

			// Give the controller access to the mainController
			SingleModeController singleModeController = loader.getController();
			singleModeController.setMain(this);

		} catch (IOException e) {
			// Exception gets thrown if the fxml file could not be loaded
			e.printStackTrace();
		}
	}

	/**
	 * Returns the data as an observable list of OneImage.
	 * 
	 * @return
	 */
	public ArrayList<OneImage> getImageList() {
		return imageList;
	}

	public void updateImageList() {
		
		imageList.clear();
		
		int[] imageIdArray = serverCommHandler.getAllImageIds();		

		for (int i = 0; i < imageIdArray.length; i++) {
			System.out.println(serverCommHandler.getAllImageIds()[i]);
			imageList.add(new OneImage(this, serverCommHandler.getThumbnail(imageIdArray[i]), imageIdArray[i]));
		}
		
		
	}
	
	

	public ServerCommHandlerTesting getServerCommHandler() {
		return serverCommHandler;
	}

	public int getCurrentImageId() {
		return currentImageId;
	}

	public void setCurrentImageId(int currentImageId) {
		this.currentImageId = currentImageId;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
