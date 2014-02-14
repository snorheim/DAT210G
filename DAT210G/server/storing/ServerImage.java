package storing;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import javax.imageio.ImageIO;

public class ServerImage {

	public static final int THUMBNAIL_WIDTH = 100, THUMBNAIL_HEIGHT = 100,
			MEDIUM_WIDTH = 250, MEDIUM_HEIGHT = 250;

	public final int ID;
	public final BufferedImage bufferedImage;
	public GregorianCalendar timeTaken;
	public String title;
	public String description;
	public int rating;
	// TODO:
	public String fileextension;

	public ArrayList<String> tags;

	private Path defaultPath;

	public ServerImage(int ID, String fileExtension,
			BufferedImage bufferedImage, Path defaultPath) {
		this(ID, null, null, null, -1, bufferedImage, defaultPath);
	}

	public ServerImage(int ID, String title, String description,
			GregorianCalendar timeTaken, int rating,
			BufferedImage bufferedImage, Path defaultPath) {
		this.ID = ID;
		this.title = title;
		this.description = description;
		this.bufferedImage = bufferedImage;

		this.timeTaken = timeTaken;
		this.rating = rating;
		this.defaultPath = defaultPath;

		tags = new ArrayList<String>();
	}

	/**
	 * Metoden tar inn et bufferedImage, lager nytt bilde som skaleres etter en
	 * dimensjon som er definert i MEDIUM_WIDTH x MEDIUM_HEIGHT og returnerer
	 * det nye bildet.
	 * 
	 * @param bufferedImage
	 *            Bildet i fullstørrelse.
	 * @return Bildet i mediumstørrelse.
	 */
	private BufferedImage resizeToMedium(BufferedImage bufferedImage) {
		Image tempImage = bufferedImage.getScaledInstance(MEDIUM_WIDTH,
				MEDIUM_HEIGHT, BufferedImage.SCALE_FAST);
		BufferedImage scaledImage = toBufferedImage(tempImage);
		return scaledImage;
	}

	/**
	 * Metoden tar inn et bufferedImage, lager nytt bilde som skaleres etter en
	 * dimensjon som er definert i THUMBNAIL_WIDTH x THUMBNAIL_HEIGHT og
	 * returnerer det nye bildet.
	 * 
	 * @param bufferedImage
	 *            Bildet i fullstørrelse.
	 * @return Bildet i thumbnailstørrelse.
	 */
	public BufferedImage resizeToThumbnail(BufferedImage bufferedImage) {
		Image tempImage = bufferedImage.getScaledInstance(THUMBNAIL_WIDTH,
				THUMBNAIL_HEIGHT, BufferedImage.SCALE_FAST);
		BufferedImage scaledImage = toBufferedImage(tempImage);
		return scaledImage;
	}

	/**
	 * Lagrer en thumbnail til "<defaultpath>\thumb"
	 * 
	 * @param fileName
	 * @return
	 */

	public boolean saveThumbnailImageToFile(String fileName) {
		BufferedImage temp = resizeToThumbnail(bufferedImage);
		return saveToFile(temp, defaultPath + "\\thumb", fileName);
	}

	/**
	 * Lagrer mediumstørrelsebilde til "<defaultpath>\thumb"
	 * 
	 * @param fileName
	 *            Navn på filen som lagres, uten filtype
	 * @return Returnerer om metoden har hatt suksess
	 */

	public boolean saveMediumImageToFile(String fileName) {
		BufferedImage temp = resizeToMedium(bufferedImage);
		return saveToFile(temp, defaultPath + "\\medium", fileName);
	}

	/**
	 * Lagrer bildet i original størrelse i mappen "<defaultPath>\full".
	 * 
	 * @param fileName
	 *            Navn på filen som skal lagres i mappen, uten filtype
	 * @return Boolsk verdi om metoden har hatt suksess
	 */

	public boolean saveImageToFile(String fileName) {
		return saveToFile(bufferedImage, defaultPath + "\\full", fileName);
	}

	/**
	 * Lagrer et BufferedImage i spesifisert destinasjon(filePath) som fileName.
	 * Grunnen til at denne metoden tar inn et bilde i innparameteret, og ikke
	 * bare bruker det lokale feltet bufferedImage, er at det av og til lagres
	 * forminskede instanser av bildet, som kun er midlertidige.
	 * 
	 * @param bufferedImage
	 *            Bildet som skal lagres i destinasjonen
	 * @param filePath
	 *            Destinasjonen til bildefilen
	 * @param filename
	 *            Filnavnet til bildefilen (inkludert filtype) eks. "Fugl.jpg"
	 * @return Returnerer en boolsk verdi om bildet lykkes i å bli lagret.
	 */

	private boolean saveToFile(BufferedImage bufferedImage, String filePath,
			String fileName) {
		boolean success = false;
		try {
			File output = new File(filePath + "\\" + fileName + ".png");
			// TODO: andre filtyper enn png ?
			success = ImageIO.write(bufferedImage, "png", output);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return success;
	}

	/**
	 * Hjelpemetode for å realisere størrelsesendring-metoden. Kilde:
	 * http://stackoverflow.com/questions/13605248/java-converting-image-to-
	 * bufferedimage
	 * 
	 * @param img
	 *            tar inn et Image-objekt
	 * @return Gir tilbake et objekt av typen BufferedImage
	 */

	private BufferedImage toBufferedImage(Image img) {
		if (img instanceof BufferedImage) {
			return (BufferedImage) img;
		}

		// Create a buffered image with transparency
		BufferedImage bimage = new BufferedImage(img.getWidth(null),
				img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

		// Draw the image on to the buffered image
		Graphics2D bGr = bimage.createGraphics();
		bGr.drawImage(img, 0, 0, null);
		bGr.dispose();

		// Return the buffered image
		return bimage;
	}

	@Override
	public String toString() {
		return String.format("ID: %d\nTitle: %s\nDescription: %s\nRating: %d",
				ID, title, description, rating);

	}

}
