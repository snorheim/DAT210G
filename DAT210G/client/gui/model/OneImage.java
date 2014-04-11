package gui.model;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;

public class OneImage {

	private int imageId;
	private int folderId;
	private String titleMeta;
	private String descMeta;
	private String ratingMeta;
	private String dateMeta;
	private String tagsMeta;

	private ImageView thumbnailImage;
	private ImageView mediumImage;
	
	

	public OneImage(int imageId, int folderId) {

		this.setImageId(imageId);
		this.setFolderId(folderId);
		

		cacheMeta();
		cacheImages();

	}

	private void cacheMeta() {

		String[] tempMeta = ServerCommHandler.getMetaData(imageId);

		titleMeta = tempMeta[0];
		descMeta = tempMeta[1];
		ratingMeta = tempMeta[2];
		dateMeta = tempMeta[3];
		tagsMeta = tempMeta[4];

	}

	private void cacheImages() {
		thumbnailImage = ServerCommHandler.getThumbnail(imageId);

		

		mediumImage = ServerCommHandler.getMediumImage(imageId);

		

	}

	public int getImageId() {
		return imageId;
	}

	public void setImageId(int imageId) {
		this.imageId = imageId;
	}

	public int getFolderId() {
		return folderId;
	}

	public void setFolderId(int folderId) {
		this.folderId = folderId;
	}

	public ImageView getThumbnailImage() {
		
		thumbnailImage.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				
				FolderTree.setCurrentImage(OneImage.this);
				
				FolderTree.getMain().setSingleMode();

			}
		});

		return thumbnailImage;
	}

	public ImageView getMediumImage() {
		
		mediumImage.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				
				FolderTree.setCurrentImage(OneImage.this);
				
				FolderTree.getMain().setSingleMode();
			}
		});

		return mediumImage;
	}
	
	public ImageView getThumbnailImageWithoutMouseHandler() {
		
		ImageView image = thumbnailImage;
		
		image.setOnMouseClicked(null);

		return image;
	}

	public ImageView getMediumImageWithoutMouseHandler() {
		
		ImageView image = mediumImage;		
		
		image.setOnMouseClicked(null);


		return image;
	}

	public ImageView getFullImage() {
		ImageView tempImage = ServerCommHandler.getFullImage(imageId);

		tempImage.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				System.out.println("Clicked full image");

			}
		});

		return tempImage;
	}

	public String toString() {

		return "[ FolderId: " + folderId + ", ImageId: " + imageId + " ]";

	}

	public ImageView getRotLeft() {

		ImageView image = ServerCommHandler.getRotLeft(imageId);

		return image;

	}

	public ImageView getRotRight() {

		ImageView image = ServerCommHandler.getRotRight(imageId);

		return image;

	}

	public String getTitleMeta() {

		return titleMeta;

	}

	public String getDescMeta() {

		return descMeta;
	}

	public String getRatingMeta() {

		return ratingMeta;
	}

	public String getDateMeta() {

		return dateMeta;
	}

	public String getTagsMeta() {

		return tagsMeta;
	}

	public void modifyTitle(String string) {
		Boolean success = ServerCommHandler.modifyTitle(imageId, string);

		if (success) {
			
			cacheMeta();
		}
	}

	public void modifyDesc(String string) {
		Boolean success = ServerCommHandler.modifyDesc(imageId, string);

		if (success) {
			cacheMeta();
		}
	}

	public void modifyRating(String string) {
		Boolean success = ServerCommHandler.modifyRating(imageId, string);

		if (success) {
			cacheMeta();
		}
	}

	public void addTag(String string) {
		Boolean success = ServerCommHandler.addTag(imageId, string);

		if (success) {
			cacheMeta();

			System.out
					.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		}
	}

}
