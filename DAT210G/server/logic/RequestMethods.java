package logic;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class RequestMethods {

	public static void getAllImages(RequestServer request, int imageId, String detail){
		
	}
	public static void getThumbnail(RequestServer request, int imageId, String detail){
		request.sendJsonResponse(new ResponseServer(true));
		BufferedImage image = null;
		String path = "c:\\dev\\image.jpeg";
		try {
			image = ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		request.sendImageResponse(image, "JPEG");
	}
	public static void getLargeImage(RequestServer request, int imageId, String detail){

	}
	public static void getImagesWithTag(RequestServer request, int imageId, String detail){

	}
	public static void getTagsStartingWith(RequestServer request, int imageId, String detail){

	}
	public static void addTag(RequestServer request, int imageId, String detail){

	}
	public static void modifyTitle(RequestServer request, int imageId, String detail){

	}
	public static void modifyDescription(RequestServer request, int imageId, String detail){

	}
	public static void rotate90Clock(RequestServer request, int imageId, String detail){

	}
	public static void rotate90CounterClock(RequestServer request, int imageId, String detail){

	}
	public static void addNewImage(RequestServer request, int imageId, String detail){

	}
}
