package storing;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import storing.ReadFromDatabase.IsNotOnlyChildObject;

public class testSkrivBilde {

	public static void main(String[] args) {


//				PictureDb p = new PictureDb("det er en test", "tesdatsd5", 3, "2012-10-10 10:10:10", "ds", "ds", "ds", 10);
//				boolean wr = WriteToDatabase.writeOnePic(p);
//				System.out.println(wr);


//				ParentFolderDb par = ReadFromDatabase.getFolderInfo(4);
//				System.out.println(par.toString());

//				List<ParentFolderDb> d = ReadFromDatabase.getFolderAndSubFolderInfo(2);
//				for (ParentFolderDb p: d) {
//					System.out.println(p.getFolderId() + ", " + p.getName());
//				}

//				int[] ar = ReadFromDatabase.getFolderAndSubFolderId(2);
//				for (int i: ar) {
//					System.out.println(i);
//				}

//				List<PictureDb> alleBilderFraFolderOgSubfolder = ReadFromDatabase.getPicturesInFolderAndSubFolderInfo(2);
//				for (PictureDb i: alleBilderFraFolderOgSubfolder) {
//					System.out.println(i.getId());
//				}


//				int[] getPic = ReadFromDatabase.getPicturesInFolderAndSubFolderId(2);
//				for (int p: getPic) {
//					System.out.println(p);
//				}


		//TODO: hvordan vi adder folders
//				ParentFolderDb nyFolder = new ParentFolderDb("undersommer", "\\img\\sommer\\undersommer\\", 1);
//				
//				IsNotOnlyChildObject onlyChild = ReadFromDatabase.isFolderOnlyChild(3);
//				System.out.println(onlyChild.isOnlyChild());
//				
//				if (onlyChild.isOnlyChild) {
//					boolean writ = WriteToDatabase.addFolderAsAnOnlyChildToFolder(nyFolder, 3);
//					System.out.println(writ + ", only child");
//				} else {
//					boolean writ = WriteToDatabase.addFolderInAFolderWithOtherChildren(nyFolder, onlyChild.getLeftChildId());
//					System.out.println(writ + ", sibling");
//				}


		//		TODO: legg til i ensure folder fra kjelli
//				boolean imgFolder = WriteToDatabase.ensureImgFolderDatabase();
//				System.out.println(imgFolder);


//				boolean del = DeleteFromDatabase.deleteFolderAndContent(1);
//				System.out.println(del);

//				List<PictureDb> li = ReadFromDatabase.getPicturesBasedOnDate(" ", 1);
//				for (PictureDb p: li) {
//					System.out.println(p.getId());
//				}

//				List<TreeMenuNode> treet = ReadFromDatabase.getTreeForMenu();
//				System.out.println(treet.get(0).getRoot().getName());
//				System.out.println("  " + treet.get(0).getChildren().get(0).getRoot().getName());
//				System.out.println("    " + treet.get(0).getChildren().get(0).getChildren().get(0).getRoot().getName());
//				System.out.println("    " + treet.get(0).getChildren().get(0).getChildren().get(1).getRoot().getName());
//				System.out.println("      " + treet.get(0).getChildren().get(0).getChildren().get(1).getChildren().get(0).getRoot().getName());
//				System.out.println("  " + treet.get(0).getChildren().get(1).getRoot().getName());
//				for (TreeMenuNode tn: treet) {
//					System.out.println(tn.getRoot().getName() + ":");
//					System.out.println(tn.printChildren());
//		
//				}
	}

}
