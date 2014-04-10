package gui.model;

import java.util.ArrayList;
import java.util.List;

public class FolderNode {

	private List<FolderNode> children = new ArrayList<FolderNode>();
	private FolderNode parent = null;
	private ArrayList<OneImage> imageList = null;
	private int folderId;
	private String folderName;

	public FolderNode(ArrayList<OneImage> imageList, int folderId,
			String folderName) {
		this.imageList = imageList;
		this.folderId = folderId;
		this.folderName = folderName;
	}

	public FolderNode(ArrayList<OneImage> imageList, int folderId,
			String folderName, FolderNode parent) {
		this.imageList = imageList;
		this.folderId = folderId;
		this.folderName = folderName;
		this.parent = parent;
	}

	public List<FolderNode> getChildren() {
		return children;
	}

	public void setParent(FolderNode parent) {
		this.parent = parent;
	}

	public FolderNode getParent() {
		return parent;
	}

	public void addChild(ArrayList<OneImage> imageList, int folderId,
			String folderName) {
		FolderNode child = new FolderNode(imageList, folderId, folderName);
		child.setParent(this);
		this.children.add(child);
	}

	public void addChild(FolderNode child) {
		child.setParent(this);
		this.children.add(child);
	}

	

	

	

	public ArrayList<OneImage> getImageList() {
		return imageList;
	}

	public void setImageList(ArrayList<OneImage> imageList) {
		this.imageList = imageList;
	}

	public int getFolderId() {
		return folderId;
	}

	public void setFolderId(int folderId) {
		this.folderId = folderId;
	}

	public String getFolderName() {
		return folderName;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}

	public void setChildren(List<FolderNode> children) {
		this.children = children;
	}

	public String toString() {

		return folderName;
	}

}
