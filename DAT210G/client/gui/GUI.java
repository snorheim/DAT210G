package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class GUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel singleImageSidebar;
	private JPanel multiImageSidebar;
	private JPanel imageViewPanel;
	private JScrollPane scroll;

	public GUI() {
		this.setSize(800, 600);
		this.setTitle("GUI Prototyp");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		this.setLayout(new BorderLayout());

		singleImageSidebar = new JPanel();
		multiImageSidebar = new JPanel();
		imageViewPanel = new JPanel();

		initImageViewPanel();
		initSingleImageSidebar();
		initMultiImageSidebar();
		
		scroll = new JScrollPane(imageViewPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		
		this.add(multiImageSidebar, BorderLayout.WEST);
		this.add(scroll, BorderLayout.CENTER);

		this.setVisible(true);

	}

	private void initImageViewPanel() {
		
		imageViewPanel.setBounds(0, 0, 200, 2000);  
		imageViewPanel.setPreferredSize(new Dimension(200, 2000));  
	}
	
	private void initSingleImageSidebar() {
		singleImageSidebar.setLayout(new BoxLayout(singleImageSidebar, BoxLayout.PAGE_AXIS));
		
		singleImageSidebar.add(new JButton("Next"));
		singleImageSidebar.add(new JButton("Previous"));
		singleImageSidebar.add(new JButton("Rotate clockwise"));
		singleImageSidebar.add(new JButton("Rotate anticlockwise"));
		singleImageSidebar.add(new JButton("Edit Metadata"));
	}
	
	private void initMultiImageSidebar() {
		multiImageSidebar.setLayout(new BoxLayout(multiImageSidebar, BoxLayout.PAGE_AXIS));

		multiImageSidebar.add(new JTextField("Rating"));
		multiImageSidebar.add(new JTextField("Name"));
		multiImageSidebar.add(new JTextField("Tags"));
		multiImageSidebar.add(new JTextField("Date"));
		multiImageSidebar.add(new JTextField("Etc"));
		multiImageSidebar.add(new JButton("Search"));
		multiImageSidebar.add(new JButton("Import"));
	}

	public void drawImagesFromList(ArrayList<JPanel> imageList) {
		for (JPanel imagePanel : imageList) {			
			imageViewPanel.add(imagePanel);			
		}
		imageViewPanel.repaint();
	}
	
}
