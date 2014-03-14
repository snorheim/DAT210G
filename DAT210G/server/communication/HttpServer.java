package communication;

import java.net.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

import storing.ImageHandler;

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
			System.out.println("Server received: " + inComingString.toString());
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
			System.out.println("Server sent: " + outGoingString);
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
	public BufferedImage receiveImage(){
		BufferedImage image = null;
		try {
			image = ImageIO.read(connection.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}
	public void sendImageToAndroid(String fileName) throws Exception {
		OutputStream os = connection.getOutputStream();
		File myFile = new File(fileName);
		int fSize = (int) myFile.length();
		System.out.println("Sender fil: " + fileName + ": " + myFile.length());
		if (fSize < myFile.length()){
			System.out.println("File is too big'");
			throw new IOException("File is too big.");
		}
		byte[] bSize = new byte[4];
		bSize[0] = (byte) ((fSize & 0xff000000) >> 24);
		bSize[1] = (byte) ((fSize & 0x00ff0000) >> 16);
		bSize[2] = (byte) ((fSize & 0x0000ff00) >> 8);
		bSize[3] = (byte) (fSize & 0x000000ff);
		os.write(bSize, 0, 4);

		boolean noMemoryLimitation = true;

		FileInputStream fis = new FileInputStream(myFile);
		BufferedInputStream bis = new BufferedInputStream(fis);
		try {
			if (noMemoryLimitation){
				byte[] outBuffer = new byte[fSize];
				int bRead = bis.read(outBuffer, 0, outBuffer.length);
				os.write(outBuffer, 0, bRead);
			}
			else {
				int bRead = 0;
				byte[] outBuffer = new byte[8 * 1024];
				while ((bRead = bis.read(outBuffer, 0, outBuffer.length)) > 0){
					os.write(outBuffer, 0, bRead);
				}
			}
			os.flush();
		}
		finally {
			bis.close();
		}
	}
	public File receiveFile(String fileName) throws Exception {
		BufferedInputStream bufferedInputStream = new BufferedInputStream(connection.getInputStream());
		String fileFullPath = ImageHandler.getInstance().defaultPath + "\\" + fileName;
		//TODO: funker denne ?
		ImageHandler.getInstance().dirWatch.ignore(new File(fileFullPath));
		System.out.println("Receiving image to file: " + fileFullPath);
		byte[] byteSize = new byte[4];
		int offset = 0;
		while (offset < byteSize.length) {
			int bytesRead = bufferedInputStream.read(byteSize, offset, byteSize.length - offset);
			offset += bytesRead;
		}
		int fileSize;
		fileSize = (int) (byteSize[0] & 0xff) << 24 
				| (int) (byteSize[1] & 0xff) << 16 
				| (int) (byteSize[2] & 0xff) << 8
				| (int) (byteSize[3] & 0xff);
		System.out.println("Incoming image is: " + fileSize + " bytes long");
		if (fileSize < 1 | fileSize > 20000000){
			throw new Exception();
		}
		byte[] data = new byte[8 * 1024];
		int bytesToRead;
		FileOutputStream fileOutputStream = new FileOutputStream(fileFullPath);
		BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
		while (fileSize > 0){
			if (fileSize > data.length) bytesToRead = data.length;
			else bytesToRead = fileSize;
			int bytesRead = bufferedInputStream.read(data, 0, bytesToRead);
			if (bytesRead > 0){
				bufferedOutputStream.write(data, 0, bytesRead);
				fileSize -= bytesRead;
				//System.out.println("Left to receive: " + fileSize + " bytes");
			}
		}
		bufferedOutputStream.close();
		return new File(fileFullPath);
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
