package gui;

import gui.model.FolderTree;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class SingleViewController {
	
	private MainController mainController;
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
	private TextField ratingTextField;
	@FXML
	private TextField dateTextField;
	@FXML
	private TextField tagsTextField;
	@FXML
	private Label currentImageLabel;
	
	
	public void displayImage() {		


		ImageView image = folderTreeModel.getFullImage(folderTreeModel.getCurrentImage());


		anchorPaneForSingle.getChildren().add(image);


		//updateMetaFields();
	}
	
	

	
	@FXML
	private void homeBtnAction() {
		
		mainController.setManyMode();
		System.out.println("clicked home button");
	}

	public void setMainController(MainController mainController) {
		this.mainController = mainController;
	}
	
	@FXML
	private void nextBtnAction() {

		/*
		int nextImage = model.getCurrentImageId() + 1;

		if (nextImage > model.getImageList().size()) {
			nextImage = 1;
		}

		model.setCurrentImageId(nextImage);


		displayImage();
		*/
		System.out.println("clicked next button");
	}
	
	@FXML
	private void prevBtnAction() {

		/*
		int prevImage = model.getCurrentImageId() - 1;

		if (prevImage < 1) {
			prevImage = model.getImageList().size();
		}

		model.setCurrentImageId(prevImage);


		displayImage();
		*/
		System.out.println("clicked prev button");

	}

	@FXML
	private void rotRightBtnAction() {
		/*
		ImageView image = model.getCurrentOneImage().getRotRight();


		anchorPaneForSingle.getChildren().add(image);
		*/
		System.out.println("clicked rot right button");
	}

	@FXML
	private void rotLeftBtnAction() {
		/*
		ImageView image = model.getCurrentOneImage().getRotLeft();


		anchorPaneForSingle.getChildren().add(image);
		*/
		System.out.println("clicked rot left button");
		
	}
	
	@FXML
	private void storeMetaBtnAction() {
		/*
		titleTextFieldAction();
		descTextFieldAction();
		ratingTextFieldAction();
		tagsTextFieldAction();
		*/
		System.out.println("clicked apply button");
	}







	
	public void setModel(FolderTree folderTreeModel) {
		this.folderTreeModel = folderTreeModel;
		
	}
	
	
}
