package gui;

import gui.model.FolderTree;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;
	private Boolean contactWithServer;
	private FolderTree folderTreeModel;
	private Rectangle2D screenBounds;
	private FXMLLoader loader;
	private Scene scene;
	private AnchorPane thumbnailsMode;
	private AnchorPane singleMode;
	private ManyViewController manyViewController;

	@Override
	public void init() {

		

		System.out.println("Contact with server: " + contactWithServer);

		// TODO: exit program hvis ikke kontakt med server
	}

	@Override
	public void start(Stage primaryStage) {

		screenBounds = Screen.getPrimary().getVisualBounds();
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("DAT210G Photo Gallery");

		try {
			// Load the root layout from the fxml file

			loader = new FXMLLoader(
					Main.class.getResource("view/MainView.fxml"));

			rootLayout = (BorderPane) loader.load();

			scene = new Scene(rootLayout, screenBounds.getWidth() / 2,
					screenBounds.getHeight() / 2);

			
			// TODO: Lag bedre css style sheet!!!!!!!
			//scene.getStylesheets().add(
			//		getClass().getResource("view/style.css").toExternalForm());

			// Give the controller access to Main
			MainController mainController = loader.getController();
			mainController.setMain(this);

			primaryStage.setScene(scene);
			primaryStage.show();

		} catch (Exception e) {
			// Exception if fxml file isn't loaded
			e.printStackTrace();
		}

		setManyMode(true);

	}

	/**
	 * Shows the thumbnails scene.
	 */
	public void setManyMode(boolean doUpdate) {
		try {

			// Load the fxml file and set into the center of the main layout
			loader = new FXMLLoader(
					Main.class.getResource("view/ManyView.fxml"));
			thumbnailsMode = (AnchorPane) loader.load();

			rootLayout.setCenter(thumbnailsMode);

			// Give the controller access to the mainController
			manyViewController = loader.getController();
			manyViewController.setMainController(this);

			
			if (doUpdate) {
				update();
			}
			

			manyViewController.setFolderTreeModel(folderTreeModel);

			manyViewController.start();

			// folderTreeModel = new FolderTree(this);

			// manyViewController.start(folderTreeModel);

		} catch (IOException e) {
			// Exception gets thrown if the fxml file could not be loaded
			e.printStackTrace();
		}
	}

	public void update() {
		folderTreeModel = new FolderTree(this);
		folderTreeModel.setManyViewController(manyViewController);
		folderTreeModel.update();
	}

	/**
	 * Shows the single image scene.
	 */
	public void setSingleMode() {
		try {
			// Load the fxml file and set into the center of the main layout
			FXMLLoader loader = new FXMLLoader(
					Main.class.getResource("view/SingleView.fxml"));
			singleMode = (AnchorPane) loader.load();

			rootLayout.setCenter(singleMode);

			// Give the controller access to the mainController
			SingleViewController singleViewController = loader.getController();
			singleViewController.setMainController(this);
			singleViewController.setModel(folderTreeModel);
			singleViewController.displayImage();

		} catch (IOException e) {
			// Exception gets thrown if the fxml file could not be loaded
			e.printStackTrace();
		}
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public Scene getScene() {
		return scene;
	}

	public Rectangle2D getScreenBounds() {
		return screenBounds;
	}

	public static void main(String[] args) {
		launch(args);
	}
}