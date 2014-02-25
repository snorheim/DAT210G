package gui;

import javax.swing.*;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Ronnie on 14.02.14.
 */
public class ThumbnailsPanel extends JPanel {

	private int columns = 3;
	private int rows;
	private int spacing = 5;

	private GridLayout gridLayout;

	public ThumbnailsPanel() {

		setBackground(Color.DARK_GRAY);
	}

	public void showThumbnails(ArrayList<OneImage> thumbnails) {

		if (thumbnails.isEmpty()) {

			add(new JLabel("Error: No Images Loaded."));

		} else {

			rows = (int) Math.ceil(1.0 * thumbnails.size() / columns);
			gridLayout = new GridLayout(rows, columns, spacing, spacing);
			setLayout(gridLayout);

			for (OneImage thumb : thumbnails) {
				add(thumb);
			}

		}

	}

	public void clearThumbnailsPanel() {
		this.removeAll();
		this.revalidate();

	}

}
