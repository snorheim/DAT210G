package storing;



public class testSkrivBilde {

	public static void main(String[] args) {
		int[] tags = ReadFromDatabase.getPicturesInFolderAndSubFolderId(1);
		for (int s: tags) {
			System.out.println(s);
		}
	}
}
