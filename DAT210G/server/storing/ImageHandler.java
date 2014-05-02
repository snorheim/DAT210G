package storing;

import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.*;

import javax.imageio.ImageIO;

import logic.Loggy;
import net.coobird.thumbnailator.Thumbnails;

public class ImageHandler {

	private static final int THUMBNAIL_SIZE = 150, MEDIUM_SIZE = 500;

	public static final String[] SUPPORTED_EXTENSIONS = { "jpg", "png", "bmp",
			"jpeg" };

	private static Path defaultPath;

	public ImageHandler() {
		init();
	}

	public static void init() {
		if (defaultPath != null) {
			log("ImageHandler already initialized!");
			return;
		}

		log("Initializing...");

		defaultPath = Paths.get(".\\img").normalize().toAbsolutePath();
		log("Default path: " + defaultPath);
		log("Relative path: " + getRelativePathString());

		try {
			log("Default path ready: \t" + ensureLocation());
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		}
	}

	public static String getRelativePathString() {
		int nameCount = defaultPath.getNameCount();
		return defaultPath.subpath(nameCount - 1, nameCount).toString() + "\\";
	}

	private static void log(String string) {
		Loggy.log(string, Loggy.IMG_HANDLER);
	}

	private static boolean ensureLocation() throws FileNotFoundException {

		if (defaultPath.toFile().exists()) {
			return true;
		} else {
			WriteToDatabase.ensureImgFolderDatabase();
			return defaultPath.toFile().mkdirs();
		}
	}

	public static BufferedImage load(String filepath) {
		log("Loading imagefile: " + getDefaultPathString() + filepath);
		File imageFile = new File(getDefaultPathString() + filepath);
		if (imageFile.exists()) {
			return load(imageFile);
		}
		return null;
	}

	public static Path getDefaultPath() {
		return defaultPath;
	}

	public static String getDefaultPathString() {
		return defaultPath.toString() + "\\";
	}

	public static String getDefaultPathParentString() {
		return defaultPath.getParent().toString() + "\\";
	}

	public static BufferedImage load(File file) {
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

	private static boolean copyFile(File sourceFile, File destinationFile) {
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

	public static boolean saveFullImageToFile(File imageFile) {
		Path oldLocation = imageFile.toPath().getParent();
		return saveFullImageToFile(imageFile, oldLocation);
	}

	public static boolean saveFullImageToFile(File imageFile, Path destination) {
		String newFilename = imageFile.getName();
		File destFile = new File(destination + "\\" + newFilename);

		if (destFile.exists()) {
			log(destFile + " already exists!");
			return true;
		}

		boolean wasSuccessful = copyFile(imageFile, destFile);

		if (wasSuccessful) {
			log("Saved " + imageFile + " to " + destFile);
			FileWatcher.ignore(imageFile);
			FileWatcher.ignore(destFile);
			return true;
		} else
			log("Could not save fullsized image");
		return false;
	}

	public static boolean saveMediumImageToFile(File imageFile) {
		Path oldLocation = imageFile.toPath().getParent();
		return saveMediumImageToFile(imageFile, oldLocation);
	}

	public static boolean saveMediumImageToFile(File imageFile, Path destination) {
		try {

			String newFilename = imageFile.getName().split("[.]")[0]
					+ "_medium" + "." + imageFile.getName().split("[.]")[1];
			File destFile = new File(destination + "\\" + newFilename);

			if (destFile.exists()) {
				log(destFile + " already exists!");
				return true;
			}

			Thumbnails.of(imageFile).size(MEDIUM_SIZE, MEDIUM_SIZE)
					.toFile(destFile);
			log("Saved " + imageFile + " to " + destFile);

			FileWatcher.ignore(destFile);

			hideImageFile(destFile, true);

			return true;
		} catch (IOException e) {

			e.printStackTrace();
			log("Could not save mediumsized image");
			return false;
		}
	}

	public static boolean saveThumbnailImageToFile(File imageFile) {
		Path oldLocation = imageFile.toPath().getParent();
		return saveThumbnailImageToFile(imageFile, oldLocation);
	}

	public static boolean saveThumbnailImageToFile(File imageFile,
			Path destination) {
		try {
			String newFilename = imageFile.getName().split("[.]")[0] + "_thumb"
					+ "." + imageFile.getName().split("[.]")[1];
			File destFile = new File(destination + "\\" + newFilename);

			if (destFile.exists()) {
				log(destFile + " already exists!");
				return true;
			}
			Thumbnails.of(imageFile).size(THUMBNAIL_SIZE, THUMBNAIL_SIZE)
					.toFile(destFile);
			log("Saved " + imageFile + " to " + destFile);

			FileWatcher.ignore(destFile);

			hideImageFile(destFile, true);

			return true;
		} catch (IOException e) {
			log("Could not save thumbnailsized image");
			return false;
		}
	}

	public static boolean saveImageFromDisk(File tempFile) {
		if (!saveMediumImageToFile(tempFile)) {
			return false;
		}
		if (!saveThumbnailImageToFile(tempFile)) {
			return false;
		}

		// writeImageToDatabase(tempFile);

		return true;
	}

	public static boolean saveAll(File imageFile) {
		return saveAll(imageFile, defaultPath);
	}

	public static boolean saveAll(File imageFile, Path destination) {
		if (!saveFullImageToFile(imageFile, destination)) {
			return false;
		}

		if (!saveMediumImageToFile(imageFile, destination)) {
			return false;
		}
		if (!saveThumbnailImageToFile(imageFile, destination)) {
			return false;
		}

		return true;
	}

	// Tester metodene:

	public static void main(String[] args) {
		new ImageHandler();
		System.out.println(getDefaultPathParentString());
	}

	public static void hideImageFile(File file, boolean hide) {
		try {
			Object hidden = Files.getAttribute(file.toPath(), "dos:hidden",
					LinkOption.NOFOLLOW_LINKS);
			if (hidden != null) {
				Files.setAttribute(file.toPath(), "dos:hidden", hide,
						LinkOption.NOFOLLOW_LINKS);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static boolean isImageFile(File file) {
		String extension = DirectoryMethods.getFileExtension(file);
		for (String supportedExtension : SUPPORTED_EXTENSIONS)
			if (extension.toLowerCase().equals(supportedExtension))
				return true;
		return false;
	}
}
