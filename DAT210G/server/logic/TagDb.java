package logic;

import java.util.Set;

public class TagDb {
	private String tag;
	private Set<PictureDb> pics;
	
	public Set<PictureDb> getPics() {
		return pics;
	}

	public void setPics(Set<PictureDb> pics) {
		this.pics = pics;
	}

	public TagDb(){}
	
	public TagDb(String tag){
		this.tag = tag;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
	
	public void addPic(PictureDb pic) {
		pics.add(pic);
		pic.getTags().add(this);
	}
	
	
}
