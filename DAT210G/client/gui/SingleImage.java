package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class SingleImage extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int imageID;
	private BufferedImage image;
	private Dimension panelSize;
	
	public SingleImage(BufferedImage image, int imageID) {
		
		this.image = image;
		this.imageID = imageID;
		this.panelSize = new Dimension(image.getWidth(), image.getHeight());
		this.setMinimumSize(panelSize);
		this.setPreferredSize(panelSize);
	}
	
	public void paint (Graphics graphics) {
		
		Graphics2D g = (Graphics2D) graphics;
		
		g.setFont(new Font("Arial", Font.PLAIN, 24));
		g.setColor(Color.RED);
		
		g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
		g.drawString(Integer.toString(imageID), image.getWidth()/2, image.getHeight()/2);
	}

	public void addImageListener(MouseListener listenForImageClick){
		
		this.addMouseListener(listenForImageClick);
		
	}
	
	public int getImageID() {
		return imageID;
	}
	
	public BufferedImage getImage() {
		return image;
	}
	
	
}
