package storing;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

public class ImageHandler {

	public static final int THUMBNAIL_WIDTH = 100, THUMBNAIL_HEIGHT = 100,
			MEDIUM_WIDTH = 250, MEDIUM_HEIGHT = 250;

	public Path defaultPath = Paths.get(".\\img");

	public ImageHandler() {
		init();
	}

	public void init() {
		try {
			ensureStorageLocation();
		} catch (FileNotFoundException fnfe) {
			// TODO Auto-generated catch block
			fnfe.printStackTrace();
		}
	}

	/**
	 * Lager nødvendige mapper for lagring av bilder.
	 * 
	 * 
	 * @throws FileNotFoundException
	 */
	public void ensureStorageLocation() throws FileNotFoundException {
		File folder = new File(defaultPath.toString());
		boolean success = folder.mkdirs();
		System.out.println("Folder was made: " + success);
		success = folder.exists();
		System.out.println("Folder exists: " + success);
		if (!success)
			throw new FileNotFoundException(
					"Image-containing folder was not found and could not be made!");
	}

	/**
	 * Testmetode for å laste inn et BufferedImage som kan brukes til å lagres
	 * 
	 * @return Returnerer et bilde som ligger i defaultmappen til
	 *         ImageHandleren.
	 */
	public BufferedImage testLoad() {
		BufferedImage bufferedImage = null;
		try {
			bufferedImage = ImageIO.read(new File(defaultPath + "\\dog.jpg"));
			System.out.println("Image was found and successfully read: "
					+ (bufferedImage != null));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return bufferedImage;
	}

	/**
	 * Metoden forsøker å lese og laste inn et bilde med sti defaultPath og
	 * returnerer et BufferedImage.
	 * 
	 * 
	 * @Param filename Navn og filtype til bildefilen en vil hente ut. Ett
	 *        eksempel: "bilde1.jpg".
	 * 
	 * @return Returnerer et BufferedImage av bildet.
	 */

	public BufferedImage load(String filename) {
		BufferedImage bufferedImage = null;
		try {
			bufferedImage = ImageIO
					.read(new File(defaultPath + "\\" + filename));
			System.out.println("Image was found and successfully read: "
					+ (bufferedImage != null));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return bufferedImage;
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
	 * Metoden tar inn et bufferedImage, lager nytt bilde som skaleres etter en
	 * dimensjon som er definert i MEDIUM_WIDTH x MEDIUM_HEIGHT og returnerer
	 * det nye bildet.
	 * 
	 * @param bufferedImage
	 *            Bildet i fullstørrelse.
	 * @return Bildet i mediumstørrelse.
	 */
	public BufferedImage resizeToMedium(BufferedImage bufferedImage) {
		Image tempImage = bufferedImage.getScaledInstance(MEDIUM_WIDTH,
				MEDIUM_HEIGHT, BufferedImage.SCALE_FAST);
		BufferedImage scaledImage = toBufferedImage(tempImage);
		return scaledImage;
	}

	/**
	 * Lagrer et BufferedImage i
	 * 
	 * @param bufferedImage
	 * @param filename
	 * @return
	 */

	public boolean saveBufferedImage(BufferedImage bufferedImage,
			String filename) {
		boolean success = false;
		try {
			File output = new File(defaultPath + "\\" + filename);
			success = ImageIO.write(bufferedImage, "png", output);

		} catch (IOException e) {
			// TODO Auto-generated catch block
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

	public static BufferedImage toBufferedImage(Image img) {
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

	public static void main(String[] args) {
		ImageHandler imageHandler = new ImageHandler();
		BufferedImage bufferedImage = imageHandler.load("bear.jpg");
		BufferedImage thumbnail = imageHandler.resizeToThumbnail(bufferedImage);
		BufferedImage medium = imageHandler.resizeToMedium(bufferedImage);

		System.out.println("Thumbnail was saved: "
				+ imageHandler.saveBufferedImage(thumbnail, "bear_thumb.png"));
		System.out.println("Medium was made: "
				+ imageHandler.saveBufferedImage(medium, "bear_med.png"));

	}
}
