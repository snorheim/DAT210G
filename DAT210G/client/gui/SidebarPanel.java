package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.*;

/**
 * SidebarPanel
 * 
 * 
 * @author Ronnie
 * 
 */
public class SidebarPanel extends JPanel {

	private JButton homeBtn;
	private JButton nextBtn;
	private JButton prevBtn;
	private JButton rotRightBtn;
	private JButton rotLeftBtn;
	private JButton editMetaBtn;

	private JPanel thumbnailsPanel;
	private JPanel singleImagePanel;

	private JTextField ratingJTextField;
	private JTextField nameJTextField;
	private JTextField tagsJTextField;
	private JTextField dateJTextField;
	private JTextField etcJTextField;
	private JButton searchBtn;
	private JButton importBtn;
	private JButton refreshBtn;
	private OneImage currentSingleImageId; // Image shown if in singleImageMode

	public SidebarPanel() {

		addThumbnailsComponents();
		addSingleImageComponents();

	}

	private void addSingleImageComponents() {

		singleImagePanel = new JPanel();

		singleImagePanel.setLayout(new BoxLayout(singleImagePanel,
				BoxLayout.PAGE_AXIS));

		homeBtn = new JButton("Home");
		nextBtn = new JButton("Next");
		prevBtn = new JButton("Previous");
		rotRightBtn = new JButton("Rotate Right");
		rotLeftBtn = new JButton("Rotate Left");
		editMetaBtn = new JButton("Edit Metadata");

		singleImagePanel.add(homeBtn);
		singleImagePanel.add(nextBtn);
		singleImagePanel.add(prevBtn);
		singleImagePanel.add(rotLeftBtn);
		singleImagePanel.add(rotRightBtn);
		singleImagePanel.add(editMetaBtn);

		add(singleImagePanel);

	}

