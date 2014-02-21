package storing;

import java.util.Set;

import antlr.collections.List;



public class PictureDb {
	private int id;
	private String title;
	private String description;
	private int rating;
	private String dateTime;
	private String fileLocation;
	private String mediumFileLocation;
	private String thumbnailFileLocation;
	private Set<TagDb> tags;
	
	public PictureDb() {}
	
	public PictureDb(String title, String description, int rating, String dateTime,
			String fileLocation, String mediumFileLocation, String thumbnailFileLocation) {
		this.title = title;
		this.description = description;
		this.rating = rating;
		this.dateTime = dateTime;
		this.fileLocation = fileLocation;
		this.mediumFileLocation = mediumFileLocation;
		this.thumbnailFileLocation = thumbnailFileLocation;
		
	}
	
	public void addTag(TagDb t) {
		tags.add(t);
		t.getPics().add(this);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public String getFileLocation() {
		return fileLocation;
	}

	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}

	public String getMediumFileLocation() {
		return mediumFileLocation;
	}

	public void setMediumFileLocation(String mediumFileLocation) {
		this.mediumFileLocation = mediumFileLocation;
	}

	public String getThumbnailFileLocation() {
		return thumbnailFileLocation;
	}

	public void setThumbnailFileLocation(String thumbnailFileLocation) {
		this.thumbnailFileLocation = thumbnailFileLocation;
	}
	
	public String getTagString() {
		return ReadFromDatabase.getAllTagsForAPicture(id);
	}
	
	public Set<TagDb> getTags() {
		return tags;
	}

	public void setTags(Set<TagDb> tags) {
		this.tags = tags;
	}
}
