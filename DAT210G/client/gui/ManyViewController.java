package gui;

import java.util.ArrayList;

import gui.model.FolderTree;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class ManyViewController {

	private FolderTree folderTreeModel;

	private MainController mainController;

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

	private static final int SMALL_ZOOM = 6;
	private static final int MEDIUM_ZOOM = 3;

	@FXML
	private Label antallBilder;

	private GridPane thumbnailGridPane;

	private int gridColumns = SMALL_ZOOM;
	private int gridRows = 0;

	@FXML
	public void goToSingleView() {

		mainController.setSingleMode();

	}

	/**
	 * Called when the user clicks import.
	 */
	@FXML
	private void handleImportBtn() {
		
		  mainController.openFileChooser();
		  
		  makeGridAndDisplayImages();
		 
		System.out.println("clicked import");

	}

	public void updateFolderTree() {
		hboxForTree.getChildren().add(folderTreeModel.getTree());

	}

	public void makeGridAndDisplayImages() {
		
		folderTreeModel.updateTree();

		anchorPane.getChildren().clear();

		thumbnailGridPane = new GridPane();
		thumbnailGridPane.setHgap(10);
		thumbnailGridPane.setVgap(10);
		thumbnailGridPane.gridLinesVisibleProperty();

		if (getGridColumns() == SMALL_ZOOM) {
			smallZoomLevelImages();
		} else if (getGridColumns() == MEDIUM_ZOOM) {

			mediumZoomLevelImages();

		}

		System.out.println("size of gridpane now: "
				+ thumbnailGridPane.getChildren().size());

		anchorPane.getChildren().add(thumbnailGridPane);

		System.out.println("making grid and displaying images");

	}

	private void smallZoomLevelImages() {

		ArrayList<ImageView> imagesToBeDisplayed = folderTreeModel
				.getCurrentFolder().getThumbnailsFromThisFolderDown();					
		
 
		if (!imagesToBeDisplayed.isEmpty()) {

			gridRows = (int) Math.ceil(1.0 * imagesToBeDisplayed.size()
					/ gridColumns);

			System.out.println(gridColumns + " " + gridRows);

			int imageNum = 0;

			for (int i = 0; i < gridRows; i++) {

				for (int j = 0; j < gridColumns; j++) {

					thumbnailGridPane.add(imagesToBeDisplayed.get(imageNum), j,
							i);
					
					

					// thumbnailGridPane.add(new
					// Button(Integer.toString(imagesToBeDisplayed.get(imageNum).getImageId())),
					// j, i);

					imageNum++;
					if (imageNum >= imagesToBeDisplayed.size()) {
						break;
					}
				}

				if (imageNum >= imagesToBeDisplayed.size()) {
					break;
				}

			}

		}

	}

	private void mediumZoomLevelImages() {

		ArrayList<ImageView> imagesToBeDisplayed = folderTreeModel
				.getCurrentFolder().getMediumFromThisFolderDown();

		if (!imagesToBeDisplayed.isEmpty()) {

			gridRows = (int) Math.ceil(1.0 * imagesToBeDisplayed.size()
					/ gridColumns);

			System.out.println(gridColumns + " " + gridRows);

			int imageNum = 0;

			for (int i = 0; i < gridRows; i++) {

				for (int j = 0; j < gridColumns; j++) {

					thumbnailGridPane.add(imagesToBeDisplayed.get(imageNum), j,
							i);

					// thumbnailGridPane.add(new
					// Button(Integer.toString(imagesToBeDisplayed.get(imageNum).getImageId())),
					// j, i);

					imageNum++;
					if (imageNum >= imagesToBeDisplayed.size()) {
						break;
					}
				}

				if (imageNum >= imagesToBeDisplayed.size()) {
					break;
				}

			}

		}

		System.out.println("we are in mediumzoomlevel");
	}

	public int getGridColumns() {
		return gridColumns;
	}

	public void setGridColumns(int gridColumns) {
		this.gridColumns = gridColumns;
	}

	public int getGridRows() {
		return gridRows;
	}

	public void setGridRows(int gridRows) {
		this.gridRows = gridRows;
	}

	public static int getSmallZoom() {
		return SMALL_ZOOM;
	}

	public static int getMediumZoom() {
		return MEDIUM_ZOOM;
	}

	public void setMainController(MainController mainController) {
		this.mainController = mainController;
	}

	public void setModel(FolderTree folderTreeModel) {
		this.folderTreeModel = folderTreeModel;
		updateFolderTree();

		zoomSlider.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable,
					Number oldValue, Number newValue) {

				if (newValue.intValue() == 1) {
					setGridColumns(MEDIUM_ZOOM);
					System.out.println("medium zoom");
					makeGridAndDisplayImages();
				} else if (newValue.intValue() == 0) {
					setGridColumns(SMALL_ZOOM);
					System.out.println("small zoom");
					makeGridAndDisplayImages();
				}

			}
		});

	}

}
