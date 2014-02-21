package gui;

import java.awt.BorderLayout;

import javax.swing.BoxLayout;
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
		
		metaDataPanel.setLayout(new BoxLayout(metaDataPanel, BoxLayout.PAGE_AXIS));
		
		ratingJTextField = new JTextField("Rating");
		nameJTextField = new JTextField("Name");
		tagsJTextField = new JTextField("Tags");
		dateJTextField = new JTextField("Date");
		
		metaDataPanel.add(ratingJTextField);
		metaDataPanel.add(nameJTextField);
		metaDataPanel.add(tagsJTextField);
		metaDataPanel.add(dateJTextField);
		
		add(metaDataPanel, BorderLayout.EAST);
		
		
		
		
	}
	
	public void setImage(OneImage image) {
		
		theImage = image;
						
		setupMetaDataPanel();
		
		add(theImage, BorderLayout.CENTER);
		
		
	}
	
	
	

}
