package gui;

import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class SingleImagePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	
	public SingleImagePanel(JLabel image) {
		this.name = image.getText();
		
		this.add(image);
	}

	public void addImageListener(MouseListener listenForImageClick){
		
		this.addMouseListener(listenForImageClick);
		
	}
	
	public String getImageName() {
		return name;
	}
	
}
