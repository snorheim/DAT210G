package gui;

import java.util.Map;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import gui.MainController;
import gui.model.Model;

public class ThumbnailsModeController {

	// Reference to the main application
	private MainController main;
	

	@FXML
	private TilePane thumbnailsTilePane;
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

	
	private Model model;

	/**
	 * Is called by the Main class to give a reference back to itself.
	 * 
	 * @param main
	 */
	public void setMain(MainController main, Model model) {
		this.main = main;
		this.model = model;
		makeGridAndDisplayImages();
	

	}

	/**
	 * Called when the user clicks refresh.
	 */
	@FXML
	private void handleRefreshBtn() {
		System.out.println("Clicked refresh");
		model.updateImageHashMap();
		makeGridAndDisplayImages();
	}

	/**
	 * Called when the user clicks import.
	 */
	@FXML
	private void handleImportBtn() {
		
		main.openFileChooser();
		
	}

	private void makeGridAndDisplayImages() {

		thumbnailsTilePane.setPrefColumns(4);
		thumbnailsTilePane.setPadding(new Insets(5, 0, 5, 0));
		thumbnailsTilePane.setVgap(10);
		thumbnailsTilePane.setHgap(10);
		
		
		for(Map.Entry<Integer, ImageView> image : model.getImageHashMap().entrySet()) {									
			
			thumbnailsTilePane.getChildren().add(image.getValue());
			
		}
		
		
		

		
	}
}
