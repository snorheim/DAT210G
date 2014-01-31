package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

public class GUI extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private static final int WIDTH = 500;
	private static final int HEIGHT = 500;
	
	private int imgGridColumns = 3;
	private int imgGridRows = 6;
	
	private JButton searchBtn;
	private JButton importBtn;
	private JPanel navPanel;
	private ImagePanel imgPanel;
	
	
	
	public GUI() {
		
		makeGUI();
		
	}
	
	private void makeGUI() {
		
		setTitle("Prosjekt Client GUI");
		setSize(WIDTH, HEIGHT);
		
		
		
		/*
		 * navPanel for navigation
		 * 
		 * Buttons etc.
		 * 
		 */
		navPanel = new JPanel();
		navPanel.setLayout(new BoxLayout(navPanel, BoxLayout.PAGE_AXIS));;	
		navPanel.setBorder(new LineBorder(Color.BLUE));
		add(navPanel, BorderLayout.WEST);
		
		
		/*
		 * imgPanel
		 * 
		 * Area for images
		 * 
		 */				
		imgPanel = new ImagePanel(imgGridRows, imgGridColumns);		

		JScrollPane scroll = new JScrollPane(imgPanel);
		
		add(scroll, BorderLayout.CENTER);
		
		/*
		 * Buttons
		 * 
		 * 
		 */
		searchBtn = new JButton("Search");
		navPanel.add(searchBtn);
		importBtn = new JButton("Import");
		navPanel.add(importBtn);
		
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	
	public void addSearchBtnListener(ActionListener listenForSearchBtn) {
		searchBtn.addActionListener(listenForSearchBtn);
	}
	
	public void addImportBtnListener(ActionListener listenForImportBtn) {
		importBtn.addActionListener(listenForImportBtn);
	}

	
	
}
