package storing;



public class testSkrivBilde {

	public static void main(String[] args) {
		int[] i = ReadFromDatabase.getFolderAndSubFolderId(1);
		for (int s: i) {
			System.out.println(s);
		}
		
		int[] t = ReadFromDatabase.getFolderAndSubFolderId(1);
		for (int r: t) {
			System.out.println(r);
		}
	}
}
