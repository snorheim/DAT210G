package gui;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.controlsfx.dialog.Dialogs;

import gui.model.FolderTree;
import gui.model.OneImage;
import gui.model.ServerCommHandler;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.util.Callback;

public class ManyViewController {

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
	private HBox dateFromField;
	@FXML
	private HBox dateToField;
	@FXML
	private Button searchByDateButton;
	@FXML
	private TextField tagsTextField;
	@FXML
	private Slider zoomSlider;
	@FXML
	private HBox hboxForTree;

	private boolean filterImages = false;
	private ArrayList<OneImage> filteredImages;

	private ScrollPane scrollPane;
	private StackPane stackPane;
	private FlowPane drawPane;

	private ZoomLevel currentZooom;

	private DatePicker datePickerFromDate;
	private DatePicker datePickerToDate;

	private enum ZoomLevel {
		SMALL, MEDIUM
	}

	public ManyViewController() {

		scrollPane = new ScrollPane();
		drawPane = new FlowPane();
		stackPane = new StackPane();

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

				ServerCommHandler.SendImageToServer(file, FolderTree
						.getCurrentFolder().getFolderId());

			}

			FolderTree.update();

			start();

		}

	}

	@FXML
	private void handleNewDirectoryBtn() {

		String newDirectoryName = Dialogs.create().masthead(null)
				.title("Add a directory")

				.message("Enter name of new directory!").showTextInput();

		if (newDirectoryName != null) {

			if (!newDirectoryName.isEmpty()) {
				ServerCommHandler.sendNewDirReqToServer(newDirectoryName,
						FolderTree.getCurrentFolder().getFolderId());

				FolderTree.update();
				start();

			}
		}

	}

	private void findImagesMatchingFilter(int[] pictureIds) {
		filteredImages = new ArrayList<>();

		for (int i : pictureIds) {

			for (OneImage image : FolderTree.getAllImagesList()) {
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
				pictureIds = ServerCommHandler.searchTilePictures(titleText,
						FolderTree.getCurrentFolder().getFolderId());
			}

			findImagesMatchingFilter(pictureIds);

		} else {
			beginDrawingImages();
			updateFolderTree();
		}

	}

	@FXML
	private void ratingSearchAl() {
		// TODO: sjekke input
		String rating = ratingTextField.getText();

		if (!rating.isEmpty()) {

			int ratingCheck = Integer.parseInt(rating);
			int[] pictureIds = null;
			if (ratingCheck > 0 && ratingCheck < 6) {
				pictureIds = ServerCommHandler.searchRatingPictures(rating,
						FolderTree.getCurrentFolder().getFolderId());
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
				pictureIds = ServerCommHandler.searchDescriptionPictures(
						description, FolderTree.getCurrentFolder()
						.getFolderId());
			}
			findImagesMatchingFilter(pictureIds);

		} else {
			beginDrawingImages();
			updateFolderTree();
		}

	}



	@FXML
	private void dateSearchAl() {
		int[] pictureIds = null;
		if (datePickerFromDate.getValue() != null && datePickerToDate.getValue() != null) {
			String fromDate = datePickerFromDate.getValue().toString();
			String toDate = datePickerToDate.getValue().toString();
			String dateValues = fromDate + ";" + toDate;
			pictureIds = ServerCommHandler.searchDateTimePictures(dateValues, FolderTree.getCurrentFolder().getFolderId());
		} else {
			beginDrawingImages();
			updateFolderTree();
		}
	}

	@FXML
	private void tagSearchAl() {
		String tags = tagsTextField.getText().toLowerCase();
		if (!tags.isEmpty()) {
			int[] pictureIds = null;
			if (!tags.equals("")) {
				pictureIds = ServerCommHandler.searchTagsPictures(tags,
						FolderTree.getCurrentFolder().getFolderId());
			}

			findImagesMatchingFilter(pictureIds);

		} else {
			beginDrawingImages();
			updateFolderTree();
		}

	}

	public void updateFolderTree() {

		hboxForTree.getChildren().clear();

		hboxForTree.getChildren().add(FolderTree.getTree());

	}

	public void start() {


		if (!FolderTree.isReady()) {

			ProgressIndicator bar = new ProgressIndicator(0);
			bar.setMaxSize(50, 50);
			bar.progressProperty()
			.bind(FolderTree.getTask().progressProperty());

			datePickerFromDate = new DatePicker(LocalDate.now().minusDays(1));
			dateFromField.getChildren().add(datePickerFromDate);

			datePickerToDate = new DatePicker(LocalDate.now());
			dateToField.getChildren().add(datePickerToDate);

			CalendarSettings calendarSettings = new CalendarSettings(datePickerFromDate, datePickerToDate);
			calendarSettings.configFromDatePicker();
			calendarSettings.configToDatePicker();

			setupScrollingArea();

			stackPane.getChildren().addAll(scrollPane, bar);

		} else {

			setModel();

			beginDrawingImages();

			FolderTree.setManyViewController(this);

		}

	}

	public void setupScrollingArea() {
		anchorPaneForMany.getChildren().clear();

		drawPane.getChildren().clear();

		scrollPane.prefViewportWidthProperty().bind(
				anchorPaneForMany.widthProperty());
		scrollPane.prefViewportHeightProperty().bind(
				anchorPaneForMany.heightProperty());

		drawPane.setEffect(new GaussianBlur());

		scrollPane.setContent(drawPane);

		anchorPaneForMany.getChildren().add(stackPane);

	}

	public void addImageDuringLoading(OneImage image) {

		drawPane.setPadding(new Insets(5, 0, 5, 0));
		drawPane.setVgap(4);
		drawPane.setHgap(4);

		drawPane.prefWrapLengthProperty().bind(
				anchorPaneForMany.widthProperty());

		if (currentZooom == ZoomLevel.SMALL) {
			drawPane.getChildren().add(
					image.getThumbnailImageWithoutMouseHandler());
		} else if (currentZooom == ZoomLevel.MEDIUM) {
			drawPane.getChildren().add(
					image.getMediumImageWithoutMouseHandler());
		}

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
		drawPane.setEffect(null);

		drawPane.setPadding(new Insets(5, 0, 5, 0));
		drawPane.setVgap(4);
		drawPane.setHgap(4);


		drawPane.prefWrapLengthProperty().bind(
				anchorPaneForMany.widthProperty());
		ArrayList<OneImage> imagesToBeDisplayed;

		if (filterImages) {
			imagesToBeDisplayed = filteredImages;

		} else {
			imagesToBeDisplayed = FolderTree.getImagesInThisFolderAndDown();

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

}
