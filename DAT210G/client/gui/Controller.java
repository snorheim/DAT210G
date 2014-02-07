package gui;

import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Controller {
	
	private GUI gui;

	
	private ArrayList<JPanel> imagePanelList;	// List with panels containing single images.
	
	public Controller() {
		gui = new GUI();

		imagePanelList = new ArrayList<>();
	}
	
	public void insertImageIntoList(JLabel image) {
		SingleImagePanel singleImagePanel = new SingleImagePanel(image);
		
		
		imagePanelList.add(singleImagePanel);
	}
	
	public void pushImagesToGUI() {
		gui.drawImagesFromList(imagePanelList);
	}
	
	
	
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				Controller controller  = new Controller();
				String labelName;
				for (int i = 0; i < 500; i++) {
					labelName = "Image #" + String.valueOf(i);
					controller.insertImageIntoList(new JLabel(labelName));
				}
				
				controller.pushImagesToGUI();
			}
		});
	}
}
