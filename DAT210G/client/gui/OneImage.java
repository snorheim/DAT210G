package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

/**
 * Created by Ronnie on 14.02.14.
 */
public class OneImage extends JPanel {

	private int imageId;
	private BufferedImage image;
	private Dimension size;
	private String[] metaData;

	/**
	 * Constructor
	 * 
	 * @param image
	 *            A BufferedImage
	 * @param imageId
	 *            An ID number for image
	 */
	public OneImage(BufferedImage image, int imageId, String[] metaData) {

		this.metaData = metaData;
		this.image = image;
		this.imageId = imageId;
		this.size = new Dimension(image.getWidth(), image.getHeight());
		this.setMinimumSize(size);
		this.setPreferredSize(size);

	}

	public String[] getMetaData() {
		return metaData;
	}

	public void paint(Graphics graphics) {
		Graphics2D g = (Graphics2D) graphics;

//		g.setFont(new Font("Arial", Font.PLAIN, 24));
//		g.setColor(Color.RED);

		g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
//		g.drawString(Integer.toString(imageId), image.getWidth() / 2,
//				image.getHeight() / 2);

//		g.drawString("null", 50, 50);

	}

	public void addImageMouseListener(MouseListener listenForImageClick) {

		this.addMouseListener(listenForImageClick);

	}

	public int getImageId() {
		return imageId;
	}

	public Dimension getSize() {
		return size;
	}
}
