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
	private Rectangle2D screenBounds;
	private Scene scene;
	private AnchorPane manyModePane;
	private AnchorPane singleModePane;
	private ManyViewController manyViewController;
	private SingleViewController singleViewController;

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

			FXMLLoader loader = new FXMLLoader(
					Main.class.getResource("view/MainView.fxml"));

			rootLayout = (BorderPane) loader.load();

			scene = new Scene(rootLayout, screenBounds.getWidth() / 2,
					screenBounds.getHeight() / 2);

			// TODO: Lag bedre css style sheet!!!!!!!
			// scene.getStylesheets().add(
			// getClass().getResource("view/style.css").toExternalForm());

			// Give the controller access to Main
			MainController mainController = loader.getController();
			mainController.setMain(this);
			FolderTree.setMain(this);

			primaryStage.setScene(scene);
			primaryStage.show();

		} catch (Exception e) {
			// Exception if fxml file isn't loaded
			e.printStackTrace();
		}

		try {
			FXMLLoader loader = new FXMLLoader(
					Main.class.getResource("view/ManyView.fxml"));
			manyModePane = (AnchorPane) loader.load();
			manyViewController = loader.getController();
			manyViewController.setMainController(this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			// Load the fxml file and set into the center of the main layout
			FXMLLoader loader = new FXMLLoader(
					Main.class.getResource("view/SingleView.fxml"));
			singleModePane = (AnchorPane) loader.load();
			singleViewController = loader.getController();
			singleViewController.setMainController(this);

		} catch (IOException e) {
			// Exception gets thrown if the fxml file could not be loaded
			e.printStackTrace();
		}

		FolderTree.setManyViewController(manyViewController);

		setManyMode(true);
		;

	}

	/**
	 * Shows the thumbnails scene.
	 */
	public void setManyMode(boolean doUpdate) {

		rootLayout.setCenter(manyModePane);

		// Give the controller access to the mainController

		if (doUpdate) {
			update();
		}

		manyViewController.start();

	}

	public void update() {

		FolderTree.update();
	}

	/**
	 * Shows the single image scene.
	 */
	public void setSingleMode() {

		System.out.println("ImageId: "
				+ FolderTree.getCurrentImage().getImageId());

		rootLayout.setCenter(singleModePane);

		// Give the controller access to the mainController

		singleViewController.showScaledToScreenImage();

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