package communication;

import java.net.*;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;

public class HttpClient {
	private String host =  "localhost";
	private int port = 19999;
	private Socket connection;
	private boolean connected = false;

	public HttpClient(){
		System.out.println("Client started");
		connect();
	}
	public boolean getConnected(){
		return connected;
	}
	private void connect(){
		try {
			InetAddress address = InetAddress.getByName(host);
			connection = new Socket(address, port);
			connected = true;
		} catch (UnknownHostException e) {
			connected = false;
			System.err.println("Not able to connect to " + host);
			return;
		} catch (IOException e) {
			connected = false;
			System.err.println("Not able to connect to " + host);
			return;
		}
	}
	public void sendData(String outGoingString){
		try {
			BufferedOutputStream bufferOutStream = new BufferedOutputStream(connection.getOutputStream());
			OutputStreamWriter outStreamWriter = new OutputStreamWriter(bufferOutStream);
			outStreamWriter.write(outGoingString + (char) 13);
			outStreamWriter.flush();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void closeConnection(){
		try {
			connection.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public String receiveData(){
		StringBuffer inComingString = new StringBuffer();
		try {
			BufferedInputStream bis = new BufferedInputStream(connection.getInputStream());
			InputStreamReader isr = new InputStreamReader(bis);
			int character;
			while ( (character = isr.read()) != 13){
				inComingString.append( (char) character);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return inComingString.toString();
	}
	public BufferedImage receiveImage(){
		BufferedImage image = null;
		try {
			image = ImageIO.read(connection.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}
}


