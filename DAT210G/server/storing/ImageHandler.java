package storing;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import net.coobird.thumbnailator.Thumbnails;

public class ImageHandler {

	public static ImageHandler IMAGE_HANDLER = new ImageHandler();

	private static final int THUMBNAIL_SIZE = 100, MEDIUM_SIZE = 250;
	public Path defaultPath;

	private ImageHandler() {
		init();

	}

	private void init() {

		defaultPath = Paths.get(".\\img");

		try {
			ensureLocation();
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		}
	}

	/**
	 * Soerger for at noedvendige mapper for lagring av bilder eksisterer, lager
	 * dem om noedvendig.
	 * 
	 * 
	 * @throws FileNotFoundException
	 */
	private void ensureLocation() throws FileNotFoundException {
		File imageFolder = new File(defaultPath.toString());
		File thumbFolder = new File(defaultPath + "\\thumb");
		File mediumFolder = new File(defaultPath + "\\medium");
		File fullFolder = new File(defaultPath + "\\full");

		/*
		 * exists() sjekker om mappen faktisk eksisterer paa maskinen, om den
		 * ikke gjoer det har vi ikke grunnlag til aa lagre bildene, og da skal
		 * det oppstaa en feilmelding
		 */

		boolean success = (imageFolder.exists() && thumbFolder.exists()
				&& mediumFolder.exists() && fullFolder.exists());

		System.out.println("Folders exist: " + success);

		/*
		 * mkdirs() lager mappene som spesifisert ovenfor, om en mappe ikke blir
		 * lagd kan det vaere at den allerede eksisterer, eller av en annen
		 * grunn feiler.
		 */
		if (!success) {
			success = (thumbFolder.mkdirs() && mediumFolder.mkdirs() && fullFolder
					.mkdirs());

			System.out.println("Folders were made: " + success);
		}
		if (!success)
			throw new FileNotFoundException(
					"Image-containing folder was not found and could not be made!");
	}

	public BufferedImage load(String filepath) {
		System.out.println("ImageHandler loading file: " + defaultPath
				+ filepath);
		File imageFile = new File(defaultPath + filepath);
		if (imageFile.exists())
			return load(imageFile);
		return null;
	}

	/**
	 * Metoden forsoeker aa lese og laste inn en bildefil med navn filename og
	 * sti defaultPath og returnerer et BufferedImage.
	 * 
	 * 
	 * @Param file Filen som skal leses og lastes inn.
	 * 
	 * @return Returnerer et BufferedImage av bildet.
	 */

	public BufferedImage load(File file) {
		BufferedImage bufferedImage = null;
		try {
			bufferedImage = ImageIO.read(file);
			System.out.println("Imagefile " + file.getName()
					+ " was found and successfully read");
		} catch (IOException e) {
			e.printStackTrace();
		}

		return bufferedImage;
	}

	/**
	 * Metoden oppretter en instans av serverImage med gitt ID, filtype og et
	 * bilde
	 * 
	 * @param ID
	 *            ID tildelt av databasen
	 * @param fileExtension
	 *            Filtypen til bildet (jpeg, png, ... )
	 * @param bufferedImage
	 *            Bildet som et bufferedImage
	 */

	public boolean saveFullImageToFile(int ID, File imageFile) {
		File destFile = new File(defaultPath + "\\full\\" + ID + "."
				+ imageFile.getName().toString().split("[.]")[1]);

		return copyFile(imageFile, destFile);
	}

	private boolean copyFile(File sourceFile, File destinationFile) {
		InputStream inStream = null;
		OutputStream outStream = null;

		try {
			inStream = new FileInputStream(sourceFile);
			outStream = new FileOutputStream(destinationFile);

			byte[] buffer = new byte[1024];

			int length;

			while ((length = inStream.read(buffer)) > 0) {
				outStream.write(buffer, 0, length);
			}

			inStream.close();
			outStream.close();

			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return false;
		}

	}

	public boolean saveThumbnailImageToFile(int ID, File file) {
		try {
			File destFile = new File(defaultPath + "\\thumb\\" + ID + "."
					+ file.getName().toString().split("[.]")[1]);
			Thumbnails.of(file).size(THUMBNAIL_SIZE, THUMBNAIL_SIZE)
					.toFile(destFile);
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	public boolean saveMediumImageToFile(int ID, File file) {
		try {
			File destFile = new File(defaultPath + "\\medium\\" + ID + "."
					+ file.getName().toString().split("[.]")[1]);
			Thumbnails.of(file).size(MEDIUM_SIZE, MEDIUM_SIZE).toFile(destFile);
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	// Tester metodene:

	public static void main(String[] args) {
		File[] directory = IMAGE_HANDLER.defaultPath.toFile().listFiles();
		int id = 0;
		for (File file : directory) {
			if (file.getName().endsWith(".png")) {
				System.out.println(IMAGE_HANDLER.saveFullImageToFile(id, file));
				System.out.println(IMAGE_HANDLER
						.saveMediumImageToFile(id, file));
				System.out.println(IMAGE_HANDLER.saveThumbnailImageToFile(id,
						file));
				id++;
			}
		}

	}
}
