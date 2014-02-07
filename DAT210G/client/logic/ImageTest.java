package logic;

import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import communication.JsonClient;

public class ImageTest {

	public static void main(String[] args) {

		RequestClient request1 = new RequestClient("addTag", 12, "tagsforlikes");
		JsonClient json = new JsonClient(request1);
		if (json.sendJsonToServer()){
			ResponseClient response1 = json.receiveJsonFromServer();

			if (response1.getSuccess()){				
				BufferedImage image = json.receiveImageFromServer();
			}
			json.closeHttpConnection();
		}
	}
}

