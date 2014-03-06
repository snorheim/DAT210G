package storing;



public class ParentFolderDb {
	private int folderId;
	private String name;
	private String path;
	private int lft;
	private int rgt;
	
	public ParentFolderDb() {}
	
	public ParentFolderDb(String folderName, String path) {
		this.name = folderName;
		this.path = path;
	}
	
	public ParentFolderDb(String folderName, String path, int lft, int rgt) {
		this.name = folderName;
		this.path = path;
		this.lft = lft;
		this.rgt = rgt;
	}

	public int getLft() {
		return lft;
	}

	public void setLft(int lft) {
		this.lft = lft;
	}

	public int getRgt() {
		return rgt;
	}

	public void setRgt(int rgt) {
		this.rgt = rgt;
	}

	public int getFolderId() {
		return folderId;
	}

	public void setFolderId(int folderId) {
		this.folderId = folderId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	
}
