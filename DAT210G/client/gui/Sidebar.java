package gui;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Sidebar extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField ratingJTextField;
	private JTextField nameJTextField;
	private JTextField tagsJTextField;
	private JTextField dateJTextField;
	private JTextField etcJTextField;
	private JButton searchBtn;
	private JButton importBtn;
	private JButton homeBtn;
	private JButton nextBtn;
	private JButton prevBtn;
	private JButton rotCWBtn;
	private JButton rotCCWBtn;
	private JButton editMetaBtn;
	
	public Sidebar() {
		
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		ratingJTextField = new JTextField("Rating");
		nameJTextField = new JTextField("Name");
		tagsJTextField = new JTextField("Tags");
		dateJTextField = new JTextField("Date");
		etcJTextField = new JTextField("Etc");
		searchBtn = new JButton("Search");
		importBtn = new JButton("Import");
		homeBtn = new JButton("Home");
		nextBtn = new JButton("Next");
		prevBtn = new JButton("Previous");
		rotCWBtn = new JButton("Rotate Right");
		rotCCWBtn = new JButton("Rotate Left");
		editMetaBtn = new JButton("Edit Metadata");
		
		add(ratingJTextField);
		add(nameJTextField);
		add(tagsJTextField);
		add(dateJTextField);
		add(etcJTextField);
		add(searchBtn);
		add(importBtn);
		add(homeBtn);
		add(nextBtn);
		add(prevBtn);
		add(rotCCWBtn);
		add(rotCWBtn);
		add(editMetaBtn);
		
		setMultiImageMode();
		
		
	}
	
	
	public void setMultiImageMode() {
		homeBtn.setVisible(false);
		nextBtn.setVisible(false);
		prevBtn.setVisible(false);
		rotCWBtn.setVisible(false);
		rotCCWBtn.setVisible(false);
		editMetaBtn.setVisible(false);
		
		ratingJTextField.setVisible(true);
		nameJTextField.setVisible(true);
		tagsJTextField.setVisible(true);
		dateJTextField.setVisible(true);
		etcJTextField.setVisible(true);
		searchBtn.setVisible(true);
		importBtn.setVisible(true);
		
	}
	
	public void setSingleImageMode() {
		ratingJTextField.setVisible(false);
		nameJTextField.setVisible(false);
		tagsJTextField.setVisible(false);
		dateJTextField.setVisible(false);
		etcJTextField.setVisible(false);
		searchBtn.setVisible(false);
		importBtn.setVisible(false);
		
		homeBtn.setVisible(true);
		nextBtn.setVisible(true);
		prevBtn.setVisible(true);
		rotCWBtn.setVisible(true);
		rotCCWBtn.setVisible(true);
		editMetaBtn.setVisible(true);
	}
}
