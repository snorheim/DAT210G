package gui;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JPanel;

public class ImageView extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public ImageView() {
		
		this.setBounds(0, 0, 200, 2000);  
		this.setPreferredSize(new Dimension(200, 2000));
		
	}
	
	public ImageView(ArrayList<JPanel> imageList) {
		
		this();
		
		for(JPanel image : imageList) {
			this.add(image);
		}
		
	}
	
	public void drawImages(ArrayList<SingleImage> imageList) {
		
		for (SingleImage image : imageList) {
			this.add(image);
		}
		
	}

}
