package gui.model;

public class Folder {

	private ServerCommHandler serverCommHandler;

	private int folderId;
	private String folderName;
	private FolderTree folderTreeModel;
	
	

	public Folder(ServerCommHandler serverCommHandler, int folderId,
			String folderName, FolderTree folderTreeModel) {

		this.serverCommHandler = serverCommHandler;
		this.folderId = folderId;

		this.folderName = folderName;
		this.folderTreeModel = folderTreeModel;

		
	}
	
	

	public void setFolderTreeModel(FolderTree folderTreeModel) {
		this.folderTreeModel = folderTreeModel;
	}

	

	public String getFolderName() {
		return folderName;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}

	public int getFolderId() {
		return folderId;
	}

	public void setFolderId(int folderId) {
		this.folderId = folderId;
	}

	public String toString() {

		return folderName + "    (" + folderId + ")    ";

	}



}
