package gui;

import java.awt.Dimension;
import javax.swing.JPanel;

public class ImageView extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public ImageView() {
		
		  
		this.setPreferredSize(new Dimension(600, 6000));
		
	}
	
	
	public void addSingleImage(SingleImage singleImageJPanel) {
		
		this.add(singleImageJPanel);
	}
	
	public void clearView() {
		this.removeAll();
	}

}
