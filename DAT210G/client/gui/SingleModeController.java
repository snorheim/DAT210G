package gui;

import gui.model.Model;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
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
		anchorPaneForSingle.getChildren().clear();
				
		Image image = model.getLargeImage(model.getCurrentImageId());
		
		ImageView imageView = new ImageView(image);
		anchorPaneForSingle.getChildren().add(imageView);
		updateMetaFields();
	}

	@FXML
	private void homeBtnAction() {
		main.showThumbnailsMode();
	}
	
	@FXML
	private void nextBtnAction() {
						
		
		int nextImage = model.getCurrentImageId() + 1;
		
		if (nextImage > model.getImageHashMap().size()) {
			nextImage = 1;
		}
		
		model.setCurrentImageId(nextImage);
		
		
		displayImage();
		
	}
	
	private void updateMetaFields() {
		
		currentImageLabel.setText(Integer.toString(model.getCurrentImageId()));		
		
		titleTextField.setText(model.getTitleMeta(model.getCurrentImageId()));
		descTextField.setText(model.getDescMeta(model.getCurrentImageId()));
		ratingTextField.setText(model.getRatingMeta(model.getCurrentImageId()));
		dateTextField.setText(model.getDateMeta(model.getCurrentImageId()));
		tagsTextField.setText(model.getTagsMeta(model.getCurrentImageId()));
		
	
	}
	
	@FXML
	private void prevBtnAction() {
		
							
		int prevImage = model.getCurrentImageId() - 1;
		
		if (prevImage < 1) {
			prevImage = model.getImageHashMap().size();
		}
		
		model.setCurrentImageId(prevImage);
		
		
		displayImage();
		
	}
	
	@FXML
	private void rotRightBtnAction() {
		
	}
	
	@FXML
	private void rotLeftBtnAction() {
		
	}
	
	@FXML
	private void titleTextFieldAction() {
		
	};
	
	@FXML
	private void descTextFieldAction() {
		
	};
	
	@FXML
	private void ratingTextFieldAction() {
		
	};
	@FXML
	private void dateTextFieldAction() {
		
	};
	@FXML
	private void tagsTextFieldAction() {
		
	};
	

}
