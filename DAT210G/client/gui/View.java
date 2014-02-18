package gui;

import java.awt.*;

import javax.swing.*;

/**
 * View
 * 
 * Main view
 * 
 * @author Ronnie
 * 
 */
public class View extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public View(SidebarPanel sidebarPanel, JScrollPane imageAreaScrollPane) {

		this.setTitle("Prosjekt");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		this.add(sidebarPanel, BorderLayout.WEST);
		this.add(imageAreaScrollPane, BorderLayout.CENTER);

		this.pack();
		this.setVisible(true);

	}

}