	private void addThumbnailsComponents() {
		thumbnailsPanel = new JPanel();

		thumbnailsPanel.setLayout(new GridBagLayout());

		ratingJTextField = new JTextField("Rating", 10);
		nameJTextField = new JTextField("Name", 10);
		tagsJTextField = new JTextField("Tags", 10);
		dateJTextField = new JTextField("Date", 10);
		etcJTextField = new JTextField("Etc", 10);
		searchBtn = new JButton("Search");
		importBtn = new JButton("Import");
		refreshBtn = new JButton("Refresh");

		GridBagConstraints a = new GridBagConstraints();
		a.fill = GridBagConstraints.HORIZONTAL;
		a.ipadx = 2;
		a.ipady = 2;
		a.weightx = 0.5;
		a.gridx = 0;
		a.gridy = 0;
		thumbnailsPanel.add(new JLabel("Rating"), a);

		GridBagConstraints b = new GridBagConstraints();
		b.fill = GridBagConstraints.HORIZONTAL;
		b.ipadx = 2;
		b.ipady = 2;
		b.weightx = 0.5;
		b.gridx = 1;
		b.gridy = 0;
		thumbnailsPanel.add(ratingJTextField, b);

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 2;
		c.ipady = 2;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 1;
		thumbnailsPanel.add(new JLabel("Name"), c);

		GridBagConstraints d = new GridBagConstraints();
		d.fill = GridBagConstraints.HORIZONTAL;
		d.ipadx = 2;
		d.ipady = 2;
		d.weightx = 0.5;
		d.gridx = 1;
		d.gridy = 1;
		thumbnailsPanel.add(nameJTextField, d);

		GridBagConstraints e = new GridBagConstraints();
		e.fill = GridBagConstraints.HORIZONTAL;
		e.ipadx = 2;
		e.ipady = 2;
		e.weightx = 0.5;
		e.gridx = 0;
		e.gridy = 2;
		thumbnailsPanel.add(new JLabel("Tags"), e);

		GridBagConstraints f = new GridBagConstraints();
		f.fill = GridBagConstraints.HORIZONTAL;
		f.ipadx = 2;
		f.ipady = 2;
		f.weightx = 0.5;
		f.gridx = 1;
		f.gridy = 2;
		thumbnailsPanel.add(tagsJTextField, f);

		GridBagConstraints g = new GridBagConstraints();
		g.fill = GridBagConstraints.HORIZONTAL;
		g.ipadx = 2;
		g.ipady = 2;
		g.weightx = 0.5;
		g.gridx = 0;
		g.gridy = 3;
		thumbnailsPanel.add(new JLabel("Date"), g);

		GridBagConstraints h = new GridBagConstraints();
		h.fill = GridBagConstraints.HORIZONTAL;
		h.ipadx = 2;
		h.ipady = 2;
		h.weightx = 0.5;
		h.gridx = 1;
		h.gridy = 3;
		thumbnailsPanel.add(dateJTextField, h);

		GridBagConstraints i = new GridBagConstraints();
		i.fill = GridBagConstraints.HORIZONTAL;
		i.ipadx = 2;
		i.ipady = 2;
		i.weightx = 0.5;
		i.gridx = 0;
		i.gridy = 4;
		thumbnailsPanel.add(new JLabel("Etc"), i);

		GridBagConstraints j = new GridBagConstraints();
		j.fill = GridBagConstraints.HORIZONTAL;
		j.ipadx = 2;
		j.ipady = 2;
		j.weightx = 0.5;
		j.gridx = 1;
		j.gridy = 4;
		thumbnailsPanel.add(etcJTextField, j);

		GridBagConstraints k = new GridBagConstraints();
		k.fill = GridBagConstraints.HORIZONTAL;
		k.ipadx = 2;
		k.ipady = 5;
		k.weightx = 1.0;
		k.anchor = GridBagConstraints.PAGE_END;
		k.gridx = 0;
		k.gridy = 5;
		k.gridwidth = 2;
		thumbnailsPanel.add(searchBtn, k);

		GridBagConstraints l = new GridBagConstraints();
		l.fill = GridBagConstraints.HORIZONTAL;
		l.ipadx = 2;
		l.ipady = 5;
		l.weightx = 0.0;
		l.gridx = 0;
		l.gridy = 6;
		l.gridwidth = 2;
		thumbnailsPanel.add(importBtn, l);

		GridBagConstraints m = new GridBagConstraints();
		m.fill = GridBagConstraints.HORIZONTAL;
		m.ipadx = 2;
		m.ipady = 5;
		m.weightx = 0.0;
		m.gridx = 0;
		m.gridy = 7;
		m.gridwidth = 2;
		thumbnailsPanel.add(refreshBtn, m);

		add(thumbnailsPanel);
	}

	/**
	 * Switch to sidebar for single image
	 * 
	 */
	public void setSingleImageMode(OneImage image) {
		currentSingleImageId = image;
		thumbnailsPanel.setVisible(false);
		singleImagePanel.setVisible(true);
		revalidate();
	}

	/**
	 * Switch to sidebar for thumbnails
	 * 
	 */
	public void setThumnailsMode() {
		singleImagePanel.setVisible(false);
		thumbnailsPanel.setVisible(true);
		revalidate();
	}

	/*
	 * Getters for all buttons and textfields. Used by controller for
	 * actionEvents
	 */

	public JButton getHomeBtn() {
		return homeBtn;
	}

	public JButton getNextBtn() {
		return nextBtn;
	}

	public JButton getPrevBtn() {
		return prevBtn;
	}

	public JButton getRotRightBtn() {
		return rotRightBtn;
	}

	public JButton getRotLeftBtn() {
		return rotLeftBtn;
	}

	public JButton getEditMetaBtn() {
		return editMetaBtn;
	}

	public JPanel getThumbnailsPanel() {
		return thumbnailsPanel;
	}

	public JPanel getSingleImagePanel() {
		return singleImagePanel;
	}

	public JTextField getRatingJTextField() {
		return ratingJTextField;
	}

	public JTextField getNameJTextField() {
		return nameJTextField;
	}

	public JTextField getTagsJTextField() {
		return tagsJTextField;
	}

	public JTextField getDateJTextField() {
		return dateJTextField;
	}

	public JTextField getEtcJTextField() {
		return etcJTextField;
	}

	public JButton getSearchBtn() {
		return searchBtn;
	}

	public JButton getImportBtn() {
		return importBtn;
	}

	public JButton getRefreshBtn() {
		return refreshBtn;
	}
}
