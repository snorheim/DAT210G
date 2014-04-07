package gui;

import gui.model.ServerCommHandler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;



public class NewDirDialog {
	Stage dialogStage;
	TextField dirName;
	int parentId;
	
	public NewDirDialog(int id) {
		parentId = id;
		dirName = new TextField();
		Button confirmButton = new Button("ok");
		dialogStage = new Stage();
		dialogStage.initModality(Modality.WINDOW_MODAL);
		dialogStage.setScene(new Scene(VBoxBuilder.create().
				children(new Text("Directory name:"), dirName, confirmButton).
				alignment(Pos.CENTER).padding(new Insets(5)).build()));
		dialogStage.show();

		confirmButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				if (!(dirName.getText().equals(""))) {
					ServerCommHandler.sendNewDirReqToServer(dirName.getText(), parentId);
				}
				dialogStage.close();
			}
		});
	}
}
