package storing;

import java.util.ArrayList;
import java.util.List;

import storing.ReadFromDatabase.IsNotOnlyChildObject;

public class testSkrivBilde {

	public static void main(String[] args) {

//		ParentFolderDb p = new ParentFolderDb(2, "2013", "\\img\\jul\\2013\\");
//		boolean fold = WriteToDatabase.addNewFolder(p);
//		System.out.println(fold);
		
//		PictureDb p = new PictureDb("tes6", "tesdatsd5", 3, "2012-10-10 10:10:10", "ds", "ds", "ds", 3);
//		boolean wr = WriteToDatabase.writeOnePic(p);
//		System.out.println(wr);
		
//		int[] t = ReadFromDatabase.getFoldersInAFolder(2);
//		for (int p: t) {
//			System.out.println(p);
//		}
		
//		ParentFolderDb par = ReadFromDatabase.getParentInfo(4);
//		System.out.println(par.getFolderId() + ", " + par.getParentId() + ", " + par.getFolderName() + ", " + par.getPath());
		
//		ParentFolderDb par = ReadFromDatabase.getFolderInfo(4);
//		System.out.println(par.toString());
//		ParentFolderDb folder = new ParentFolderDb("julen 3001", "\\img\\jul\\julen 3001\\");
//		boolean t = WriteToDatabase.addFolderAsAnOnlyChildToFolder(folder, "jul");
//		System.out.println(t);
		
//		List<ParentFolderDb> d = ReadFromDatabase.getFolderAndSubFolderInfo(2);
//		for (ParentFolderDb p: d) {
//			System.out.println(p.getFolderId() + ", " + p.getName());
//		}
		
//		int[] ar = ReadFromDatabase.getFolderAndSubFolderId(2);
//		for (int i: ar) {
//			System.out.println(i);
//		}
		
//		int[] alleBilderFraFolderOgSubfolder = ReadFromDatabase.getPicturesInFolderAndSubFolder(2);
//		for (int i: alleBilderFraFolderOgSubfolder) {
//			System.out.println(i);
//		}
		
		
//		int[] getPic = ReadFromDatabase.getPicturesInFolderAndSubFolder("jul");
//		for (int p: getPic) {
//			System.out.println(p);
//		}
		
		
		//TODO: hvordan vi adder folders
//		ParentFolderDb nyFolder = new ParentFolderDb("sidenavlavestyolo", "\\img\\jul\\yolo\\yolo\\sidenavlavestyolo\\");
//		
//		IsNotOnlyChildObject onlyChild = ReadFromDatabase.isFolderOnlyChild(7);
//		System.out.println(onlyChild.isOnlyChild());
//		
//		if (onlyChild.isOnlyChild) {
//			boolean writ = WriteToDatabase.addFolderAsAnOnlyChildToFolder(nyFolder, 7);
//			System.out.println(writ + ", only child");
//		} else {
//			boolean writ = WriteToDatabase.addFolderInAFolderWithOtherChildren(nyFolder, onlyChild.getLeftChildId());
//			System.out.println(writ + ", sibling");
//		}
		
		
//		TODO: legg til i ensure folder fra kjelli
//		ParentFolderDb p = new ParentFolderDb("img", "\\img\\", 1, 2);
//		boolean imgFolder = WriteToDatabase.ensureImgFolderDatabase(p);
//		System.out.println(imgFolder);
		
		
	}

}
