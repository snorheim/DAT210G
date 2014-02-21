package gui;

import javax.swing.*;

import java.awt.*;

/**
 * Created by Ronnie on 14.02.14.
 */
public class ImageAreaPanel extends JPanel {

	private JPanel thumbnailsPanel;
	private SingleImagePanel singleImagePanel;	
	private Controller controller;

	private OneImage currentSingleImageId; // Image shown if in singleImageMode

	public ImageAreaPanel(JPanel thumbnailsPanel) {
		setBackground(Color.DARK_GRAY);

		this.thumbnailsPanel = thumbnailsPanel;
		singleImagePanel = new SingleImagePanel();

		addThumbnailsPanel();
		addSingleImagePanel();

	}

	private void addThumbnailsPanel() {

		add(thumbnailsPanel);

	}

	private void addSingleImagePanel() {		
					
		add(singleImagePanel);

	}

	public void setThumbnailsMode() {
		singleImagePanel.setVisible(false);
		thumbnailsPanel.setVisible(true);
		revalidate();
	}

	public void setSingleImageMode(OneImage image) {
		currentSingleImageId = image;
		thumbnailsPanel.setVisible(false);
		singleImagePanel.setVisible(true);		
		
		singleImagePanel.removeAll();
		singleImagePanel.setImage(image);

		revalidate();
	}

}
