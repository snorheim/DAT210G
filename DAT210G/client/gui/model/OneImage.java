package gui.model;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;

public class OneImage {


	private int imageId;
	private ImageView image;
	private Model model;
	private ServerCommHandler serverCommHandler;
	private String titleMeta;
	private String descMeta;
	private String ratingMeta;
	private String dateMeta;
	private String tagsMeta;

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

		titleMeta = serverCommHandler.getMetaData(imageId)[0];
		
		return titleMeta;

	}

	public String getDescMeta() {
		descMeta = serverCommHandler.getMetaData(imageId)[1];
		
		return descMeta;
	}

	public String getRatingMeta() {
		ratingMeta = serverCommHandler.getMetaData(imageId)[2];
		
		return ratingMeta;
	}

	public String getDateMeta() {
		dateMeta = serverCommHandler.getMetaData(imageId)[3];
		
		return dateMeta;
	}

	public String getTagsMeta() {
		tagsMeta = serverCommHandler.getMetaData(imageId)[4];
		
		return tagsMeta;
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
