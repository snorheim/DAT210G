package logic;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class ImageManipulate {

	public BufferedImage RotateImage90Degrees(BufferedImage imageFile) {
		int H =imageFile.getHeight();
		int W =imageFile.getWidth();
		
		BufferedImage orginal = imageFile;
		
		BufferedImage Rotert = new BufferedImage(H, W, BufferedImage.TYPE_INT_RGB);
		
		double theta = Math.PI / 2;
	
		//bildeposisjon
		AffineTransform xform = AffineTransform.getRotateInstance(theta, W / 2, W / 2);
		
		Graphics2D g = (Graphics2D) Rotert.createGraphics();
		g.drawImage(orginal, xform, null);
		g.dispose();
	
		return Rotert;
	
	}
	
	public static void main(String[] args) {
		// Test
		
	}

}
