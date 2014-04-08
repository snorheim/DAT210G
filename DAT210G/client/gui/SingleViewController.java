package gui;

import org.controlsfx.control.Rating;
import org.controlsfx.dialog.Dialogs;

import gui.model.FolderTree;
import gui.model.OneImage;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class SingleViewController {

	private Main mainController;
	private FolderTree folderTreeModel;

	@FXML
	private AnchorPane anchorPaneForSingle;
	@FXML
	private Button homeBtn;
	@FXML
	private Button nextBtn;
	@FXML
	private Button prevBtn;
	@FXML
	private Button rotLeftBtn;
	@FXML
	private Button rotRightBtn;
	@FXML
	private Button storeMetaBtn;
	@FXML
	private TextField titleTextField;
	@FXML
	private TextField descTextField;
	
	@FXML
	private TextField dateTextField;
	@FXML
	private TextField tagsTextField;
	@FXML
	private Label currentFolder;
	@FXML
	private HBox ratingHBox;
	@FXML
	private Button addTagBtn;
	
	private Rating ratingStars;

	private ImageView imageToDisplay;

	private OneImage currentImage;
	
	private boolean hasFullImageInMemory = false;
	
	
	

	public void showScaledToScreenImage() {
		
		
		
		
		anchorPaneForSingle.getChildren().clear();
		
		
				

		currentImage = folderTreeModel.getCurrentImage();
		
		

		if (!hasFullImageInMemory) {
			imageToDisplay = currentImage.getFullImage();
			hasFullImageInMemory = true;
			System.out.println("ImageView imageToDisplay er nå: " + currentImage.getImageId());
		}

		imageToDisplay.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {

				showFullImage();

			}
		});

		imageToDisplay.fitWidthProperty().bind(
				anchorPaneForSingle.widthProperty());
		imageToDisplay.fitHeightProperty().bind(
				anchorPaneForSingle.heightProperty());
		imageToDisplay.setPreserveRatio(true);

		
		
		
		anchorPaneForSingle.getChildren().add(imageToDisplay);

		updateMetaFields();

		currentFolder.setText(folderTreeModel.getCurrentFolder()
				.getFolderName());
	}
	
	private void makeRatingStars() {
		
	
		
		ratingStars = new Rating(5);
		
		ratingStars.setId("ratingStars");
		
		
		
       
        
        
        ratingStars.ratingProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
                System.out.println("Rating = " + t1);
            }
        });
		
		ratingHBox.getChildren().addAll(ratingStars);
		
		
		
		
	}

	private void showFullImage() {

		ScrollPane scrollPane = new ScrollPane();

		anchorPaneForSingle.getChildren().clear();

		imageToDisplay.fitWidthProperty().unbind();
		imageToDisplay.fitHeightProperty().unbind();

		imageToDisplay.fitWidthProperty().set(0);
		imageToDisplay.fitHeightProperty().set(0);

		imageToDisplay.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {

				showScaledToScreenImage();

			}
		});

		scrollPane.setContent(imageToDisplay);

		scrollPane.prefViewportWidthProperty().bind(
				anchorPaneForSingle.widthProperty());
		scrollPane.prefViewportHeightProperty().bind(
				anchorPaneForSingle.heightProperty());

		anchorPaneForSingle.getChildren().add(scrollPane);

	}

	private void updateMetaFields() {

		titleTextField.setText(currentImage.getTitleMeta());
		descTextField.setText(currentImage.getDescMeta());
		ratingStars.setRating(Double.parseDouble(currentImage.getRatingMeta()));
		dateTextField.setText(currentImage.getDateMeta());
		tagsTextField.setText(currentImage.getTagsMeta());

	}

	@FXML
	private void homeBtnAction() {

		hasFullImageInMemory = false;
		mainController.setManyMode(false);

	}

	public void setMainController(Main mainController) {
		this.mainController = mainController;
		makeRatingStars();
	}

	@FXML
	private void nextBtnAction() {

		hasFullImageInMemory = false;
		
		folderTreeModel.getNextImageInImageList(currentImage);

		imageToDisplay = null;

		showScaledToScreenImage();

	}

	@FXML
	private void prevBtnAction() {
		
		hasFullImageInMemory = false;

		folderTreeModel.getPrevImageInImageList(currentImage);

		imageToDisplay = null;

		showScaledToScreenImage();

	}

	@FXML
	private void rotRightBtnAction() {
		
		hasFullImageInMemory = false;

		imageToDisplay = folderTreeModel.getCurrentImage().getRotRight();

		showScaledToScreenImage();

	}

	@FXML
	private void rotLeftBtnAction() {
		
		hasFullImageInMemory = false;

		imageToDisplay = folderTreeModel.getCurrentImage().getRotLeft();

		showScaledToScreenImage();

	}

	@FXML
	private void storeMetaBtnAction() {

		titleTextFieldAction();
		descTextFieldAction();
		ratingTextFieldAction();
		

	}

	

	private void ratingTextFieldAction() {
		
		int rating = (int) ratingStars.getRating();
		
		String stringOfRating = String.valueOf(rating);
		
		currentImage.modifyRating(stringOfRating);

	}

	private void descTextFieldAction() {
		currentImage.modifyDesc(descTextField.getText());

	}

	private void titleTextFieldAction() {
		currentImage.modifyTitle(titleTextField.getText());

	}

	public void setModel(FolderTree folderTreeModel) {
		this.folderTreeModel = folderTreeModel;

	}
	
	public void addTagBtnAction() {
		
		
		String tagToAdd = Dialogs.create()
			     .masthead(null)
			      .title("Add a tag")
			 
			      .message( "Enter a tag!")
			      .showTextInput();
		
		
		if (tagToAdd != null) {
		 
			if (!tagToAdd.isEmpty()) {
				currentImage.addTag(tagToAdd);
				updateMetaFields();
				
			}
		}
		
		
	}
	
	public void addTag(String tag) {
		
		currentImage.addTag(tag);
		
	}

}
