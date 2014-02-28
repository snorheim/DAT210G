package gui.model;

import gui.MainController;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;


/**
 * Created by Ronnie on 14.02.14.
 */
public class OneImage {

	private MainController main;
	private int imageId;	
	private ImageView imageView;
	private String[] metaData;

	
	public OneImage(MainController main, Image image, int imageId) {

		this(main, image, imageId, null);
		

	}

	public OneImage(MainController main, Image image, int imageId, String[] metaData) {

		this.main = main;
		this.metaData = metaData;
		imageView = new ImageView(image);
		this.imageId = imageId;
		
		imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				System.out.println("clicked image " + getImageId());
				getMain().setCurrentImageId(getImageId());				
				getMain().showSingleMode();
				
				
			}
		});

	}


	public int getImageId() {
		return imageId;
	}


	public ImageView getImageView() {
		return imageView;
	}


	public String[] getMetaData() {
		return metaData;
	}

	public MainController getMain() {
		return main;
	}
	
	
	
	
}
