package gui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Ronnie on 14.02.14.
 */
public class ImageAreaPanel extends JPanel {

	private JPanel thumbnailsPanel;
	private JPanel singleImagePanel;
	private Controller controller;

	private OneImage currentSingleImageId; // Image shown if in singleImageMode

	public ImageAreaPanel(JPanel thumbnailsPanel) {
		setBackground(Color.DARK_GRAY);

		this.thumbnailsPanel = thumbnailsPanel;

		addThumbnailsPanel();
		addSingleImagePanel();

	}

	private void addThumbnailsPanel() {

		add(thumbnailsPanel);

	}

	private void addSingleImagePanel() {

		singleImagePanel = new JPanel();
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
		singleImagePanel.add(currentSingleImageId);

		revalidate();
	}

}
