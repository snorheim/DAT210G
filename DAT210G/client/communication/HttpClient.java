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
			System.out.println("Client connected to " + host + ":" + port);
		} catch (UnknownHostException e) {
			connected = false;
			System.err.println("Not able to connect to " + host + ":" + port);
			return;
		} catch (IOException e) {
			connected = false;
			System.err.println("Not able to connect to " + host + ":" + port);
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
	public void sendImage(BufferedImage image,String type){
		try {
			ImageIO.write(image, type, connection.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void sendFile(String filePath) throws Exception {
		OutputStream outputStream = connection.getOutputStream();
		File outGoingFile = new File(filePath);
		int fileSize = (int) outGoingFile.length();
		System.out.println("Sender fil: " + filePath + ": " + outGoingFile.length());
		if (fileSize < outGoingFile.length()){
			System.out.println("File is too big");
			throw new IOException("File is too big");
		}
		byte[] byteSize = new byte[4];
		byteSize[0] = (byte) ((fileSize & 0xff000000) >> 24);
		byteSize[1] = (byte) ((fileSize & 0x00ff0000) >> 16);
		byteSize[2] = (byte) ((fileSize & 0x0000ff00) >> 8);
		byteSize[3] = (byte) (fileSize & 0x000000ff);
		outputStream.write(byteSize, 0, 4);

		boolean noMemoryLimitation = true;

		FileInputStream fileInputStream = new FileInputStream(outGoingFile);
		BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
		try {
			if (noMemoryLimitation){
				byte[] outBuffer = new byte[fileSize];
				int bRead = bufferedInputStream.read(outBuffer, 0, outBuffer.length);
				outputStream.write(outBuffer, 0, bRead);
			}
			else {
				int bRead = 0;
				byte[] outBuffer = new byte[8 * 1024];
				while ((bRead = bufferedInputStream.read(outBuffer, 0, outBuffer.length)) > 0){
					outputStream.write(outBuffer, 0, bRead);
				}
			}
			outputStream.flush();
		}
		finally {
			bufferedInputStream.close();
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
	public void closeConnection(){
		try {
			connection.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}


