package gui;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import gui.MainController;
import gui.model.Model;
import gui.model.OneImage;

public class ThumbnailsModeController {

	// Reference to the main application
	private MainController main;
	

	@FXML
	private AnchorPane anchorPane;
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
	
	private GridPane thumbnailGridPane;
	
	private int gridColumns = 4;
	private int gridRows;

	
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
		
		thumbnailGridPane = new GridPane();
		thumbnailGridPane.setHgap(10);
		thumbnailGridPane.setVgap(10);
		thumbnailGridPane.gridLinesVisibleProperty();
		
		gridRows = (int) Math.ceil(1.0 * model.getImageHashtable().size() / gridColumns);
		
		System.out.println(gridColumns + " " + gridRows);
		
		
		int imageNum = 1;
		
		for (int i = 0; i < gridRows; i++) {
			
			for (int j = 0; j < gridColumns; j++) {
				System.out.println(imageNum);
				thumbnailGridPane.add(model.getImageHashtable().get(imageNum).getThumbnail(), j, i);
				
				imageNum++;
				if (imageNum > model.getImageHashtable().size()) {
					break;
				}
			}
			
			if (imageNum > model.getImageHashtable().size()) {
				break;
			}
			
		}
		
		
		
		
		anchorPane.getChildren().add(thumbnailGridPane);
		
		
		
		/*for(Map.Entry<Integer, OneImage> image : model.getImageHashMap().entrySet()) {									
			
			//thumbnailsGridPane.getChildren().add(image.getValue().getThumbnail());
			
			
		}*/
		
		
		

		
	}
}
