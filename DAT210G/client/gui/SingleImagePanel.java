package gui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SingleImagePanel extends JPanel {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private OneImage theImage;
	private JPanel metaDataPanel;
	
	private JTextField ratingJTextField;
	private JTextField nameJTextField;
	private JTextField tagsJTextField;
	private JTextField dateJTextField;
	
	
	

	public SingleImagePanel() {
		
		setLayout(new BorderLayout());
		
		
		
		metaDataPanel = new JPanel();
		
		
		
		setupMetaDataPanel();		
		
		
	}
	
	private void setupMetaDataPanel() {
		
		
		
		metaDataPanel.removeAll();
		
		metaDataPanel.setLayout(new GridBagLayout());
		
		
		ratingJTextField = new JTextField("Rating");
		nameJTextField = new JTextField("Name");
		tagsJTextField = new JTextField("Tags");
		dateJTextField = new JTextField("Date");
		
		
		GridBagConstraints a = new GridBagConstraints();
		a.fill = GridBagConstraints.HORIZONTAL;
		a.ipadx = 2;
		a.ipady = 2;
		a.weightx = 0.5;
		a.gridx = 0;
		a.gridy = 0;
		metaDataPanel.add(new JLabel("Rating"), a);

		GridBagConstraints b = new GridBagConstraints();
		b.fill = GridBagConstraints.HORIZONTAL;
		b.ipadx = 2;
		b.ipady = 2;
		b.weightx = 0.5;
		b.gridx = 1;
		b.gridy = 0;
		metaDataPanel.add(ratingJTextField, b);

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 2;
		c.ipady = 2;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 1;
		metaDataPanel.add(new JLabel("Name"), c);

		GridBagConstraints d = new GridBagConstraints();
		d.fill = GridBagConstraints.HORIZONTAL;
		d.ipadx = 2;
		d.ipady = 2;
		d.weightx = 0.5;
		d.gridx = 1;
		d.gridy = 1;
		metaDataPanel.add(nameJTextField, d);

		GridBagConstraints e = new GridBagConstraints();
		e.fill = GridBagConstraints.HORIZONTAL;
		e.ipadx = 2;
		e.ipady = 2;
		e.weightx = 0.5;
		e.gridx = 0;
		e.gridy = 2;
		metaDataPanel.add(new JLabel("Tags"), e);

		GridBagConstraints f = new GridBagConstraints();
		f.fill = GridBagConstraints.HORIZONTAL;
		f.ipadx = 2;
		f.ipady = 2;
		f.weightx = 0.5;
		f.gridx = 1;
		f.gridy = 2;
		metaDataPanel.add(tagsJTextField, f);

		GridBagConstraints g = new GridBagConstraints();
		g.fill = GridBagConstraints.HORIZONTAL;
		g.ipadx = 2;
		g.ipady = 2;
		g.weightx = 0.5;
		g.gridx = 0;
		g.gridy = 3;
		metaDataPanel.add(new JLabel("Date"), g);

		GridBagConstraints h = new GridBagConstraints();
		h.fill = GridBagConstraints.HORIZONTAL;
		h.ipadx = 2;
		h.ipady = 2;
		h.weightx = 0.5;
		h.gridx = 1;
		h.gridy = 3;
		metaDataPanel.add(dateJTextField, h);

		
		
		
		
		add(metaDataPanel, BorderLayout.EAST);
		
		updateMetaDataInTextFields();
		
		
		
	}
	
	public void setImage(OneImage image) {
		
		theImage = image;
						
		setupMetaDataPanel();
		
		add(theImage, BorderLayout.CENTER);
		
		
	}
	
	private void updateMetaDataInTextFields() {
		
		
		System.out.println(theImage.getMetaData().length);
		
		ratingJTextField.setText("sdfgsdgs");
		nameJTextField.setText("dgsdfg");;
		tagsJTextField.setText("dgsdfg");;
		dateJTextField.setText("dgsdfg");
		
	}
	
	

}
