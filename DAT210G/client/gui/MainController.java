package gui;

import org.controlsfx.dialog.Dialog;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class MainController {

	private Main main;

	public void changePinkStyle() {

		// main.getScene().getStylesheets().remove("view/style.css");

		main.getScene().getStylesheets()
				.add(getClass().getResource("view/pink.css").toExternalForm());

	}

	public void changeDefaultStyle() {

		main.getScene().getStylesheets().remove("view/pink.css");

		main.getScene().getStylesheets().clear();

		// main.getScene().getStylesheets()
		// .add(getClass().getResource("view/style.css").toExternalForm());

	}

	public void exitProgram() {
		Platform.exit();
	}

	public void setMain(Main main) {
		this.main = main;
	}

	public void showAboutPopup() {

		BorderPane aboutPane = new BorderPane();

		String url = "<iframe width=\"500\" height=\"500\" src=\"http://www.youtube.com/embed/oHg5SJYRHA0\" frameborder=\"0\" allowfullscreen></iframe>";

		WebView browser = new WebView();
		WebEngine webEngine = browser.getEngine();
		webEngine.loadContent(url);

		aboutPane.setCenter(browser);

		Label aboutText = new Label(
				"Authors: Christian Magnus, John Kåre Seldal Jansen, Kjell Arne Hellum, Ronnie Bjørkelund, Ronny Wathne, Simon Jespersen, Sondre Norheim");

		aboutPane.setBottom(aboutText);

		Dialog dialog = new Dialog(null, "About");
		dialog.setResizable(false);
		dialog.setIconifiable(false);

		dialog.setContent(aboutPane);

		dialog.show();

	}

}
