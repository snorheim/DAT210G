package logic;

public class RequestClient {
	private String order;
	private int id;
	private String detail;

	public RequestClient(String order){
		this.order = order;
	}
	public RequestClient(String order, int id){
		this.order = order;
		this.id = id;
	}
	public RequestClient(String order, String detail){
		this.order = order;
		this.detail = detail;
	}
	public RequestClient(String order, int id, String detail){
		this.order = order;
		this.id = id;
		this.detail = detail;
	}
}