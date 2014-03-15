package storing;

import java.util.ArrayList;

public class TreeMenuNode {
	private ParentFolderDb root;
	private ArrayList<TreeMenuNode> children;
	
	public ParentFolderDb getRoot() {
		return root;
	}

	public void setRoot(ParentFolderDb root) {
		this.root = root;
	}

	public ArrayList<TreeMenuNode> getChildren() {
		return children;
	}

	public void setChildren(ArrayList<TreeMenuNode> children) {
		this.children = children;
	}

	public TreeMenuNode() {}
	
	public TreeMenuNode(ParentFolderDb root) {
		this.root = root;
	}
	
	public TreeMenuNode(ParentFolderDb root, ArrayList<TreeMenuNode> children) {
		this.root = root;
		this.children = children;
	}
	
	public String printChildren() {
		String result ="   ";
		for (TreeMenuNode t: children) {
			result += t.getRoot().getFolderId() + ", " + t.getRoot().getName() + "\n   ";
		}
		return result;
	}
	
}
