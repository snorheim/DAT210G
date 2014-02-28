package gui.model;

import gui.MainController;

import java.awt.image.BufferedImage;

import javafx.embed.swing.SwingFXUtils;
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

	
	public OneImage(MainController main, BufferedImage bufImage, int imageId) {

		this(main, bufImage, imageId, null);
		

	}

	public OneImage(MainController main, BufferedImage bufImage, int imageId, String[] metaData) {

		this.main = main;
		this.metaData = metaData;
		Image image =  SwingFXUtils.toFXImage(bufImage, null);
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
