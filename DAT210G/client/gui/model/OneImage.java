package gui.model;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;

public class OneImage {
	
	
	private int imageId;
	private ImageView thumbnail;
	private Model model;
	
	public OneImage(int imageId, ImageView thumbnail, Model model) {
		
		this.imageId = imageId;
		this.thumbnail = thumbnail;
		this.model = model;
		
		this.thumbnail.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				System.out.println("Clicked image: " + getImageId());
				getModel().setCurrentImageId(getImageId());
				
			}
		});
		
	}

	public int getImageId() {
		return imageId;
	}

	public ImageView getThumbnail() {
		return thumbnail;
	}

	public Model getModel() {
		return model;
	}


	
	

}
