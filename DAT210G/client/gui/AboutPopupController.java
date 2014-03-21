package gui;


import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class AboutPopupController {




	@FXML
	private AnchorPane aboutAnchorPane;
	
	

	




	public void showPopup() {

		


		try {

			FXMLLoader loader = new FXMLLoader(
					MainController.class
					.getResource("view/AboutPopup.fxml"));

			
			aboutAnchorPane = (AnchorPane) loader.load();
			
			Scene popupScene = new Scene(aboutAnchorPane, 550, 580);
			popupScene.getStylesheets().add(MainController.class.getResource("view/style.css").toExternalForm());
			Stage popupStage = new Stage();
			popupStage.setScene(popupScene);
			popupStage.show();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String url = "<iframe width=\"500\" height=\"500\" src=\"http://www.youtube.com/embed/oHg5SJYRHA0\" frameborder=\"0\" allowfullscreen></iframe>";

		WebView browser = new WebView();
		WebEngine webEngine = browser.getEngine();
		webEngine.loadContent(url);
		
		
		BorderPane borderPane = (BorderPane) aboutAnchorPane.getChildren().get(0);
		
		borderPane.setCenter(browser);

		
		 
	}



}
