package logic;

public class RequestClient {
	private String order;
	private int imageId;
	private String detail;

	public RequestClient(String order){
		this.order = order;
	}
	public RequestClient(String order, int imageId){
		this.order = order;
		this.imageId = imageId;
	}
	public RequestClient(String order, String detail){
		this.order = order;
		this.detail = detail;
	}
	public RequestClient(String order, int imageId, String detail){
		this.order = order;
		this.imageId = imageId;
		this.detail = detail;
	}	
}
