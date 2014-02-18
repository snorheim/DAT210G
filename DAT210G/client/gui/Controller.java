package gui;

import javax.swing.*;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Controller
 * 
 * Controls everything, main entrypoint for gui
 * 
 * @author Ronnie
 * 
 */
public class Controller {

	private Dimension screenSize;
	private View view;
	private SidebarPanel sidebarPanel;
	private ImageAreaPanel imageAreaPanel;
	private ThumbnailsPanel thumbnailsPanel;
	private JScrollPane scrollPane;
	private ImageHandler imageHandler;
	private JFileChooser fc = new JFileChooser();

	private int[] imageIdArray = null;
	private ArrayList<OneImage> currentImages;
	private OneImage currentSingleImage; // Single image shown in
											// singleImageMode, eg. NOT a
											// thumbnail.

	private MouseClickHandler mouseClickHandler;
	private ButtonHandler buttonHandler;

	/**
	 * Constructor
	 */
	public Controller() {

		mouseClickHandler = new MouseClickHandler(this);

		imageHandler = new ImageHandler();

		screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		sidebarPanel = new SidebarPanel();
		buttonHandler = new ButtonHandler(this, sidebarPanel);
		thumbnailsPanel = new ThumbnailsPanel();

		imageAreaPanel = new ImageAreaPanel(thumbnailsPanel);

		scrollPane = new JScrollPane(imageAreaPanel);

		view = new View(sidebarPanel, scrollPane);

		view.setSize(new Dimension((int) screenSize.getWidth() / 2,
				(int) screenSize.getHeight() / 2));

		getAllThumbnails();

		setThumbnailsMode();

	}

	public void setSingleImageMode(int imageId) {

		getBigImage(imageId);
		sidebarPanel.setSingleImageMode(currentSingleImage);
		imageAreaPanel.setSingleImageMode(currentSingleImage);

	}

	public void setThumbnailsMode() {
		thumbnailsPanel.showThumbnails(currentImages);
		sidebarPanel.setThumnailsMode();
		imageAreaPanel.setThumbnailsMode();

	}

	public void refreshThumbnails() {

		thumbnailsPanel.clearThumbnailsPanel();

		getAllThumbnails();

		thumbnailsPanel.showThumbnails(currentImages);
	}

	private void getAllThumbnails() {

		getAllImageIds();

		currentImages = new ArrayList<OneImage>();

		if (imageIdArray == null) {
			System.out.println("Got no images from server!");
		} else {

			for (int i = 0; i < imageIdArray.length; i++) {
				currentImages.add(i,
						new OneImage(
								imageHandler.getThumbnail(imageIdArray[i]),
								imageIdArray[i]));
				currentImages.get(i).addImageMouseListener(mouseClickHandler);
			}

		}

	}

	private void getAllImageIds() {

		imageIdArray = imageHandler.getAllImages();
		
		if (imageHandler.getAllImages() == null) {
			System.out.println("Får ikke bilder fra server....  imageIdArray = null");
		}
		
		
		

	}

	private OneImage getBigImage(int imageId) {

		BufferedImage temp = imageHandler.getLargeImage(5);

		currentSingleImage = new OneImage(imageHandler.getLargeImage(imageId),
				imageId);

		return currentSingleImage;

	}

	/**
	 * JFileChooser
	 * 
	 * @return
	 */
	public void openFileChooser() {

		fc.setMultiSelectionEnabled(true);
		fc.setFileHidingEnabled(true);

		int returnVal = fc.showOpenDialog(sidebarPanel);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			System.out.println(fc.getSelectedFiles().length);
			
			for (int i = 0; i < fc.getSelectedFiles().length; i++) {
				imageHandler.SendImageToServer(fc.getSelectedFiles()[i]);
			}
			
			
		} 
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Controller();

	}

}
