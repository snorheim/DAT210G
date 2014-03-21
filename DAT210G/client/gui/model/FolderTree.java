package gui.model;

import gui.MainController;
import gui.ManyViewController;
import gui.SingleViewController;

import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;

public class FolderTree {

	private TreeView<Folder> treeView;
	private TreeItem<Folder> treeRoot;
	
	private ArrayList<Folder> allFoldersList = new ArrayList<Folder>();

	private ServerCommHandler serverCommHandler;
	private ManyViewController manyViewController;
	private SingleViewController singleViewController;
	private MainController mainController;

	private int rootFolderId = 1;
	private int currentImage;
	private Folder currentFolder;

	public FolderTree() {

		serverCommHandler = new ServerCommHandler();

		

		updateTree();

	}

	public void updateTree() {
		treeView = new TreeView<Folder>();
		treeView.getSelectionModel().selectedItemProperty()
				.addListener(new ChangeListener<TreeItem<Folder>>() {
					public void changed(
							ObservableValue<? extends TreeItem<Folder>> observableValue,
							TreeItem<Folder> oldItem, TreeItem<Folder> newItem) {

						currentFolder = newItem.getValue();

						manyViewController.makeGridAndDisplayImages();

					}
				});

		Folder rootFolder = new Folder(serverCommHandler, rootFolderId, "/",
				this);
		
		

		currentFolder = rootFolder;

		allFoldersList.add(rootFolder);

		treeRoot = new TreeItem<Folder>(rootFolder);

		populateTree(rootFolderId, treeRoot);

		treeView.setRoot(treeRoot);
	}

	private void populateTree(int parentFolder, TreeItem<Folder> parentItem) {

		Hashtable<Integer, String> subFolderIdAndName = serverCommHandler
				.getSubFoldersIdAndName(parentFolder);

		TreeItem<Folder> temp;

		for (Integer id : subFolderIdAndName.keySet()) {

			Folder tempFolder = new Folder(serverCommHandler, id,
					subFolderIdAndName.get(id), this);
						

			allFoldersList.add(tempFolder);

			temp = new TreeItem<Folder>(tempFolder);

			parentItem.getChildren().add(temp);

			populateTree(id, temp);

		}

	}

	public TreeView<Folder> getTree() {
		return treeView;
	}

	public TreeItem<Folder> getTreeRoot() {
		return treeRoot;
	}

	

	public void setManyViewController(ManyViewController manyViewController) {
		this.manyViewController = manyViewController;
	}

	public void setMainController(MainController mainController) {
		this.mainController = mainController;
	}

	public void setSingleViewController(
			SingleViewController singleViewController) {
		this.singleViewController = singleViewController;
	}

	public int getCurrentImage() {
		return currentImage;
	}

	public ImageView getFullImage(int imageid) {
		return serverCommHandler.getFullImage(imageid);
	}

	public void setCurrentImage(int currentImage) {
		this.currentImage = currentImage;
		mainController.setSingleMode();
	}

	public ArrayList<Folder> getAllFoldersList() {
		return allFoldersList;
	}

	public Folder getCurrentFolder() {
		return currentFolder;
	}
	
	public void sendImagesToServer(List<File> fileList) {

		// TODO: fix så ikke den sender evig mange bilder

		if (fileList != null) {
			for (File file : fileList) {

				serverCommHandler.SendImageToServer(file);

			}
		}

	}

}
