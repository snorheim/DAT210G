package gui;

import javafx.application.Platform;

public class MainController {

	private AboutPopupController aboutPopupController;
	private Main main;

	public void changePinkStyle() {

		main.getScene().getStylesheets().remove("view/style.css");

		main.getScene().getStylesheets()
				.add(getClass().getResource("view/pink.css").toExternalForm());

	}

	public void changeDefaultStyle() {

		main.getScene().getStylesheets().remove("view/pink.css");

		main.getScene().getStylesheets()
				.add(getClass().getResource("view/style.css").toExternalForm());

	}

	public void exitProgram() {
		Platform.exit();
	}

	public void setMain(Main main) {
		this.main = main;
	}

	public void showAboutPopup() {

		aboutPopupController = new AboutPopupController();

		aboutPopupController.showPopup();
	}

}
