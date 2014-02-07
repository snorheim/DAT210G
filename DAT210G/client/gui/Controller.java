package gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class Controller {
	
	private GUI gui;
	private ArrayList<SingleImage> imageList;	// List with panels containing single images.
	
	public Controller() {
		gui = new GUI();

		imageList = new ArrayList<>();
		
		
	}
	
	public void insertImageIntoList(JLabel image) {
		SingleImage singleImagePanel = new SingleImage(image);
		
		singleImagePanel.addImageListener(new ImageClickListener());
		
		imageList.add(singleImagePanel);
	}
	
	public void pushImagesToGUI() {
		gui.drawImagesFromList(imageList);
	}
	
	class ImageClickListener implements MouseListener{


		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			System.out.println("mouse clicked");
			gui.setSingleImageMode();
			System.out.println(e.getSource());

			
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			//System.out.println("mouse pressed");
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			//System.out.println("mouse released");
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			//System.out.println("mouse entered");
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			//System.out.println("mouse exited");
		}
		
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
