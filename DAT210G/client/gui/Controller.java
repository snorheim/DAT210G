package gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class Controller {
	
	private GUI gui;
	
	// for testing
	private BufferedImage thumbnail = null;
	private BufferedImage fullsize = null;
	
	public Controller() {
		
		
		loadImages();
		
		gui = new GUI();
		
		insertImages();
		
	}
	
	private void insertImages() {
		
		for (int i = 0; i < 500; i++) {
			SingleImage temp = new SingleImage(thumbnail, i);
			temp.addMouseListener(new ImageClickListener());
			gui.insertImage(temp);
		}
		
	}
	
	private void loadImages() {
		
		try {
			thumbnail = ImageIO.read(new File("C:\\Users\\Ronnie\\Documents\\GitHub\\DAT210G\\DAT210G\\client\\gui\\funny-dog-thumb.jpg"));
			fullsize = ImageIO.read(new File("C:\\Users\\Ronnie\\Documents\\GitHub\\DAT210G\\DAT210G\\client\\gui\\funny-dog.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	class ImageClickListener implements MouseListener{


		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			System.out.println("mouse clicked");
			gui.setSingleImageMode();
			System.out.println(e.getSource().toString());
			SingleImage test = (SingleImage) e.getSource();
			gui.setSingleImageMode();
			System.out.println(test.getImageID());
			gui.insertImage(test);
			
			
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
				
				
				
			}
		});
	}
}
