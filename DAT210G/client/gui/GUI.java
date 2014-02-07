package gui;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

public class GUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Sidebar sidebar;
	private ImageView imageView;
	private JScrollPane scroll;

	public GUI() {
		this.setSize(800, 600);
		this.setTitle("GUI Prototyp");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		this.setLayout(new BorderLayout());

		sidebar = new Sidebar();
		imageView = new ImageView();

		scroll = new JScrollPane(imageView, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		
		this.add(sidebar, BorderLayout.WEST);
		this.add(scroll, BorderLayout.CENTER);

		this.setVisible(true);

	}
	

	public void drawImagesFromList(ArrayList<SingleImage> imageList) {
		imageView.drawImages(imageList);
	}
	
	public void setMultiImageMode() {
		sidebar.setMultiImageMode();
	}
	
	public void setSingleImageMode() {
		sidebar.setSingleImageMode();
	}
	
}
