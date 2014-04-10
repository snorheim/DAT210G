package gui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.controlsfx.dialog.Dialogs;

import gui.model.FolderTree;
import gui.model.OneImage;
import gui.model.ServerCommHandler;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;

public class ManyViewController {

	private FolderTree folderTreeModel;

	private Main main;

	@FXML
	private AnchorPane anchorPaneForMany;
	@FXML
	private Button importBtn;
	@FXML
	private Button dirButton;
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

	private boolean filterImages = false;
	private ArrayList<OneImage> filteredImages;
	

	private ScrollPane scrollPane;
	private FlowPane drawPane;

	private ZoomLevel currentZooom;

	private enum ZoomLevel {
		SMALL, MEDIUM
	}
	
	public ManyViewController() {
	
		scrollPane = new ScrollPane();
		drawPane = new FlowPane();
		
	}

	@FXML
	public void goToSingleView() {

		main.setSingleMode();

	}

	/**
	 * Called when the user clicks import.
	 */
	@FXML
	private void handleImportBtn() {



		openFileChooser();



		folderTreeModel.update();


		start();

	}



	@FXML
	private void handleNewDirectoryBtn() {


		String newDirectoryName = Dialogs.create()
				.masthead(null)
				.title("Add a directory")

				.message( "Enter name of new directory!")
				.showTextInput();


		if (newDirectoryName != null) {

			if (!newDirectoryName.isEmpty()) {
				ServerCommHandler.sendNewDirReqToServer(newDirectoryName,
						folderTreeModel.getCurrentFolder().getFolderId());

				folderTreeModel.update();
				start();

			}
		}

	}

	private void findImagesMatchingFilter(int[] pictureIds) {
		filteredImages = new ArrayList<>();



		for (int i: pictureIds) {

			for (OneImage image : folderTreeModel.getAllImagesList()) {
				if (image.getImageId() == i) {
					filteredImages.add(image);
				}
			}

		}

		filterImages = true;

		beginDrawingImages();
		
		hboxForTree.getChildren().clear();
		

		filterImages = false;
	}

	@FXML
	private void titleSearchAl() {
		String titleText = titleTextField.getText();
		
		if (!titleText.isEmpty()) {
		
		int[] pictureIds = null;
		if (!titleText.equals("")) {
			pictureIds = ServerCommHandler.searchTilePictures(titleText, folderTreeModel.getCurrentFolder().getFolderId());
		}

		findImagesMatchingFilter(pictureIds);
		
		} else {
			beginDrawingImages();
			updateFolderTree();
		}

	}

	@FXML
	private void ratingSearchAl() {
		//TODO: sjekke input
		String rating = ratingTextField.getText();
		
		if (!rating.isEmpty()) {
		
		int ratingCheck = Integer.parseInt(rating);
		int[] pictureIds = null;
		if (ratingCheck > 0 && ratingCheck < 6) {
			pictureIds = ServerCommHandler.searchRatingPictures(rating, folderTreeModel.getCurrentFolder().getFolderId());
		}
		findImagesMatchingFilter(pictureIds);
		
		} else {
			beginDrawingImages();
			updateFolderTree();
		}
	}

	@FXML
	private void descSearchAl() {
		String description = descTextField.getText();
		
		if (!description.isEmpty()) {
		
		int[] pictureIds = null;
		if (!description.equals("")) {
			pictureIds = ServerCommHandler.searchDescriptionPictures(description, folderTreeModel.getCurrentFolder().getFolderId());
		}
		findImagesMatchingFilter(pictureIds);
		
		} else {
			beginDrawingImages();
			updateFolderTree();
		}

	}

	@FXML
	private void dateSearchAl() {
		String dateTime = dateTextField.getText();
		
		if (!dateTime.isEmpty()) {
		
		int[] pictureIds = null;
		if (!dateTime.equals("")) {
			pictureIds = ServerCommHandler.searchDateTimePictures(dateTime, folderTreeModel.getCurrentFolder().getFolderId());
		}
		findImagesMatchingFilter(pictureIds);
		
		} else {
			beginDrawingImages();
			updateFolderTree();
		}
	}

