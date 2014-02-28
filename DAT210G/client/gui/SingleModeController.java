package gui;

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
	public void setMain(MainController main) {
		this.main = main;
		displayImage();

	}

	private void displayImage() {		
		anchorPaneForSingle.getChildren().clear();
		Image image = main.getServerCommHandler().getLargeImage(
				main.getCurrentImageId());
		
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
		int currentImage = main.getCurrentImageId();
		int numOfImages = main.getImageList().size();
				
		
		int nextImage = currentImage + 1;
		
		if (nextImage > numOfImages) {
			nextImage = 1;
		}
		
		main.setCurrentImageId(nextImage);
		
		
		displayImage();
		
	}
	
	private void updateMetaFields() {
		
		currentImageLabel.setText(Integer.toString(main.getCurrentImageId()));
		
		String[] meta = main.getServerCommHandler().getMetaData(main.getCurrentImageId());
		
		System.out.println(meta[0] + " " + meta[1] + " " + meta[2] + " " + meta[3] + " " + meta[4] );
		
		titleTextField.setText(meta[0]);
		descTextField.setText(meta[1]);
		ratingTextField.setText(meta[2]);
		dateTextField.setText(meta[3]);
		tagsTextField.setText(meta[4]);
		
	
	}
	
	@FXML
	private void prevBtnAction() {
		
		int currentImage = main.getCurrentImageId();
		int numOfImages = main.getImageList().size();
				
		
		int nextImage = currentImage - 1;
		
		if (nextImage < 1) {
			nextImage = numOfImages;
		}
		
		main.setCurrentImageId(nextImage);
		
		
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
