package storing;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;

import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

import storing.ReadFromDatabase.IsNotOnlyChildObject;

public class FileWatcher implements FileAlterationListener {

	static Path directoryToWatch;
	static FileAlterationMonitor monitor;
	static ArrayList<String> ignoreList;

	public static final int READ_INTERVAL_MS = 10000;

	public FileWatcher(Path dir) {
		directoryToWatch = dir;

		ignoreList = new ArrayList<String>();
		FileAlterationObserver observer = new FileAlterationObserver(
				directoryToWatch.toString());
		observer.addListener(this);

		monitor = new FileAlterationMonitor(READ_INTERVAL_MS);
		monitor.addObserver(observer);
		start();

	}

	public static void stop() {
		try {
			log("Stopped.");
			monitor.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void start() {
		try {
			monitor.start();
			log("Started.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void log(String string) {
		System.out.println("FW@ " + string);
	}

	@Override
	public void onDirectoryChange(File directory) {
		log(directory + " was changed (Directory)");
	}

	@Override
	public void onDirectoryCreate(File directory) {
		if (isIgnored(directory)) {
			unignore(directory);
			return;
		}

		log("New directory: " + directory.getName());
		int count = directory.toPath().getParent().getNameCount();
		String parentPath = "\\"
				+ directory.toPath().getParent().subpath(1, count).toString()
				+ "\\";
		int parentID = ReadFromDatabase.getFolderID(parentPath);

		String folderPath = parentPath + directory.getName() + "\\";

		ParentFolderDb newFolder = new ParentFolderDb(directory.getName(),
				folderPath, parentID);

		IsNotOnlyChildObject onlyChild = ReadFromDatabase
				.isFolderOnlyChild(parentID);

		if (onlyChild.isOnlyChild) {
			boolean writ = WriteToDatabase.addFolderAsAnOnlyChildToFolder(
					newFolder, parentID);
		} else {
			boolean writ = WriteToDatabase.addFolderInAFolderWithOtherChildren(
					newFolder, onlyChild.getLeftChildId());
		}

	}

	@Override
	public void onDirectoryDelete(File directory) {

		if (isIgnored(directory)) {
			unignore(directory);
			return;
		}

		log("Deleted directory: " + directory.getName());
		int count = directory.toPath().getParent().getNameCount();

		String parentPath = "\\"
				+ directory.toPath().getParent().subpath(1, count).toString()
				+ "\\";

		String folderPath = parentPath + directory.getName() + "\\";

		int folderId = ReadFromDatabase.getFolderID(folderPath);
		boolean success = DeleteFromDatabase.deleteFolderAndContent(folderId);
		if (success)
			log("Folder was deleted: " + success);
	}

	@Override
	public void onFileChange(File file) {
		log(file + " was changed (File)");
	}

	@Override
	public void onFileCreate(File file) {
		if (isIgnored(file)) {
			unignore(file);
			return;
		}
		log("New file: " + file);
		if (ImageHandler.isImageFile(file)) {

			int length = file.toPath().getNameCount();
			String parentPath = "\\"
					+ file.toPath().getParent().subpath(1, length - 1)
							.toString() + "\\";

			int parentID = ReadFromDatabase.getFolderID(parentPath);

			String fullPath = file.toPath().subpath(2, length).toString();
			int lio = fullPath.split("[.]").length;
			String mediumPath = fullPath.split("[.]")[lio - 2] + "_medium"
					+ "." + fullPath.split("[.]")[lio - 1];
			String thumbPath = fullPath.split("[.]")[lio - 2] + "_thumb" + "."
					+ fullPath.split("[.]")[lio - 1];
			ReadExif read = new ReadExif(file.getPath().toString());
			PictureDb pictureDb = new PictureDb(read.getExifTitle(),
					read.getExifComment(), read.getExifRating(),
					read.getExifDateTimeTaken(), fullPath, mediumPath,
					thumbPath, parentID);

			boolean wasWritten = WriteToDatabase.writeOnePic(pictureDb);

			if (wasWritten) {
				if (!(read.getExifTags() == null)) {
					int pictureId = ReadFromDatabase
							.getPictureFromPath(fullPath);
					System.out.println("picture id til tagbilde: " + pictureId);
					String[] tagsInString = read.getExifTags().split(";");
					for (int i = 0; i < tagsInString.length; i++) {
						boolean writeTagsToDb = WriteToDatabase.addTagToPic(
								pictureId, tagsInString[i]);
						System.out.println("tag skrevet: " + writeTagsToDb
								+ " : " + tagsInString[i]);
					}
				}
			}
			log("Was written to batadase: " + wasWritten);

			ImageHandler.saveImageFromDisk(file);
		}
	}

	@Override
	public void onFileDelete(File file) {
		if (isIgnored(file)) {
			unignore(file);
			return;
		}
		if (ImageHandler.isImageFile(file)) {
			String filename = file.getName().split("[.]")[0];
			if (!filename.endsWith("_medium") && !filename.endsWith("_thumb")) {
				deleteRemaining(file);

				int count = file.toPath().getNameCount();

				String fullPath = file.toPath().subpath(2, count).toString();

				int imageID = ReadFromDatabase.getPictureFromPath(fullPath);

				boolean success = DeleteFromDatabase.deletePicture(imageID);
				log("Image file deleted: " + file.getName() + ", "
						+ (success ? "" : "not") + " removed from badatase.");
			}

		} else {
			log("File deleted: " + file);
		}

	}

	public static void ignore(File... files) {
		int length = files.length;
		StringBuilder statusMessage = new StringBuilder("Ignored ");
		boolean wasIgnored = false;
		for (int i = 0; i < length; i++) {
			File file = files[i];
			if (!isIgnored(file)) {
				statusMessage.append(file.getName() + " ");
				ignoreList.add(file.getAbsolutePath());
				wasIgnored = true;
			}
		}
		if (wasIgnored)
			log(statusMessage.toString());
	}

	private static boolean unignore(File file) {
		if (isIgnored(file))
			return ignoreList.remove(file.toPath().toString());
		return false;
	}

	private static boolean isIgnored(File file) {
		return ignoreList.contains(file.getAbsolutePath());
	}

	@Override
	public void onStart(FileAlterationObserver arg0) {
		log("Scanning...");
	}

	@Override
	public void onStop(FileAlterationObserver arg0) {
		StringBuilder sb = new StringBuilder("");
		boolean hasItems = !ignoreList.isEmpty();
		if (hasItems) {
			sb.append(" Ignoring ");
			for (String filename : ignoreList) {
				sb.append(filename + " ");
			}
		}
		log("Scan complete." + (hasItems ? sb.toString() : ""));
	}

	private static File searchFile(File file, String string) {
		if (file == null)
			return null;

		Path directory = file.toPath().getParent();

		if (directory == null)
			return null;

		File[] children = directory.toFile().listFiles();
		if (children == null)
			return null;

		for (File child : children) {
			if (child.getName().equals(
					file.getName().split("[.]")[0] + string + "."
							+ file.getName().split("[.]")[1]))
				return child;
		}
		return null;
	}

	private static void deleteRemaining(File file) {
		File thumb = searchFile(file, "_thumb");
		if (thumb != null) {
			log("Deleted remaining " + thumb.getName());
			ignore(thumb);
			thumb.delete();
		}
		File medium = searchFile(file, "_medium");
		if (medium != null) {
			log("Deleted remaining " + medium.getName());
			ignore(medium);
			medium.delete();
		}
	}

}
