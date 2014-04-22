package gui;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.controlsfx.control.Rating;
import org.controlsfx.dialog.Dialogs;

import gui.model.FolderTree;
import gui.model.OneImage;
import gui.model.ServerCommHandler;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
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
	@FXML
	private HBox ratingHBox;

	private Rating ratingStars;

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

	private void makeRatingStars() {

		ratingHBox.getChildren().clear();

		ratingStars = new Rating(5);

		ratingStars.setRating(0);

		ratingStars.setId("ratingStars");

		ratingStars.ratingProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> ov, Number t,
					Number t1) {

				int rating = t1.intValue();

				ratingSearchAl(rating);

			}
		});

		ratingHBox.getChildren().addAll(ratingStars);

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

		disableNodes(titleTextField);

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

		enableNodes(titleTextField);

	}

	private void ratingSearchAl(int ratingNum) {

		disableNodes(ratingStars);

		String rating = String.valueOf(ratingNum);

		if (!rating.isEmpty()) {

			int[] pictureIds = null;

			pictureIds = ServerCommHandler.searchRatingPictures(rating,
					FolderTree.getCurrentFolder().getFolderId());

			findImagesMatchingFilter(pictureIds);

		} else {
			beginDrawingImages();
			updateFolderTree();

		}

		enableNodes(ratingStars);

	}
	
	

	@FXML
	private void descSearchAl() {

		disableNodes(descTextField);

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

		enableNodes(descTextField);

	}

	@FXML
	private void dateSearchAl() {
		disableNodes(searchByDateButton);

		int[] pictureIds = null;
		if (datePickerFromDate.getValue() != null
				&& datePickerToDate.getValue() != null) {
			String fromDate = datePickerFromDate.getValue().toString();
			String toDate = datePickerToDate.getValue().toString();
			String dateValues = fromDate + ";" + toDate;
			pictureIds = ServerCommHandler.searchDateTimePictures(dateValues,
					FolderTree.getCurrentFolder().getFolderId());
			findImagesMatchingFilter(pictureIds);
		} else {
			beginDrawingImages();
			updateFolderTree();
		}

		enableNodes(searchByDateButton);
	}

	@FXML
	private void tagSearchAl() {

		disableNodes(tagsTextField);

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

		enableNodes(tagsTextField);

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

			setupScrollingArea();

			stackPane.getChildren().addAll(scrollPane, bar);
			makeRatingStars();

		} else {
			dateFromField.getChildren().clear();
			dateToField.getChildren().clear();

			datePickerFromDate = new DatePicker(LocalDate.now().minusDays(1));
			dateFromField.getChildren().add(datePickerFromDate);
			datePickerToDate = new DatePicker(LocalDate.now());
			dateToField.getChildren().add(datePickerToDate);
			CalendarSettings calendarSettings = new CalendarSettings(
					datePickerFromDate, datePickerToDate);
			calendarSettings.configFromDatePicker();
			calendarSettings.configToDatePicker();

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

	// De to neste metodene trengs kanskje ikke
	private void disableNodes(Node doNotDisableThis) {

		if (!titleTextField.equals(doNotDisableThis)) {
			titleTextField.clear();
			titleTextField.setDisable(true);
		}
		if (!ratingStars.equals(doNotDisableThis)) {
			ratingStars.setRating(0);
			ratingStars.setDisable(true);
		}
		if (!descTextField.equals(doNotDisableThis)) {
			descTextField.clear();
			descTextField.setDisable(true);
		}
		if (!tagsTextField.equals(doNotDisableThis)) {
			tagsTextField.clear();
			tagsTextField.setDisable(true);
		}
		if (!searchByDateButton.equals(doNotDisableThis)) {
			searchByDateButton.setDisable(true);
		}

	}

	private void enableNodes(Node doNotDisableThis) {

		if (!titleTextField.equals(doNotDisableThis))
			titleTextField.setDisable(false);

		if (!ratingStars.equals(doNotDisableThis))
			ratingStars.setDisable(false);

		if (!descTextField.equals(doNotDisableThis))
			descTextField.setDisable(false);

		if (!tagsTextField.equals(doNotDisableThis))
			tagsTextField.setDisable(false);

		if (!searchByDateButton.equals(doNotDisableThis))
			searchByDateButton.setDisable(false);
	}
	
	@FXML
	private void clearSearch() {
		
		ratingStars.setRating(0);
		titleTextField.clear();
		descTextField.clear();
		tagsTextField.clear();
		main.setManyMode(false);
		
	}

}
