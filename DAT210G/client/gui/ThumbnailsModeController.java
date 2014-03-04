package gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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
	@FXML
	private TextField titleTextField;
	@FXML
	private TextField descTextField;
	@FXML
	private TextField ratingTextField;
	@FXML
	private TextField dateTextField;
	@FXML
	private TextField tagsTextField;

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

		thumbnailsFlowPane.getChildren().clear();
		System.out.println();
		System.out.println();
		System.out.println();
		for (int i = 0; i < main.getImageList().size(); i++) {
			System.out.print("- [" + i + "]" + " " + "[" + main.getImageList().get(i).getImageId() + "] ");
		}
		System.out.println();
		gridRows = (int) Math.ceil(1.0 * main.getImageList().size() / gridColumns);
		
		
		
		
		
		int numberOfImagesInList = main.getImageList().size();
		
		for (int i = 0; i < numberOfImagesInList; i++) {
			ImageView tempImage = main.getImageList().get(i).getImageView();
			thumbnailsFlowPane.getChildren().add(tempImage);
		}
		
		
		
		
		

		
	}
}
