package storing;

import java.util.Set;

public class TagDb {
	private String tag;
	private Set<PictureDb> pics;
	
	public TagDb(){}
	
	public TagDb(String tag){
		this.tag = tag.toLowerCase();
	}
	
	public Set<PictureDb> getPics() {
		return pics;
	}

	public void setPics(Set<PictureDb> pics) {
		this.pics = pics;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag.toLowerCase();
	}
	
	public void addPic(PictureDb pic) {
		pics.add(pic);
		pic.getTags().add(this);
	}
	
	
}
