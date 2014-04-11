package communication;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import javax.imageio.ImageIO;

import logic.Loggy;
import storing.FileWatcher;
import storing.HibernateUtil;
import storing.ImageHandler;

public class HttpServer implements Runnable {

	private Socket connection;
	private int id;
	private int count = 0;

	public HttpServer(Socket socket, int id) {
		this.connection = socket;
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void run() {
		try {

			BufferedInputStream bufferInStream = new BufferedInputStream(
					connection.getInputStream());
			InputStreamReader inStreamReader = new InputStreamReader(
					bufferInStream);
			int character;
			StringBuffer inComingString = new StringBuffer();
			while ((character = inStreamReader.read()) != 13) {
				inComingString.append((char) character);
			}
			log("Server received: " + inComingString.toString());
			@SuppressWarnings("unused")
			JsonServer json = new JsonServer(this, inComingString.toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static void log(String string) {
		Loggy.log(string, Loggy.SERVER);
	}

	public void sendData(String outGoingString) {
		try {
			BufferedOutputStream bufferOutputStream = new BufferedOutputStream(
					connection.getOutputStream());
			OutputStreamWriter outStreamWriter = new OutputStreamWriter(
					bufferOutputStream);
			outStreamWriter.write(outGoingString + (char) 13);
			outStreamWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendImage(BufferedImage image, String type) {
		try {
			ImageIO.write(image, type, connection.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public BufferedImage receiveImage() {
		BufferedImage image = null;
		try {
			image = ImageIO.read(connection.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}

	public File receiveFile(String fileName) throws Exception {
		BufferedInputStream bufferedInputStream = new BufferedInputStream(
				connection.getInputStream());
		String fileFullPath = ImageHandler.getDefaultPathString() + fileName;
		FileWatcher.ignore(new File(fileFullPath));
		byte[] byteSize = new byte[4];
		int offset = 0;
		while (offset < byteSize.length) {
			int bytesRead = bufferedInputStream.read(byteSize, offset,
					byteSize.length - offset);
			offset += bytesRead;
		}
		int fileSize;
		fileSize = (int) (byteSize[0] & 0xff) << 24
				| (int) (byteSize[1] & 0xff) << 16
				| (int) (byteSize[2] & 0xff) << 8 | (int) (byteSize[3] & 0xff);
		if (fileSize < 1 | fileSize > 20000000) {
			throw new Exception();
		}
		byte[] data = new byte[8 * 1024];
		int bytesToRead;
		FileOutputStream fileOutputStream = new FileOutputStream(fileFullPath);
		BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(
				fileOutputStream);
		while (fileSize > 0) {
			if (fileSize > data.length)
				bytesToRead = data.length;
			else
				bytesToRead = fileSize;
			int bytesRead = bufferedInputStream.read(data, 0, bytesToRead);
			if (bytesRead > 0) {
				bufferedOutputStream.write(data, 0, bytesRead);
				fileSize -= bytesRead;
				// System.out.println("Left to receive: " + fileSize +
				// " bytes");
			}
		}
		bufferedOutputStream.close();
		return new File(fileFullPath);
	}

	public static void main(String[] args) {
		new ImageHandler();
		new FileWatcher(ImageHandler.getDefaultPath());
		int port = 19999;
		int count = 0;
		ServerSocket serverConnection = null;
		try {
			serverConnection = new ServerSocket(port);
			while (true) {
				Socket connection = serverConnection.accept();
				Runnable runnable = new HttpServer(connection, ++count);
				Thread thread = new Thread(runnable);
				thread.start();
			}
		} catch (IOException e) {
			log("Not able to open server on port " + port);
		} finally {
			try {
				serverConnection.close();
				HibernateUtil.shutdown();
			} catch (IOException e) {
				log("Not able to close server on port " + port);
			} catch (Exception e) {
				log("Not able to close server on port " + port);
			}

		}
	}

}
