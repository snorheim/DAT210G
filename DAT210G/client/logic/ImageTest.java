package logic;

import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import communication.JsonClient;

public class ImageTest {

	public static void main(String[] args) {
		BufferedImage image = null;
		String path = "c:\\dev\\image.jpeg";
		try {
			image = ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}

		JsonClient json = new JsonClient(new RequestClient("addNewImage"));
		if (json.sendJsonToServer()){
			json.sendImageToServer(image, "JPEG");
			json.closeHttpConnection();
		}
		
		
	}
}

