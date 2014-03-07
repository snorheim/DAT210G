package gui.model;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;

import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;

public class FolderTree {

	private TreeView<String> tree;

	
	public void createTree() {
		
		
		
		
		TreeItem<String> rootItem = new TreeItem<String> ("Inbox");
		
		rootItem.setExpanded(true);
		
		for (int i = 1; i < 6; i++) {
			
			TreeItem<String> item = new TreeItem<String> ("Message" + i); 
			
			rootItem.getChildren().add(item);
			
		}        
		tree = new TreeView<String> (rootItem);

	}


	public TreeView<String> getTree() {
		return tree;
	}
	
	
}
