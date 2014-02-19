package storing;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.sun.jmx.snmp.defaults.DefaultPaths;


public class ImageHandler {

	public static ImageHandler IMAGE_HANDLER = new ImageHandler();
	// TODO: Sett egen defaultPath, kompatibilitet med andre OS?
	public Path defaultPath= Paths.get(".\\img");
	public ArrayList<ServerImage> imageList = new ArrayList<ServerImage>();;

	public ImageHandler() {
		init();

	}

	private void init() { 
		
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
	public void fetchImages() {
		File directory = new File(defaultPath.toString());
		for (File imageFile : directory.listFiles()) {
			// TODO: andre filer enn png
			System.out.println(imageFile.getName());
			if (imageFile.getName().endsWith(".png")) {

				BufferedImage bufferedImage = load(imageFile);
				createServerImage(imageList.size(), "png", bufferedImage);
			}
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
		System.out.println("File path: " + filepath);
		File imageFile = new File(filepath);
		if (imageFile.exists())
			return load(imageFile);
		System.out.println("yolo swag");
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

	private static BufferedImage load(File file) {
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

	public void createServerImage(int ID, String fileExtension,
			BufferedImage bufferedImage) {
		ServerImage serverImage;
		serverImage = new ServerImage(ID, fileExtension,
				bufferedImage, defaultPath);
		imageList.add(serverImage);
	}

	/**
	 * Lagrer alle bildene i tre versjoner; original stoerrelse, medium
	 * stoerrelse og thumbnail stoerrelse. Referer til THUMBNAIL_WIDTH,
	 * THUMBNAIL_HEIGHT, MEDIUM_WIDTH, MEDIUM_HEIGHT i ServerImage klassen for
	 * dimensjon
	 * 
	 * Alle bildene blir lagret i hver sin mappe (full/, medium/ og thumb/)
	 */

	public boolean saveAll() {
		for (ServerImage serverImage : imageList) {
			if (!serverImage.saveImageToFile())
				return false;
			if (!serverImage.saveMediumImageToFile())
				return false;
			if (!serverImage.saveThumbnailImageToFile())
				return false;
		}
		return true;
	}

	// Tester metodene:

	public static void main(String[] args) {
		ImageHandler imageHandler = new ImageHandler();
		System.out.println();
		System.out.println("Fetching images:");
		imageHandler.fetchImages();

		int size;
		System.out.println("Total images: "
				+ (size = imageHandler.imageList.size()));
		System.out.println("\nImages:");
		for (int i = 0; i < size; i++) {
			System.out.println(imageHandler.imageList.get(i));
		}

		imageHandler.saveAll();

	}
}
