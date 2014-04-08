package storing;



public class testSkrivBilde {

	public static void main(String[] args) {
		String[] tags = ReadFromDatabase.getTagsStartingWith("l");
		for (String s: tags) {
			System.out.println(s);
		}
	}
}
