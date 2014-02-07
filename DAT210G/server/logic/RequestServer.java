package logic;

import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import communication.JsonServer;
import logic.ResponseServer;

public class RequestServer {
	private String order;
	private int imageId;
	private String detail;
	private JsonServer jsonServer;

	public RequestServer(String order){
		this.order = order;
	}
	public RequestServer(String order, int imageId){
		this.order = order;
		this.imageId = imageId;
	}
	public RequestServer(String order, String detail){
		this.order = order;
		this.detail = detail;
	}
	public RequestServer(String order, int imageId, String detail){
		this.order = order;
		this.imageId = imageId;
		this.detail = detail;
	}
	public void setJsonServer(JsonServer jsonServer){
		this.jsonServer = jsonServer;
	}
	public void sendJsonResponse(ResponseServer response){
		jsonServer.setOutGoingResponse(response);
		jsonServer.encodeOutGoingResponse();
	}	
	public void sendImageResponse(BufferedImage image, String type){
		jsonServer.getHttpServer().sendImage(image, type);
	}
	public BufferedImage receiveImage(){
		return jsonServer.getHttpServer().receiveImage();
	}
	public void execute(){
		Class<RequestMethods> requestMethodsClass = RequestMethods.class;	
		Class[] parameterTypes = new Class[3];
		parameterTypes[0] = RequestServer.class;
		parameterTypes[1] = int.class;
		parameterTypes[2] = String.class;
		Method thisMethod = null;
		try {
			thisMethod = requestMethodsClass.getMethod(order, parameterTypes);
		} catch (NoSuchMethodException e) {
			RequestServer.this.sendJsonResponse(new ResponseServer(false));
			return;
		} catch (SecurityException e) {
			RequestServer.this.sendJsonResponse(new ResponseServer(false));
			return;
		}
		try {
			thisMethod.invoke(this,this,imageId,detail);
		} catch (IllegalAccessException e) {
			RequestServer.this.sendJsonResponse(new ResponseServer(false));
			return;
		} catch (IllegalArgumentException e) {
			RequestServer.this.sendJsonResponse(new ResponseServer(false));
			return;
		} catch (InvocationTargetException e) {
			RequestServer.this.sendJsonResponse(new ResponseServer(false));
			return;
		}
	}
}
