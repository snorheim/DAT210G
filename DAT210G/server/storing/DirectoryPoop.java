package storing;

import java.io.File;
import java.nio.file.Path;

import storing.ReadFromDatabase.IsNotOnlyChildObject;

public class DirectoryPoop {

	public static Path getRelativePath(File file) {
		return getRelativePath(file.toPath());
	}

	public static Path getRelativePath(Path path) {
		int length = path.getNameCount();
		for (int i = 0; i < length; i++) {
			if (path.getName(i).toString().equals("img"))
				return path.subpath(i, length);
		}

		return null;
	}

	public static void addNewDirectory(File directory) {
		Path relative = getRelativePath(directory.toPath());
		String parentPath = relative.getParent().toString() + "\\";
		System.out.println(parentPath);
		int parentId = ReadFromDatabase.getFolderID(parentPath);

		writeDirectoryToDatabase(directory, parentId);
	}

	public static void addNewDirectory(String detail, int parentId) {
		ParentFolderDb pfdb = ReadFromDatabase.getFolderInfo(parentId);
		File directory = new File(ImageHandler.getDefaultPathParentString()
				+ pfdb.getPath() + detail);

		FileWatcher.ignore(directory);

		directory.mkdirs();
		if (!directory.exists()) {
			return;
		}

		writeDirectoryToDatabase(directory, parentId);
	}

	public static void writeDirectoryToDatabase(File directory, int parentId) {
		String folderPath = getRelativePath(directory.toPath()) + "\\";

		ParentFolderDb newFolder = new ParentFolderDb(directory.getName(),
				folderPath, parentId);

		IsNotOnlyChildObject onlyChild = ReadFromDatabase
				.isFolderOnlyChild(parentId);

		if (onlyChild.isOnlyChild) {
			boolean writ = WriteToDatabase.addFolderAsAnOnlyChildToFolder(
					newFolder, parentId);
		} else {
			boolean writ = WriteToDatabase.addFolderInAFolderWithOtherChildren(
					newFolder, onlyChild.getLeftChildId());
		}
	}

	public static String getFilenameWithSuffix(String original, String suffix) {
		String name = original.split("[.]")[0];
		String extension = name.split("[.]")[1];

		return original + suffix + "." + extension;

	}

	public static String getMediumName(String fileName) {
		return getFilenameWithSuffix(fileName, "_medium");
	}

	public static String getThumbnailName(String fileName) {
		return getFilenameWithSuffix(fileName, "_thumb");
	}
}
