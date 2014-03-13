package gui;

import gui.model.Model;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class SingleModeController {

	// Reference to the main application
	private MainController main;
	private Model model;

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
	private TextField ratingTextField;
	@FXML
	private TextField dateTextField;
	@FXML
	private TextField tagsTextField;

	@FXML
	private Label currentImageLabel;

	/**
	 * Is called by the Main class to give a reference back to itself.
	 * 
	 * @param main
	 */
	public void setMain(MainController main, Model model) {
		this.main = main;
		this.model = model;
		displayImage();

	}

	private void displayImage() {		


		ImageView image = model.getCurrentOneImage().getFullImage();


		anchorPaneForSingle.getChildren().add(image);


		updateMetaFields();
	}

	@FXML
	private void homeBtnAction() {
		
		anchorPaneForSingle.getChildren().clear();
		
		main.showThumbnailsMode();
		
	}

	@FXML
	private void nextBtnAction() {


		int nextImage = model.getCurrentImageId() + 1;

		if (nextImage > model.getImageList().size()) {
			nextImage = 1;
		}

		model.setCurrentImageId(nextImage);


		displayImage();

	}

	private void updateMetaFields() {

		currentImageLabel.setText(Integer.toString(model.getCurrentImageId()));		

		titleTextField.setText(model.getCurrentOneImage().getTitleMeta());
		descTextField.setText(model.getCurrentOneImage().getDescMeta());
		ratingTextField.setText(model.getCurrentOneImage().getRatingMeta());
		dateTextField.setText(model.getCurrentOneImage().getDateMeta());
		tagsTextField.setText(model.getCurrentOneImage().getTagsMeta());


	}

	@FXML
	private void prevBtnAction() {


		int prevImage = model.getCurrentImageId() - 1;

		if (prevImage < 1) {
			prevImage = model.getImageList().size();
		}

		model.setCurrentImageId(prevImage);


		displayImage();

	}

	@FXML
	private void rotRightBtnAction() {
		
		ImageView image = model.getCurrentOneImage().getRotRight();


		anchorPaneForSingle.getChildren().add(image);
	}

	@FXML
	private void rotLeftBtnAction() {

		ImageView image = model.getCurrentOneImage().getRotLeft();


		anchorPaneForSingle.getChildren().add(image);
		
	}
	
	@FXML
	private void storeMetaBtnAction() {
		titleTextFieldAction();
		descTextFieldAction();
		ratingTextFieldAction();
		tagsTextFieldAction();
	}

	
	private void titleTextFieldAction() {
		model.getCurrentOneImage().modifyTitle(titleTextField.getText());
	};

	
	private void descTextFieldAction() {
		model.getCurrentOneImage().modifyDesc(descTextField.getText());
	};

	
	private void ratingTextFieldAction() {
		model.getCurrentOneImage().modifyRating(ratingTextField.getText());
	};

	
	private void tagsTextFieldAction() {
		model.getCurrentOneImage().addTag(tagsTextField.getText());
	};


}
