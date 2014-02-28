package gui;





import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import gui.MainController;

public class ThumbnailsModeController {
	
	// Reference to the main application
	private MainController main;
	
	@FXML
	private AnchorPane anchorPaneForThumb;
	  
	private GridPane grid;
	private int gridColumns = 4;
	private int gridRows;	
	private int gridVgap = 10;
	private int gridHgap = 10;
	int imgCounter;
	

	
	
	/**
	  * Is called by the Main class to give a reference back to itself.
	  * 
	  * @param main
	  */
	  public void setMain(MainController main) {
	      this.main = main;
	      makeGridAndDisplayImages();
	      // Add observable list data to the table
	      //personTable.setItems(mainApp.getPersonData());
	      
	  }
	  
	  /**
	   * Called when the user clicks refresh.
	   */
	   @FXML
	   private void handleRefreshBtn() {
	       System.out.println("Clicked refresh");
		   main.updateImageList();	
		   makeGridAndDisplayImages();
	   }
	   
	 
	   
	   private void makeGridAndDisplayImages() {

		   gridRows = (int) Math.ceil(1.0 * main.getImageList().size() / gridColumns);
		   
		   grid = new GridPane();
		   grid.setVgap(gridVgap);
		   grid.setHgap(gridHgap);
		  
		   System.out.println("grid rows: " + gridRows);
		   System.out.println("grid columns: "  + gridColumns);
		   
		   imgCounter = 0;
		   
		   for (int i = 0; i < gridRows; i++) {
			   
			   for (int j = 0; j < gridColumns; j++) {
				   
				   ImageView tempImage = main.getImageList().get(imgCounter).getImageView();
				   				   
				   
				   
				   grid.add(tempImage, j, i);
				   imgCounter++;
			   }
			   
		   }
		   		   		   
		   
		   anchorPaneForThumb.getChildren().add(grid);
		   
	   }
}
