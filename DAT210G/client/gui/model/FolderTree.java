package gui.model;

import gui.Main;
import gui.ManyViewController;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class FolderTree {

	private int rootFolderId = 1;
	private FolderNode rootNode;

	private TreeView<FolderNode> treeView;
	private TreeItem<FolderNode> treeItemRoot;

	private FolderNode currentFolder;
	private TreeItem<FolderNode> currentTreeItem;
	private int currentFolderId;
	private OneImage currentImage;
	private ArrayList<OneImage> imagesInThisFolderAndDown;

	private ManyViewController manyViewController;
	private Main mainController;
	private Task<Void> task;
	private boolean ready;
	

	public FolderTree(Main mainController) {

		ready = false;

		this.mainController = mainController;
		
		currentFolderId = rootFolderId;
		
		

	}

	public void update() {
		
		ready = false;

		task = new Task<Void>() {
			@Override
			public Void call() {

				updateTask();

				updateProgress(100, 100);

				return null;
			}

			@Override
			protected void succeeded() {

				super.succeeded();

				ready = true;

				manyViewController.start();

			}
		};

		new Thread(task).start();

	}

	public void updateTask() {
		treeView = new TreeView<FolderNode>();
		treeView.getSelectionModel().selectedItemProperty()
				.addListener(new ChangeListener<TreeItem<FolderNode>>() {
					public void changed(
							ObservableValue<? extends TreeItem<FolderNode>> observableValue,
							TreeItem<FolderNode> oldItem,
							TreeItem<FolderNode> newItem) {

						
						
						currentFolder = newItem.getValue();
						
						currentFolderId = currentFolder.getFolderId();
						
						getImagesFromFolderAndDown(currentFolder.getFolderId());
						manyViewController.makeGridAndDisplayImages();

					}
				});

		rootNode = new FolderNode(null, rootFolderId, "/");
		int[] imageIdsHere = ServerCommHandler.getAllImagesInFolder(1);
		ArrayList<OneImage> tempArrayList = new ArrayList<>();

		for (int i = 0; i < imageIdsHere.length; i++) {
			tempArrayList.add(new OneImage(imageIdsHere[i], 1, this));
		}
		rootNode.setImageList(tempArrayList);		

		treeItemRoot = new TreeItem<FolderNode>(rootNode);
		
		treeItemRoot.setExpanded(true);
				
		if (currentFolder == null) {
			currentFolder = rootNode;
		}

		buildTree(rootFolderId, rootNode, treeItemRoot);

		treeView.setRoot(treeItemRoot);
		
		if (currentTreeItem == null) {
			currentTreeItem = treeItemRoot;
		}
		

		getImagesFromFolderAndDown(currentFolder.getFolderId());
		
		traverseUpRecursiveTreeItems(currentTreeItem);
				
	}

	private void buildTree(int parentFolder, FolderNode parentNode,
			TreeItem<FolderNode> parentTreeItem) {

		Hashtable<Integer, String> subFolderIdAndName = ServerCommHandler
				.getSubFoldersIdAndName(parentFolder);

		FolderNode tempNode;

		for (Integer id : subFolderIdAndName.keySet()) {

			int[] imageIdsHere = ServerCommHandler.getAllImagesInFolder(id);
			ArrayList<OneImage> tempArrayList = new ArrayList<>();

			for (int i = 0; i < imageIdsHere.length; i++) {
				tempArrayList.add(new OneImage(imageIdsHere[i], id, this));
			}

			tempNode = new FolderNode(null, id, subFolderIdAndName.get(id));

			tempNode.setImageList(tempArrayList);

			TreeItem<FolderNode> tempTreeItem = new TreeItem<FolderNode>(
					tempNode);
			
			if (tempNode.getFolderId() == currentFolderId) {
				currentTreeItem = tempTreeItem;
			}
			

			parentTreeItem.getChildren().add(tempTreeItem);

			parentNode.addChild(tempNode);

			buildTree(id, tempNode, tempTreeItem);

		}

	}

	private void getImagesFromFolderAndDown(int id) {

		ArrayList<OneImage> returnList = new ArrayList<>();

		FolderNode topNode = findFolderById(rootNode, id);

		ArrayList<FolderNode> childNodes = traverse(topNode);

		for (FolderNode node : childNodes) {

			for (OneImage image : node.getImageList()) {

				returnList.add(image);

			}

		}

		imagesInThisFolderAndDown = returnList;
	}

	public ArrayList<FolderNode> traverse(FolderNode node) {

		ArrayList<FolderNode> returnList = new ArrayList<>();

		traverseRecursive(node, returnList);

		return returnList;

	}

	private void traverseRecursive(FolderNode node,
			ArrayList<FolderNode> returnList) {

		returnList.add(node);

		for (FolderNode child : node.getChildren()) {
			traverseRecursive(child, returnList);
		}
	}
	
	private void traverseUpRecursiveTreeItems(TreeItem<FolderNode> node) {

		node.setExpanded(true);		

		if (node.getParent() != null) {
			traverseUpRecursiveTreeItems(node.getParent());
		}
	}

	private FolderNode findFolderById(FolderNode node, int id) {

		if (node.getFolderId() == id)
			return node;

		List<FolderNode> children = node.getChildren();

		FolderNode res = null;

		for (int i = 0; res == null && i < children.size(); i++) {

			res = findFolderById(children.get(i), id);

		}

		return res;

	}

	// ////////////// Brukes ikke akkurat nå //////////////////////////////
	// private FolderNode findFolderByName(FolderNode node, String folderName) {
	//
	// if (node.getFolderName().equals(folderName))
	// return node;
	//
	// List<FolderNode> children = node.getChildren();
	//
	// FolderNode res = null;
	//
	// for (int i = 0; res == null && i < children.size(); i++) {
	//
	// res = findFolderByName(children.get(i), folderName);
	//
	// }
	//
	// return res;
	//
	// }

	public void getNextImageInImageList(OneImage image) {

		int indexOfCurrentImage = imagesInThisFolderAndDown.indexOf(image);

		indexOfCurrentImage++;

		if (indexOfCurrentImage >= imagesInThisFolderAndDown.size()) {
			indexOfCurrentImage = 0;
		}

		currentImage = imagesInThisFolderAndDown.get(indexOfCurrentImage);

	}

	public void getPrevImageInImageList(OneImage image) {

		int indexOfCurrentImage = imagesInThisFolderAndDown.indexOf(image);

		indexOfCurrentImage--;

		if (indexOfCurrentImage < 0) {
			indexOfCurrentImage = imagesInThisFolderAndDown.size() - 1;
		}

		this.currentImage = imagesInThisFolderAndDown.get(indexOfCurrentImage);

	}

	public TreeView<FolderNode> getTree() {
		return treeView;
	}

	public TreeItem<FolderNode> getTreeViewRoot() {
		return treeItemRoot;
	}

	public void setManyViewController(ManyViewController manyViewController) {
		this.manyViewController = manyViewController;
	}

	public FolderNode getCurrentFolder() {
		return currentFolder;
	}

	public OneImage getCurrentImage() {
		return currentImage;
	}

	public void setCurrentImage(OneImage currentImage) {
		this.currentImage = currentImage;

	}

	public Main getMainController() {
		return mainController;
	}

	public ArrayList<OneImage> getImagesInThisFolderAndDown() {
		return imagesInThisFolderAndDown;
	}

	public Task<Void> getTask() {
		return task;
	}

	public boolean isReady() {
		return ready;
	}

}
