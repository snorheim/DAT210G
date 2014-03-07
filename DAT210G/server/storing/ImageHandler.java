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

	private static ImageHandler instance = null;

	private static final int THUMBNAIL_SIZE = 150, MEDIUM_SIZE = 500;
<<<<<<< HEAD
<<<<<<< HEAD
	private DirectoryWatcher dirWatch;
=======
>>>>>>> origin/master
=======
>>>>>>> origin/master
	public Path defaultPath;

	private ImageHandler() {
		init();
	}

	private void init() {
		log("Initializing...");

		defaultPath = Paths.get(".\\img");
		log("Default path: " + defaultPath.normalize().toAbsolutePath());
		try {
			log("Default path ready: \t" + ensureLocation());
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		}
		dirWatch = new DirectoryWatcher(defaultPath);
	}

	private void log(String string) {
		System.out.println("IH@ " + string);
	}

	/**
	 * Soerger for at noedvendige mapper for lagring av bilder eksisterer, lager
	 * dem om noedvendig.
	 * 
	 * 
	 * @throws FileNotFoundException
	 */
	private boolean ensureLocation() throws FileNotFoundException {

		if (defaultPath.toFile().exists()) {
			return true;
		} else {
			// Sørg for at databasen er tom.
			return defaultPath.toFile().mkdirs();
		}
	}

	/**
	 * Metoden forsoeker aa lese og laste inn en bildefil med sti relativ til
	 * defaultpath og returnerer et BufferedImage.
	 * 
	 * @Param file Bildefilen som skal leses og lastes inn.
	 * 
	 * @return Returnerer et BufferedImage av bildet.
	 */

	public BufferedImage load(String filepath) {
		log("Loading imagefile: " + defaultPath + filepath);
		File imageFile = new File(defaultPath + filepath);
		if (imageFile.exists()) {
			return load(imageFile);
		}
		return null;
	}

	/**
	 * Metoden forsoeker aa lese og laste inn en bildefil og returnerer et
	 * BufferedImage.
	 * 
	 * @Param file Bildefilen som skal leses og lastes inn.
	 * 
	 * @return Returnerer et BufferedImage av bildet.
	 */

	public BufferedImage load(File file) {
		BufferedImage bufferedImage = null;
		try {
			bufferedImage = ImageIO.read(file);
			log("Imagefile " + file.getName()
					+ " was found and successfully read");
		} catch (IOException e) {
			e.printStackTrace();
		}

		return bufferedImage;
	}

	// TODO: Metode som kan erstatte en databasespørring KUN hvis
	// mappestrukturen er fast
	/**
	 * Metoden forsoeker aa lese en bildefil(fullstoerrelse) basert paa en
	 * spesifikk bilde-ID og returnerer et BufferedImage
	 * 
	 * @Param id Bilde-IDen til bildet som skal lastes inn
	 * 
	 * @return Returnerer et BufferedImage av bildet.
	 */

	public BufferedImage getFullImage(int id) {
		BufferedImage bufferedImage = null;
		File imageFile = getImageWithID(id);
		if (imageFile == null)
			return null;

		String extension = "." + imageFile.getName().split("[.]")[1];

		bufferedImage = load("\\full\\" + id + extension);

		return bufferedImage;
	}

	// TODO: Metode som kan erstatte en databasespørring KUN hvis
	// mappestrukturen er fast

	/**
	 * Metoden forsoeker aa lese en bildefil(mediumstoerrelse) basert paa en
	 * spesifikk bilde-ID og returnerer et BufferedImage
	 * 
	 * @Param id Bilde-IDen til bildet som skal lastes inn
	 * 
	 * @return Returnerer et BufferedImage av bildet.
	 */

	public BufferedImage getMediumImage(int id) {
		BufferedImage bufferedImage = null;
		File imageFile = getImageWithID(id);
		if (imageFile == null)
			return null;

		String extension = "." + imageFile.getName().split("[.]")[1];

		bufferedImage = load("\\medium\\" + id + extension);

		return bufferedImage;
	}

	// TODO: Metode som kan erstatte en databasespørring KUN hvis
	// mappestrukturen er fast

	/**
	 * Metoden forsoeker aa lese en bildefil(thumbnail) basert paa en spesifikk
	 * bilde-ID og returnerer et BufferedImage
	 * 
	 * @Param id Bilde-IDen til bildet som skal lastes inn
	 * 
	 * @return Returnerer et BufferedImage av bildet.
	 */

	public BufferedImage getThumbnailImage(int id) {
		BufferedImage bufferedImage = null;
		File imageFile = getImageWithID(id);
		if (imageFile == null)
			return null;

		String extension = "." + imageFile.getName().split("[.]")[1];

		bufferedImage = load("\\thumb\\" + id + extension);

		return bufferedImage;
	}

	/**
	 * Hjelpemetode for aa kopiere en fil til en ny plassering
	 * 
	 * @param sourceFile
	 *            Filen som skal bli kopiert
	 * @param destinationFile
	 *            Den kopierte filens nye destinasjon
	 * @return Returnerer sann om kopieringen lykkes
	 */

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

	/**
	 * Metoden lagrer en gitt midlertidig bildefil i mappen for
	 * fullstoerrelse-bilder
	 * 
	 * @param ID
	 *            ID tildelt av databasen, ender opp som filnavn for den
	 *            nylagrede bildefilen
	 * @param imageFile
	 *            Midlertidig bildefil-objekt som skal lagres i mappen for
	 *            fullstoerrelse-bilder
	 */

	public boolean saveFullImageToFile(int ID, File imageFile) {
		File destFile = new File(defaultPath + "\\full\\" + ID + "."
				+ imageFile.getName().toString().split("[.]")[1]);
		boolean wasSuccessful = copyFile(imageFile, destFile);
		if (wasSuccessful)
			return true;
		else
			log("Could not save fullsized image");
		return false;
	}

	/**
	 * Metoden endrer stoerrelse paa, og lagrer en gitt midlertidig bildefil i
	 * mappen for mediumstoerrelse-bilder
	 * 
	 * @param ID
	 *            ID tildelt av databasen, ender opp som filnavn for den
	 *            nylagrede bildefilen
	 * @param imageFile
	 *            Midlertidig bildefil-objekt som skal lagres i mappen for
	 *            mediumstoerrelse-bilder
	 */

	public boolean saveMediumImageToFile(int ID, File imageFile) {
		try {
			File destFile = new File(defaultPath + "\\medium\\" + ID + "."
					+ imageFile.getName().toString().split("[.]")[1]);
			Thumbnails.of(imageFile).size(MEDIUM_SIZE, MEDIUM_SIZE)
					.toFile(destFile);
			return true;
		} catch (IOException e) {
			log("Could not save mediumsized image");
			return false;
		}
	}

	/**
	 * Metoden endrer stoerrelse paa, og lagrer en gitt midlertidig bildefil i
	 * mappen for thumbnailstoerrelse-bilder
	 * 
	 * @param ID
	 *            ID tildelt av databasen, ender opp som filnavn for den
	 *            nylagrede bildefilen
	 * @param imageFile
	 *            Midlertidig bildefil-objekt som skal lagres i mappen for
	 *            thumbnailstoerrelse-bilder
	 */

	public boolean saveThumbnailImageToFile(int ID, File imageFile) {
		try {
			File destFile = new File(defaultPath + "\\thumb\\" + ID + "."
					+ imageFile.getName().toString().split("[.]")[1]);
			Thumbnails.of(imageFile).size(THUMBNAIL_SIZE, THUMBNAIL_SIZE)
					.toFile(destFile);
			return true;
		} catch (IOException e) {
			log("Could not save thumbnailsized image");
			return false;
		}
	}

	/**
	 * Metoden tar inn en ID tildelt av databasen, og en midlertidig bildefil.
	 * Det blir lagret tre versjoner av bildefilen, i hver stoerrelse, med navn
	 * ID + filtype, saa blir den midlertidige bildefilen slettet.
	 * 
	 * @param ID
	 *            Bilde-ID tildelt av databasen, ender opp som filnavn for
	 *            nylagrede bildefiler
	 * @param tempFile
	 *            Filobjektet til den midlertidige bildefilen
	 * @return Returnerer sann om alle operasjonene gjennomfoeres uten feil.
	 */

	public boolean saveAndDispose(int ID, File tempFile) {
		if (!saveFullImageToFile(ID, tempFile)) {
			return false;
		}
		if (!saveMediumImageToFile(ID, tempFile)) {
			return false;
		}
		if (!saveThumbnailImageToFile(ID, tempFile)) {
			return false;
		}

		boolean wasDisposed = tempFile.delete();

		if (wasDisposed)
			return true;
		else
			log("Could not properly dispose of the temporary file");
		return false;
	}

	/**
	 * Hjelpemetode for aa finne bildefil med bestemt ID. Metoden soeker gjennom
	 * bildene i fullstoerrelse(villkaarlig) og sjekker om bildefilen eksisterer
	 * 
	 * @param id
	 *            Bildefilens ID
	 * @return Returnerer bildefilen hvis den eksisterer, null hvis ikke
	 */

	private File getImageWithID(int id) {
		File[] directory = new File(defaultPath + "\\full\\").listFiles();
		for (File file : directory) {
			if (file.getName().startsWith(String.valueOf(id)))
				return file;
		}
		log("Could not find file with id: " + id);
		return null;
	}

	public static ImageHandler getInstance() {
		if (instance == null)
			instance = new ImageHandler();

		return instance;

	}

	// Tester metodene:

	public static void main(String[] args) {
		ImageHandler ih = ImageHandler.getInstance();
		File[] directory = ih.defaultPath.toFile().listFiles();
		int id = 0;
		for (File file : directory) {
			if (file.getName().endsWith(".png")) {
				System.out.println("Images were saved: \t\t"
						+ ih.saveAndDispose(id, file));
				id++;
			}
		}

		System.out.println();

	}
}
