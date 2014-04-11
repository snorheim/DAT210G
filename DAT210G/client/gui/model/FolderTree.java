package gui.model;

import gui.Main;
import gui.ManyViewController;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class FolderTree {

	private static int rootFolderId = 1;
	private static FolderNode rootNode;

	private static TreeView<FolderNode> treeView;
	private static TreeItem<FolderNode> treeItemRoot;

	private static FolderNode currentFolder;
	private static TreeItem<FolderNode> currentTreeItem;
	private static int currentFolderId;
	private static OneImage currentImage;
	private static ArrayList<OneImage> imagesInThisFolderAndDown;
	private static ArrayList<OneImage> allImagesList;

	private static ManyViewController manyViewController;
	private static Main main;
	private static Task<Void> task;
	private static boolean ready;

	public FolderTree(Main mainn) {

		ready = false;

		main = mainn;

		currentFolderId = rootFolderId;

	}

	public static void update() {

		allImagesList = new ArrayList<>();

		ready = false;

		task = new Task<Void>() {

			double progress = 0;
			double progressGoal = ServerCommHandler.getAllImageIds().length;

			@Override
			public Void call() {

				updateTask();

				return null;
			}

			@Override
			protected void succeeded() {

				super.succeeded();

				ready = true;

				manyViewController.start();

			}

			public void updateTask() {

				updateProgress((progress / progressGoal) * 100, 100);
				treeView = new TreeView<>();
				treeView.getSelectionModel()
						.selectedItemProperty()
						.addListener(
								new ChangeListener<TreeItem<FolderNode>>() {
									public void changed(
											ObservableValue<? extends TreeItem<FolderNode>> observableValue,
											TreeItem<FolderNode> oldItem,
											TreeItem<FolderNode> newItem) {

										currentFolder = newItem.getValue();

										currentFolderId = currentFolder
												.getFolderId();

										getImagesFromFolderAndDown(currentFolder
												.getFolderId());
										manyViewController.beginDrawingImages();

									}
								});

				rootNode = new FolderNode(null, rootFolderId, "/");
				int[] imageIdsHere = ServerCommHandler.getAllImagesInFolder(1);

				ArrayList<OneImage> tempArrayList = new ArrayList<>();

				for (int i = 0; i < imageIdsHere.length; i++) {
					OneImage tempImage = new OneImage(imageIdsHere[i], 1);
					tempArrayList.add(tempImage);
					allImagesList.add(tempImage);

					
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							manyViewController
									.addImageDuringLoading(tempImage);
						}
					});
					
					
					progress++;

					updateProgress((progress / progressGoal) * 100, 100);
					

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

					int[] imageIdsHere = ServerCommHandler
							.getAllImagesInFolder(id);
					ArrayList<OneImage> tempArrayList = new ArrayList<>();

					for (int i = 0; i < imageIdsHere.length; i++) {
						OneImage tempImage = new OneImage(imageIdsHere[i], id);
						tempArrayList.add(tempImage);

						// TODO: legg inn bilde i view her

						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								manyViewController
										.addImageDuringLoading(tempImage);
							}
						});

						progress++;

						updateProgress((progress / progressGoal) * 100, 100);
						System.out
								.println("***************************** Progress: "
										+ (progress / progressGoal)
										* 100
										+ " / " + "100");
						System.out.println(progress + " - " + progressGoal);

						allImagesList.add(tempImage);
					}

					tempNode = new FolderNode(null, id,
							subFolderIdAndName.get(id));

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

		};

		new Thread(task).start();

	}

	private static void getImagesFromFolderAndDown(int id) {

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

	public static ArrayList<FolderNode> traverse(FolderNode node) {

		ArrayList<FolderNode> returnList = new ArrayList<>();

		traverseRecursive(node, returnList);

		return returnList;

	}

	private static void traverseRecursive(FolderNode node,
			ArrayList<FolderNode> returnList) {

		returnList.add(node);

		for (FolderNode child : node.getChildren()) {
			traverseRecursive(child, returnList);
		}
	}

	private static void traverseUpRecursiveTreeItems(TreeItem<FolderNode> node) {

		node.setExpanded(true);

		if (node.getParent() != null) {
			traverseUpRecursiveTreeItems(node.getParent());
		}
	}

	private static FolderNode findFolderById(FolderNode node, int id) {

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

	public static void getNextImageInImageList(OneImage image) {

		int indexOfCurrentImage = imagesInThisFolderAndDown.indexOf(image);

		indexOfCurrentImage++;

		if (indexOfCurrentImage >= imagesInThisFolderAndDown.size()) {
			indexOfCurrentImage = 0;
		}

		currentImage = imagesInThisFolderAndDown.get(indexOfCurrentImage);

	}

	public static void getPrevImageInImageList(OneImage image) {

		int indexOfCurrentImage = imagesInThisFolderAndDown.indexOf(image);

		indexOfCurrentImage--;

		if (indexOfCurrentImage < 0) {
			indexOfCurrentImage = imagesInThisFolderAndDown.size() - 1;
		}

		currentImage = imagesInThisFolderAndDown.get(indexOfCurrentImage);

	}

	public static TreeView<FolderNode> getTree() {
		return treeView;
	}

	public static TreeItem<FolderNode> getTreeViewRoot() {
		return treeItemRoot;
	}

	public static void setManyViewController(
			ManyViewController manyViewControllerr) {
		manyViewController = manyViewControllerr;
	}

	public static FolderNode getCurrentFolder() {
		return currentFolder;
	}

	public static OneImage getCurrentImage() {
		return currentImage;
	}

	public static void setCurrentImage(OneImage currenttImage) {
		currentImage = currenttImage;

	}

	public static Main getMain() {
		System.out.println("in foldertree");
		return main;
	}

	public static void setMain(Main mainn) {
		main = mainn;
	}

	public static ArrayList<OneImage> getImagesInThisFolderAndDown() {
		return imagesInThisFolderAndDown;
	}

	public static ArrayList<OneImage> getAllImagesList() {
		return allImagesList;
	}

	public static Task<Void> getTask() {
		return task;
	}

	public static boolean isReady() {
		return ready;
	}

}