	@FXML
	private void tagSearchAl() {
		//TODO: bug ved nytt sok
		String tags = tagsTextField.getText().toLowerCase();
		
		if (!tags.isEmpty()) {
		
		int[] pictureIds = null;
		if (!tags.equals("")) {
			pictureIds = ServerCommHandler.searchTagsPictures(tags, folderTreeModel.getCurrentFolder().getFolderId());
		}

		findImagesMatchingFilter(pictureIds);
		
		} else {
			beginDrawingImages();
			updateFolderTree();
		}

	}

	public void updateFolderTree() {

		hboxForTree.getChildren().clear();

		hboxForTree.getChildren().add(folderTreeModel.getTree());

	}

	public void start() {

		if (!folderTreeModel.isReady()) {

			ProgressIndicator bar = new ProgressIndicator();
			bar.progressProperty().bind(
					folderTreeModel.getTask().progressProperty());

			
			setupScrollingArea();
			
			anchorPaneForMany.getChildren().add(bar);
			
			
			

		} else {

			setModel();

			beginDrawingImages();

			folderTreeModel.setManyViewController(this);

		}

	}
	
	public void setupScrollingArea() {
		anchorPaneForMany.getChildren().clear();
		
		drawPane.getChildren().clear();
		
		scrollPane.prefViewportWidthProperty().bind(
				anchorPaneForMany.widthProperty());
		scrollPane.prefViewportHeightProperty().bind(
				anchorPaneForMany.heightProperty());
		
		
		
		
		
		scrollPane.setContent(drawPane);
		
		anchorPaneForMany.getChildren().add(scrollPane);
		
	}
	
	public void addImageDuringLoading(OneImage image) {
		
		System.out.println("**********************++ " + image.getThumbnailImage().getImage().getHeight());
		
		drawPane.getChildren().add(image.getThumbnailImage());
		
		
	}


	public void beginDrawingImages() {

		anchorPaneForMany.getChildren().clear();

		

		scrollPane.setContent(drawImages());

		scrollPane.prefViewportWidthProperty().bind(
				anchorPaneForMany.widthProperty());
		scrollPane.prefViewportHeightProperty().bind(
				anchorPaneForMany.heightProperty());

		anchorPaneForMany.getChildren().add(scrollPane);

	}

	
	private FlowPane drawImages() {
		
		
		drawPane.getChildren().clear();

		drawPane.setPadding(new Insets(5, 0, 5, 0));
		drawPane.setVgap(4);
		drawPane.setHgap(4);

		drawPane.prefWrapLengthProperty().bind(
				anchorPaneForMany.widthProperty());
		ArrayList<OneImage> imagesToBeDisplayed;

		if (filterImages) { 

			imagesToBeDisplayed = filteredImages;

		} else {
			imagesToBeDisplayed = folderTreeModel
					.getImagesInThisFolderAndDown();

		}

		for (OneImage image : imagesToBeDisplayed) {

			if (currentZooom == ZoomLevel.SMALL) {
				drawPane.getChildren().add(image.getThumbnailImage());
			} else if (currentZooom == ZoomLevel.MEDIUM) {
				drawPane.getChildren().add(image.getMediumImage());
			}
			

		}

		return drawPane;
		
		
	}

	
	
	

	private void openFileChooser() {

		// TODO: hvordan få til importere directories?

		ArrayList<String> fileTypes = new ArrayList<>();
		fileTypes.add("*.jpg");
		fileTypes.add("*.jpeg");
		fileTypes.add("*.png");
		fileTypes.add("*.bmp");

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Import images");
		fileChooser.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("All Images", fileTypes));

		List<File> fileList = fileChooser.showOpenMultipleDialog(main
				.getPrimaryStage());

		if (fileList != null) {
			for (File file : fileList) {

				ServerCommHandler.SendImageToServer(file, folderTreeModel.getCurrentFolder().getFolderId());

			}
		}

		

	}

	public void setMainController(Main mainController) {
		this.main = mainController;
		currentZooom = ZoomLevel.SMALL;
	}

	private void setModel() {

		updateFolderTree();

		zoomSlider.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable,
					Number oldValue, Number newValue) {

				if (newValue.intValue() == 1) {
					currentZooom = ZoomLevel.MEDIUM;

					beginDrawingImages();
				} else if (newValue.intValue() == 0) {
					currentZooom = ZoomLevel.SMALL;

					beginDrawingImages();
				}

			}
		});

	}

	public void setFolderTreeModel(FolderTree folderTreeModel) {
		this.folderTreeModel = folderTreeModel;
	}

}
