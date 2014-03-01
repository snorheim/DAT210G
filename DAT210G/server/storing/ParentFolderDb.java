package storing;

import java.util.Set;

public class ParentFolderDb {
	private int folderId;
	private int parentId;
	private String folderName;
	private String path;
//	private Set<PictureDb> pictures;
	
	public ParentFolderDb() {}
	
	public ParentFolderDb(int parentId, String folderName, String path) {
		this.parentId = parentId;
		this.folderName = folderName;
		this.path = path;
	}

	public int getFolderId() {
		return folderId;
	}

	public void setFolderId(int folderId) {
		this.folderId = folderId;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public String getFolderName() {
		return folderName;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

//	public Set<PictureDb> getPictures() {
//		return pictures;
//	}
//
//	public void setPictures(Set<PictureDb> pictures) {
//		this.pictures = pictures;
//	}
	
	public String toString() {
		String info = folderId + ", " + parentId + ", " + folderName + ", " + path;
		return info;
	}
	
}
