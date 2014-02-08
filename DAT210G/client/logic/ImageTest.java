package logic;

import java.awt.image.BufferedImage;
import communication.JsonClient;

public class ImageTest {

	public static void main(String[] args) {						
		JsonClient json = new JsonClient(new RequestClient("getThumbnail", 12));
		if (json.sendJsonToServer()){
			ResponseClient response1 = json.receiveJsonFromServer();
			System.out.println(response1.toString());
			if (response1.getSuccess()){
				BufferedImage image = json.receiveImageFromServer();
				System.out.println(image.toString());
			}
			json.closeHttpConnection();
		}	
	}
}