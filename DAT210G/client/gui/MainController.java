package gui;

import java.io.File;
import java.io.IOException;
import java.util.List;

import gui.MainController;
import gui.model.Model;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;

public class MainController extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;
	private Model model;
	private Boolean contactWithServer;
	

	public MainController() {
		model = new Model(this);
		
		contactWithServer = model.updateImageHashMap();
		
		if (!contactWithServer) {
			System.out.println("No contact with server!");
			Platform.exit();
		}
		
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
			scene.getStylesheets().add(getClass().getResource("view/style.css").toExternalForm());
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
			thumbnailsController.setMain(this, model);

		} catch (IOException e) {
			// Exception gets thrown if the fxml file could not be loaded
			e.printStackTrace();
		}
	}
	
	public void openFileChooser() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Import images");
				
		List<File> fileList =
                fileChooser.showOpenMultipleDialog(primaryStage);
            model.sendImagesToServer(fileList);
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
			singleModeController.setMain(this, model);

		} catch (IOException e) {
			// Exception gets thrown if the fxml file could not be loaded
			e.printStackTrace();
		}
	}


	

	public static void main(String[] args) {
		launch(args);
	}
}
