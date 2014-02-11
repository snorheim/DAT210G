package logic;

public class ResponseClient {
	private boolean success;
	private int imageId;
	private int[] imageIdArray;
	private String[] stringArray;

	public ResponseClient(boolean success){
		this.success = success;
	}
	public ResponseClient(boolean success, int imageId){
		this.success = success;
		this.imageId = imageId;
	}
	public ResponseClient(boolean success, int[] imageIdArray){
		this.success = success;
		this.imageIdArray = imageIdArray;
	}
	public ResponseClient(boolean success, String[] stringArray){
		this.success = success;
		this.stringArray = stringArray;
	}
	public boolean getSuccess(){
		return success;
	}
	public int getImageId(){
		return imageId;
	}
	public int[] getImageIdArray(){
		return imageIdArray;
	}
	public String[] getStringArray(){
		return stringArray;
	}
	public String toString(){
		String str = "Success: " + success + ", imageId: " + imageId + ", imageIdArray: ";
		if (imageIdArray == null){
			str += "NULL,";
		}
		else{
			for (int i = 0; i < imageIdArray.length; i++){
				str += imageIdArray[i] + ",";
			}
		}
		str += " stringArray: ";
		if (stringArray == null){
			str += "NULL";
		}
		else{
			for (int i = 0; i < stringArray.length ; i++){
				str += stringArray[i] + ",";
			}
		}
		return str;
	}
}
