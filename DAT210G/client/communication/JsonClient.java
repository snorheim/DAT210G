package communication;

import java.awt.image.BufferedImage;
import com.google.gson.*;
import logic.RequestClient;
import logic.ResponseClient;

public class JsonClient {
	private RequestClient outGoingRequest;
	private ResponseClient inComingResponse;
	private Gson json;
	private HttpClient httpClient;

	public JsonClient(RequestClient request){
		this.outGoingRequest = request;
		json = new Gson();
		httpClient = new HttpClient();
	}
	public boolean sendJsonToServer(){
		if (httpClient.getConnected()){
			httpClient.sendData(json.toJson(outGoingRequest));
			return true;
		}
		return false;
	}
	public ResponseClient receiveJsonFromServer(){
		String str = httpClient.receiveData();
		inComingResponse = json.fromJson(str,logic.ResponseClient.class);
		return inComingResponse;
	}
	public BufferedImage receiveImageFromServer(){
		return httpClient.receiveImage();
	}
	public void sendImageToServer(BufferedImage image, String type){
		httpClient.sendImage(image, type);
	}
	public void sendFileToServer(String filePath) throws Exception{
		httpClient.sendFile(filePath);
	}
	public void closeHttpConnection(){
		httpClient.closeConnection();
	}
}
