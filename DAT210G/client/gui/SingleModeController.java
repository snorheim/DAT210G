package gui;

import java.awt.image.BufferedImage;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
		BufferedImage bufImage = main.getServerCommHandler().getLargeImage(
				main.getCurrentImageId());
		Image image = SwingFXUtils.toFXImage(bufImage, null);
		ImageView imageView = new ImageView(image);
		anchorPaneForSingle.getChildren().add(imageView);
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
			nextImage = 0;
		}
		
		main.setCurrentImageId(nextImage);
		
		
		displayImage();
		
	}
	
	@FXML
	private void prevBtnAction() {
		
		int currentImage = main.getCurrentImageId();
		int numOfImages = main.getImageList().size();
				
		
		int nextImage = currentImage - 1;
		
		if (nextImage < 0) {
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
	

}
