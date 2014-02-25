package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Ronnie on 14.02.14.
 */
public class ButtonHandler {

	private Controller controller;
	private SidebarPanel sidebarPanel;

	public ButtonHandler(Controller controller, SidebarPanel sidebarPanel) {

		this.controller = controller;
		this.sidebarPanel = sidebarPanel;
		makeThumbnailsModeListeners();
		makeSingleModeListeners();

	}

	private void makeThumbnailsModeListeners() {
		/*
		 * sidebarPanel.getRatingJTextField().addActionListener();
		 * sidebarPanel.getNameJTextField().addActionListener();
		 * sidebarPanel.getTagsJTextField().addActionListener();
		 * sidebarPanel.getDateJTextField().addActionListener();
		 * sidebarPanel.getEtcJTextField().addActionListener();
		 */
		sidebarPanel.getSearchBtn().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Clicked Search Button");
			}
		});
		sidebarPanel.getImportBtn().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Clicked Import Button");

				controller.openFileChooser();

			}
		});

		sidebarPanel.getRefreshBtn().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Clicked Refresh Button");

				controller.refreshThumbnails();

			}
		});

	}

	private void makeSingleModeListeners() {

		sidebarPanel.getHomeBtn().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.setThumbnailsMode();
			}
		});
		sidebarPanel.getNextBtn().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Clicked Next Button");
				
				
				int temp = controller.getCurrentImageId() + 1;
				if (temp > controller.getCurrentImages().size()) {
					controller.setSingleImageMode(controller.getCurrentImages().get(0).getImageId());
				} else {
					controller.setSingleImageMode(temp);
				}
				
			}
		});
		sidebarPanel.getPrevBtn().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Clicked Previous button");
				
				int temp = controller.getCurrentImageId() - 1;
				if (temp <= 0) {					
					controller.setSingleImageMode(controller.getCurrentImages().get(controller.getCurrentImages().size() - 1).getImageId());
				} else {
					controller.setSingleImageMode(temp);
				}
			}
		});
		sidebarPanel.getRotLeftBtn().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Clicked Rotate Left Button");
			}
		});
		sidebarPanel.getRotRightBtn().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Clicked Rotate Right Button");
			}
		});
		sidebarPanel.getEditMetaBtn().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Clicked Edit Metadata Button");
			}
		});

	}

}
