package communication;

import com.google.gson.*;
import logic.RequestServer;
import logic.ResponseServer;

public class JsonServer {
	private HttpServer httpServer;
	private String inComingString;
	private RequestServer inComingRequest;
	private String outGoingString;
	private ResponseServer outGoingResponse;
	private Gson json;

	public JsonServer(HttpServer httpServer, String inComingString){
		this.httpServer = httpServer;
		this.inComingString = inComingString;
		json = new Gson();
		instantiateInComingRequest();
		inComingRequest.setJsonServer(this);
		inComingRequest.execute();
	}
	public RequestServer getInComingRequest(){
		return inComingRequest;
	}
	public String getOutGoingString(){
		return outGoingString;
	}
	public HttpServer getHttpServer(){
		return httpServer;
	}
	public void setOutGoingResponse(ResponseServer response){
		this.outGoingResponse = response;
	}
	private void instantiateInComingRequest(){
		inComingRequest = json.fromJson(inComingString,logic.RequestServer.class);
	}
	public void encodeOutGoingResponse(){
		outGoingString = json.toJson(outGoingResponse);
		httpServer.sendData(outGoingString);
	}
}
