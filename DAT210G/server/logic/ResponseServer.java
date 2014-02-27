package logic;

public class ResponseServer {
	private boolean success;
	private int imageId;
	private int[] imageIdArray;
	private String[] stringArray;
	
	public ResponseServer(boolean success){
		this.success = success;
	}
	public ResponseServer(boolean success, int imageId){
		this.success = success;
		this.imageId = imageId;
	}
	public ResponseServer(boolean success, int[] imageIdArray){
		this.success = success;
		this.imageIdArray = imageIdArray;
	}
	public ResponseServer(boolean success, String[] stringArray){
		this.success = success;
		this.stringArray = stringArray;
	}
	public ResponseServer(boolean success, int imageId, String[] stringArray){
		this.success = success;
		this.imageId = imageId;
		this.stringArray = stringArray;
	}
}
