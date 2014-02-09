package storing;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import javax.imageio.ImageIO;

public class ImageHandler {

	public Path defaultPath;

	public ArrayList<ServerImage> imageList;

	public ImageHandler() {
		init();

	}

	private void init() {
		defaultPath = Paths.get(".\\img");
		imageList = new ArrayList<ServerImage>();

		try {
			ensureLocation();
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		}
	}

	/**
	 * Testmetode:
	 */
	public void loadImages() {
		File file = new File(defaultPath.toString());
		for (File f : file.listFiles()) {
			if (f.getName().endsWith(".png")) {
				System.out.println(f.getName());
			}
		}
	}

	/**
	 * Sørger for at nødvendige mapper for lagring av bilder eksisterer, lager
	 * dem om nødvendig.
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
		 * mkdirs() lager mappene som spesifisert ovenfor, om en mappe ikke blir
		 * lagd kan det være at den allerede eksisterer, eller av en annen grunn
		 * feiler.
		 */
		boolean success = (thumbFolder.mkdirs() && mediumFolder.mkdirs()
				&& fullFolder.mkdirs() && imageFolder.mkdirs());

		System.out.println("Folders were made: " + success);

		/*
		 * exists() sjekker om mappen faktisk eksisterer på maskinen, om den
		 * ikke gjør det har vi ikke grunnlag til å lagre bildene, og da skal
		 * det oppstå en feilmelding
		 */

		success = (imageFolder.exists() && thumbFolder.exists()
				&& mediumFolder.exists() && fullFolder.exists());

		System.out.println("Folders exist: " + success);

		if (!success)
			throw new FileNotFoundException(
					"Image-containing folder was not found and could not be made!");
	}

	/**
	 * Metoden forsøker å lese og laste inn et bilde med navn filename og sti
	 * defaultPath og returnerer et BufferedImage.
	 * 
	 * 
	 * @Param filename Navn og filtype til bildefilen en vil hente ut. Ett
	 *        eksempel: "bilde1.jpg".
	 * 
	 * @return Returnerer et BufferedImage av bildet.
	 */

	private BufferedImage load(String filename) {
		BufferedImage bufferedImage = null;
		try {
			File file;
			file = new File(defaultPath + "\\" + filename);
			bufferedImage = ImageIO.read(file);
			System.out.println("Image was found and successfully read: "
					+ (bufferedImage != null));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return bufferedImage;
	}

	public void createServerImage(String title, String description,
			GregorianCalendar timeTaken, int rating, BufferedImage bufferedImage) {
		ServerImage serverImage;
		serverImage = new ServerImage(imageList.size(), title, description,
				timeTaken, rating, bufferedImage, defaultPath);
		imageList.add(serverImage);
	}

	public void saveAll() {
		for (ServerImage serverImage : imageList) {
			serverImage.saveFullImage(serverImage.title);
		}
	}

	public static void main(String[] args) {
		ImageHandler imageHandler = new ImageHandler();

		imageHandler.loadImages();

		int size;
		System.out.println(size = imageHandler.imageList.size());
		for (int i = 0; i < size; i++) {
			System.out.println(imageHandler.imageList.get(i));
		}

	}
}
