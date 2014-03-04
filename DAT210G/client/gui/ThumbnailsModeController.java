package gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import gui.MainController;

public class ThumbnailsModeController {

	// Reference to the main application
	private MainController main;

	@FXML
	private FlowPane thumbnailsFlowPane;
	@FXML
	private Button importBtn;

	private int gridColumns = 4;
	private int gridRows;
	int imgCounter;

	/**
	 * Is called by the Main class to give a reference back to itself.
	 * 
	 * @param main
	 */
	public void setMain(MainController main) {
		this.main = main;
		makeGridAndDisplayImages();
	

	}

	/**
	 * Called when the user clicks refresh.
	 */
	@FXML
	private void handleRefreshBtn() {
		System.out.println("Clicked refresh");
		main.updateImageList();
		makeGridAndDisplayImages();
	}

	/**
	 * Called when the user clicks import.
	 */
	@FXML
	private void handleImportBtn() {
		System.out.println("Clicked import");
		main.openFileChooser();
		
	}

	private void makeGridAndDisplayImages() {

		
		
		
		
		
		

		
	}
}
