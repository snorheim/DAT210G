package logic;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class ImageManipulate {
	
	public static BufferedImage RotateImage90DegreesClockwise(BufferedImage imageFile) {
		int imageHeight =imageFile.getHeight();
		int imageWidth =imageFile.getWidth();
		
		BufferedImage orginal = imageFile;
		
		BufferedImage Rotert = new BufferedImage(imageHeight, imageWidth, BufferedImage.TYPE_INT_RGB);
		
		double theta = Math.PI / 2;
	
		AffineTransform coordinateClass = AffineTransform.getRotateInstance(theta, imageHeight / 2, imageHeight / 2);
		
		Graphics2D graphic2DClass = (Graphics2D) Rotert.createGraphics();
		graphic2DClass.drawImage(orginal, coordinateClass, null);
		graphic2DClass.dispose();
	
		return Rotert;
	
	}
	
	public static BufferedImage RotateImage90DegreesCounterClockwise(BufferedImage imageFile) {
		int imageHeight =imageFile.getHeight();
		int imageWidth =imageFile.getWidth();
		
		BufferedImage orginal = imageFile;
		
		BufferedImage Rotert = new BufferedImage(imageHeight, imageWidth, BufferedImage.TYPE_INT_RGB);
		
		double theta = Math.PI / 2*3;
	
		AffineTransform coordinateClass = AffineTransform.getRotateInstance(theta, imageWidth / 2, imageWidth / 2);
		
		Graphics2D graphic2DClass = (Graphics2D) Rotert.createGraphics();
		graphic2DClass.drawImage(orginal, coordinateClass, null);
		graphic2DClass.dispose();
	
		return Rotert;
		
	}

}
