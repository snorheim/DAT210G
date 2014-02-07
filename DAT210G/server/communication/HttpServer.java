package communication;

import java.net.*;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;

public class HttpServer implements Runnable {

	private Socket connection;
	private int id;

	public HttpServer(Socket socket, int id) {
		this.connection = socket;
		this.id = id;
	}
	public int getId(){
		return id;
	}
	public void run() {
		try {
			BufferedInputStream bufferInStream = new BufferedInputStream(connection.getInputStream());
			InputStreamReader inStreamReader = new InputStreamReader(bufferInStream);
			int character;
			StringBuffer inComingString = new StringBuffer();
			while((character = inStreamReader.read()) != 13) {
				inComingString.append((char)character);
			}
			@SuppressWarnings("unused")
			JsonServer json = new JsonServer(this,inComingString.toString());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				connection.close();
			}
			catch (IOException e){
				e.printStackTrace();
			}
		}
	}
	public void sendData(String outGoingString){
		try {
			BufferedOutputStream bufferOutputStream = new BufferedOutputStream(connection.getOutputStream());
			OutputStreamWriter outStreamWriter = new OutputStreamWriter(bufferOutputStream);
			outStreamWriter.write(outGoingString + (char) 13);
			outStreamWriter.flush();
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
	public static void main(String[] args) {
		int port = 19999;
		int count = 0;
		ServerSocket serverConnection = null;
		try {
			serverConnection = new ServerSocket(port);
			System.out.println("HTTP server initialized");
			while (true) {
				Socket connection = serverConnection.accept();
				Runnable runnable = new HttpServer(connection, ++count);
				Thread thread = new Thread(runnable);
				thread.start();
			}
		} catch (IOException e) {
			System.err.println("Not able to open server on port " + port);
		}
		finally {
			try {
				serverConnection.close();
			} catch (IOException e) {
				System.err.println("Not able to close server on port " + port);
			} catch (Exception e) {
				System.err.println("Not able to close server on port " + port);
			}
			
		}
	}
}
