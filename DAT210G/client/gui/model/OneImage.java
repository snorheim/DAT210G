package gui.model;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;

public class OneImage {


	private int imageId;
	private ImageView thumbnailImage;
	private ImageView mediumImage;
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

	public void setThumbnailImage() {

		
		thumbnailImage = serverCommHandler.getThumbnail(imageId);

		thumbnailImage.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				System.out.println("Clicked image: " + getImageId());
				getModel().setCurrentImageId(getImageId());

			}
		});

		

	}

	public void setMediumImage() {


		mediumImage = serverCommHandler.getMediumImage(imageId);

		mediumImage.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				System.out.println("Clicked image: " + getImageId());
				getModel().setCurrentImageId(getImageId());

			}
		});

		

	}

	public ImageView getFullImage() {


		return serverCommHandler.getFullImage(imageId);		
		

	}
	
	public ImageView getRotLeft() {


		thumbnailImage = serverCommHandler.getRotLeft(imageId);

		return thumbnailImage;

	}
	
	public ImageView getRotRight() {


		thumbnailImage = serverCommHandler.getRotRight(imageId);

		return thumbnailImage;

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
			//System.out.println("sucess");
		}
	}
	
	public void modifyDesc(String string) {
		Boolean success = serverCommHandler.modifyDesc(imageId, string);
		
		if (success) {
			//System.out.println("sucess");
		}
	}
	
	public void modifyRating(String string) {
		Boolean success = serverCommHandler.modifyRating(imageId, string);
		
		if (success) {
			//System.out.println("sucess");
		}
	}
	
	public void addTag(String string) {
		Boolean success = serverCommHandler.addTag(imageId, string);
		
		if (success) {
			//System.out.println("sucess");
		}
	}

	public ImageView getThumbnailImage() {
		return thumbnailImage;
	}
	
	public ImageView getMediumImage() {
		return mediumImage;
	}
	
	



}
