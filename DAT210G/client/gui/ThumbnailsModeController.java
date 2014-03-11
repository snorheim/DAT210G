package gui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import gui.MainController;
import gui.model.FolderTree;
import gui.model.Model;

public class ThumbnailsModeController {

	private static final int SMALL_ZOOM = 6;
	private static final int MEDIUM_ZOOM = 3;
	
	
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
	@FXML
	private Slider zoomSlider;
	@FXML
	private HBox hboxForTree;
	
	private FolderTree folderTree;
	
	private GridPane thumbnailGridPane;
	
	
	private int gridColumns = SMALL_ZOOM;
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
		
		zoomSlider.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable,
					Number oldValue, Number newValue) {
					
				
				
				if (newValue.intValue() == 1) {
					setGridColumns(MEDIUM_ZOOM);					
				} else if (newValue.intValue() == 0) {
					setGridColumns(SMALL_ZOOM);					
				}
				
				
			}
		});
		
		folderTree = new FolderTree(this, model);
		
		hboxForTree.getChildren().add(folderTree.getTree());
		
		makeGridAndDisplayImages();
	

	}

	// TODO: Slett dette
	/**
	 * Called when the user clicks refresh.
	 *
	@FXML
	private void handleRefreshBtn() {
		System.out.println("Clicked refresh");
		model.updateImageHashMap();
		makeGridAndDisplayImages();
	}
	
	*/

	/**
	 * Called when the user clicks import.
	 */
	@FXML
	private void handleImportBtn() {
		
		main.openFileChooser();
		
	}

	private void makeGridAndDisplayImages() {
		
		anchorPane.getChildren().clear();
		
		
		thumbnailGridPane = new GridPane();
		thumbnailGridPane.setHgap(10);
		thumbnailGridPane.setVgap(10);
		thumbnailGridPane.gridLinesVisibleProperty();
		
		
		gridRows = (int) Math.ceil(1.0 * model.getImageHashtable().size() / gridColumns);
		
		System.out.println(gridColumns + " " + gridRows);
		
		
		if (getGridColumns() == SMALL_ZOOM) {
			smallZoomLevelImages();
		} else if (getGridColumns() == MEDIUM_ZOOM) {
		
			mediumZoomLevelImages();
		
		}
		
		
		
		
		anchorPane.getChildren().add(thumbnailGridPane);
		
		
		

		
	}

	private void smallZoomLevelImages() {
		int imageNum = 1;
		
		
		
		for (int i = 0; i < gridRows; i++) {
			
			for (int j = 0; j < gridColumns; j++) {
				System.out.println(imageNum);
				thumbnailGridPane.add(model.getImageHashtable().get(imageNum).getThumbnailImage(), j, i);
				
				imageNum++;
				if (imageNum > model.getImageHashtable().size()) {
					break;
				}
			}
			
			if (imageNum > model.getImageHashtable().size()) {
				break;
			}
			
		}
		
	}
	
	private void mediumZoomLevelImages() {
		int imageNum = 1;
		
		for (int i = 0; i < gridRows; i++) {
			
			for (int j = 0; j < gridColumns; j++) {
				System.out.println(imageNum);
				thumbnailGridPane.add(model.getImageHashtable().get(imageNum).getMediumImage(), j, i);
				
				
				imageNum++;
				if (imageNum > model.getImageHashtable().size()) {
					break;
				}
			}
			
			if (imageNum > model.getImageHashtable().size()) {
				break;
			}
			
		}
	}

	public int getGridColumns() {
		return gridColumns;
	}

	public void setGridColumns(int gridColumns) {
		
		if(!zoomSlider.isValueChanging()) {
			this.gridColumns = gridColumns;
			makeGridAndDisplayImages();
		}
	}
	
	
}
