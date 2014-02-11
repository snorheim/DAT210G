package logic;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageRotate {

	
	
	BufferedImage img ;

	try {
	    img = ImageIO.read(new File("strawberry.jpg"));
	} catch (IOException e) {
	}
	
	
	
	// This code assumes the existance of a buffered image called 'image' 
	
	// The required drawing location
	int drawLocationX = 300;
	int drawLocationY = 300;

	// Rotation information

	
	double rotationRequired = Math.toRadians(45);
	double locationX = image.getWidth() / 2;
	double locationY = image.getHeight() / 2;
	AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
	AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);

	// Drawing the rotated image at the required drawing locations
	g2d.drawImage(op.filter(image, null), drawLocationX, drawLocationY, null);
	
	
	
	
	
	/**
	 * Rotates an image. Actually rotates a new copy of the image.
	 * 
	 * @param img The image to be rotated
	 * @param angle The angle in degrees
	 * @return The rotated image
	 */
	public static Image rotate(Image img, double angle){
	    double sin = Math.abs(Math.sin(Math.toRadians(angle))),
	           cos = Math.abs(Math.cos(Math.toRadians(angle)));

	    int w = img.getWidth(null), h = img.getHeight(null);

	    int neww = (int) Math.floor(w*cos + h*sin),
	        newh = (int) Math.floor(h*cos + w*sin);

	    BufferedImage bimg = toBufferedImage(getEmptyImage(neww, newh));
	    Graphics2D g = bimg.createGraphics();

	    g.translate((neww-w)/2, (newh-h)/2);
	    g.rotate(Math.toRadians(angle), w/2, h/2);
	    g.drawRenderedImage(toBufferedImage(img), null);
	    g.dispose();

	    return toImage(bimg);
	}
	
	
}

