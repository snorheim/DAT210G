package gui.model;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;

public class OneImage {


	private int imageId;
	private ImageView image;
	private Model model;
	private ServerCommHandler serverCommHandler;

	public OneImage(int imageId, ServerCommHandler serverComm, Model model) {

		this.imageId = imageId;	
		this.serverCommHandler = serverComm;
		this.model = model;				

	}

	public int getImageId() {
		return imageId;
	}


	public Model getModel() {
		return model;
	}

	public ImageView getThumbnailImage() {

		
		image = serverCommHandler.getThumbnail(imageId);

		image.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				System.out.println("Clicked image: " + getImageId());
				getModel().setCurrentImageId(getImageId());

			}
		});

		return image;

	}

	public ImageView getMediumImage() {


		image = serverCommHandler.getMediumImage(imageId);

		image.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				System.out.println("Clicked image: " + getImageId());
				getModel().setCurrentImageId(getImageId());

			}
		});

		return image;

	}

	public ImageView getFullImage() {


		image = serverCommHandler.getFullImage(imageId);		

		return image;

	}
	
	public ImageView getRotLeft() {


		image = serverCommHandler.getRotLeft(imageId);

		return image;

	}
	
	public ImageView getRotRight() {


		image = serverCommHandler.getRotRight(imageId);

		return image;

	}

	public String getTitleMeta() {

		return serverCommHandler.getMetaData(imageId)[0];


	}

	public String getDescMeta() {
		return serverCommHandler.getMetaData(imageId)[1];
	}

	public String getRatingMeta() {
		return serverCommHandler.getMetaData(imageId)[2];
	}

	public String getDateMeta() {
		return serverCommHandler.getMetaData(imageId)[3];
	}

	public String getTagsMeta() {
		return serverCommHandler.getMetaData(imageId)[4];
	}
	
	public void modifyTitle(String string) {
		Boolean success = serverCommHandler.modifyTitle(imageId, string);
		
		if (success) {
			System.out.println("sucess");
		}
	}
	
	public void modifyDesc(String string) {
		Boolean success = serverCommHandler.modifyDesc(imageId, string);
		
		if (success) {
			System.out.println("sucess");
		}
	}
	
	public void modifyRating(String string) {
		Boolean success = serverCommHandler.modifyRating(imageId, string);
		
		if (success) {
			System.out.println("sucess");
		}
	}
	
	public void addTag(String string) {
		Boolean success = serverCommHandler.addTag(imageId, string);
		
		if (success) {
			System.out.println("sucess");
		}
	}



}
