package storing;

import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.*;

import javax.imageio.ImageIO;

import net.coobird.thumbnailator.Thumbnails;

public class ImageHandler {

	private static ImageHandler instance = null;

	private static final int THUMBNAIL_SIZE = 150, MEDIUM_SIZE = 500;

	public static final String[] SUPPORTED_EXTENSIONS = { "jpg", "png", "bmp",
			"jpeg" };

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
	}

	private void log(String string) {
		System.out.println("IH@ " + string);
	}

	private boolean ensureLocation() throws FileNotFoundException {

		if (defaultPath.toFile().exists()) {
			return true;
		} else {
			WriteToDatabase.ensureImgFolderDatabase();
			return defaultPath.toFile().mkdirs();
		}
	}

	public BufferedImage load(String filepath) {
		log("Loading imagefile: " + defaultPath + filepath);
		File imageFile = new File(defaultPath + "\\" + filepath);
		if (imageFile.exists()) {
			return load(imageFile);
		}
		return null;
	}

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

	public boolean saveFullImageToFile(File imageFile) {
		Path oldLocation = imageFile.toPath().getParent();
		return saveFullImageToFile(imageFile, oldLocation);
	}

	public boolean saveFullImageToFile(File imageFile, Path destination) {
		String newFilename = imageFile.getName();
		File destFile = new File(destination + "\\" + newFilename);

		if (destFile.exists()) {
			log(destFile + " already exists!");
			return true;
		}

		boolean wasSuccessful = copyFile(imageFile, destFile);

		if (wasSuccessful) {
			log("Saved " + imageFile + " to " + destFile);
			FileWatcher.getInstance().ignore(imageFile);
			FileWatcher.getInstance().ignore(destFile);
			return true;
		} else
			log("Could not save fullsized image");
		return false;
	}

	public boolean saveMediumImageToFile(File imageFile) {
		Path oldLocation = imageFile.toPath().getParent();
		return saveMediumImageToFile(imageFile, oldLocation);
	}

	public boolean saveMediumImageToFile(File imageFile, Path destination) {
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

			FileWatcher.getInstance().ignore(destFile);

			hideImageFile(destFile);

			return true;
		} catch (IOException e) {

			e.printStackTrace();
			log("Could not save mediumsized image");
			return false;
		}
	}

	public boolean saveThumbnailImageToFile(File imageFile) {
		Path oldLocation = imageFile.toPath().getParent();
		return saveThumbnailImageToFile(imageFile, oldLocation);
	}

	public boolean saveThumbnailImageToFile(File imageFile, Path destination) {
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

			FileWatcher.getInstance().ignore(destFile);

			hideImageFile(destFile);

			return true;
		} catch (IOException e) {
			log("Could not save thumbnailsized image");
			return false;
		}
	}

	public boolean saveImageFromDisk(File tempFile) {
		if (!saveMediumImageToFile(tempFile)) {
			return false;
		}
		if (!saveThumbnailImageToFile(tempFile)) {
			return false;
		}

		// writeImageToDatabase(tempFile);

		return true;
	}

	public boolean save(File imageFile) {
		return save(imageFile, defaultPath);
	}

	public boolean save(File imageFile, Path destination) {
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

	public static ImageHandler getInstance() {
		if (instance == null)
			instance = new ImageHandler();

		return instance;

	}

	// Tester metodene:

	public static void main(String[] args) {
		@SuppressWarnings("unused")
		ImageHandler ih = ImageHandler.getInstance();
	}

	private void hideImageFile(File file) {
		try {
			Object hidden = Files.getAttribute(file.toPath(), "dos:hidden",
					LinkOption.NOFOLLOW_LINKS);
			if (hidden != null) {
				Files.setAttribute(file.toPath(), "dos:hidden", Boolean.TRUE,
						LinkOption.NOFOLLOW_LINKS);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static boolean isImageFile(File arg0) {
		String extension = arg0.getName().split("[.]")[1];
		for (String supportedExtension : SUPPORTED_EXTENSIONS)
			if (extension.toLowerCase().equals(supportedExtension))
				return true;
		return false;
	}
}
