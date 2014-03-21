package storing;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;

import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

import storing.ReadFromDatabase.IsNotOnlyChildObject;

public class DirectoryMonitor implements FileAlterationListener {

	Path directoryToWatch;
	FileAlterationMonitor monitor;
	ArrayList<String> ignoreList;

	public static final int READ_INTERVAL_MS = 5000;

	public DirectoryMonitor(Path dir) {
		directoryToWatch = dir;

		ignoreList = new ArrayList<String>();
		FileAlterationObserver observer = new FileAlterationObserver(
				directoryToWatch.toString());
		observer.addListener(this);

		monitor = new FileAlterationMonitor(READ_INTERVAL_MS);
		monitor.addObserver(observer);
		start();

	}

	public void stop() {
		try {
			log("Stopped.");
			monitor.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void start() {
		try {
			monitor.start();
			log("Started.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void log(String string) {
		System.out.println("DM@ " + string);
	}

	@Override
	public void onDirectoryChange(File arg0) {
	}

	@Override
	public void onDirectoryCreate(File arg0) {
		log("New directory: " + arg0.getName());

		int count = arg0.toPath().getParent().getNameCount();

		String parentPath = "\\"
				+ arg0.toPath().getParent().subpath(1, count).toString() + "\\";

		int parentID = ReadFromDatabase.getFolderID(parentPath);

		String folderPath = parentPath + arg0.getName() + "\\";

		ParentFolderDb newFolder = new ParentFolderDb(arg0.getName(),
				folderPath, parentID);

		IsNotOnlyChildObject onlyChild = ReadFromDatabase
				.isFolderOnlyChild(parentID);
		log("Is only child: " + onlyChild.isOnlyChild());

		if (onlyChild.isOnlyChild) {
			boolean writ = WriteToDatabase.addFolderAsAnOnlyChildToFolder(
					newFolder, parentID);
			log(writ + ", only child");
		} else {
			boolean writ = WriteToDatabase.addFolderInAFolderWithOtherChildren(
					newFolder, onlyChild.getLeftChildId());
			log(writ + ", sibling");
		}

	}

	@Override
	public void onDirectoryDelete(File arg0) {
		log("Deleted directory: " + arg0.getName());
		int count = arg0.toPath().getParent().getNameCount();

		String parentPath = "\\"
				+ arg0.toPath().getParent().subpath(1, count).toString() + "\\";

		String folderPath = parentPath + arg0.getName() + "\\";

		int folderId = ReadFromDatabase.getFolderID(folderPath);
		boolean success = DeleteFromDatabase.deleteFolderAndContent(folderId);
		if (success)
			log("Folder was deleted: " + success);
	}

	@Override
	public void onFileChange(File arg0) {
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

			log("Was written to batadase: " + wasWritten);

			ImageHandler.getInstance().saveImageFromDisk(file);
		}
	}

	@Override
	public void onFileDelete(File file) {
		if (isIgnored(file)) {
			unignore(file);
			return;
		}
		log("Deleted file: " + file);
		if (ImageHandler.isImageFile(file)) {
			String filename = file.getName().split("[.]")[0];
			if (!filename.endsWith("_medium") && !filename.endsWith("_thumb")) {
				deleteRemaining(file);

				int count = file.toPath().getNameCount();

				String fullPath = file.toPath().subpath(2, count).toString();

				int imageID = ReadFromDatabase.getPictureFromPath(fullPath);

				boolean success = DeleteFromDatabase.deletePicture(imageID);
				log(file.getName() + " was deleted: " + success);
			}

		}

	}

	public void ignore(File file) {
		ignoreList.add(file.toPath().toString());
	}

	private boolean unignore(File file) {
		boolean success = ignoreList.remove(file.toPath().toString());
		return success;
	}

	private boolean isIgnored(File file) {
		return ignoreList.contains(file.toPath().toString());
	}

	@Override
	public void onStart(FileAlterationObserver arg0) {
		log("Reading...");
	}

	@Override
	public void onStop(FileAlterationObserver arg0) {
		log("Read complete.");
		if (!ignoreList.isEmpty()) {
			StringBuilder sb = new StringBuilder("");
			sb.append("Ignoring ");
			for (String filename : ignoreList) {
				sb.append(filename + " ");
			}
			log(sb.toString());
		}
	}

	private File searchFile(File arg0, String string) {
		if (arg0 == null)
			return null;

		Path directory = arg0.toPath().getParent();

		if (directory == null)
			return null;

		File[] children = directory.toFile().listFiles();
		if (children == null)
			return null;

		for (File file : children) {
			if (file.getName().equals(
					arg0.getName().split("[.]")[0] + string + "."
							+ arg0.getName().split("[.]")[1]))
				return file;
		}
		return null;
	}

	private void deleteRemaining(File file) {
		File thumb = searchFile(file, "_thumb");
		if (thumb != null) {
			log("Deleted remaining " + thumb);
			ignore(thumb);
			thumb.delete();
		}
		File medium = searchFile(file, "_medium");
		if (medium != null) {
			log("Deleted remaining " + medium);
			ignore(medium);
			medium.delete();
		}
	}
}
