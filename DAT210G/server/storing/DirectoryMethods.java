package storing;

import java.io.File;
import java.nio.file.Path;

import storing.ReadFromDatabase.IsNotOnlyChildObject;

public class DirectoryMethods {

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

		int parentId;

		String parentPath = relative.getParent().toString() + "\\";
		parentId = ReadFromDatabase.getFolderID(parentPath);

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

	public static String getFileNameWithoutExtension(File file) {
		String fullName = file.getName();
		String[] elements = fullName.split("[.]");
		String name = elements[0];
		return name;
	}

	public static String getFileExtension(File file) {
		String name = file.getName();
		String[] elements = name.split("[.]");
		String extension = elements[elements.length - 1];
		return extension;
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
		String extension = original.split("[.]")[1];

		return name + suffix + "." + extension;

	}

	public static String getMediumName(String fileName) {
		return getFilenameWithSuffix(fileName, "_medium");
	}

	public static String getThumbnailName(String fileName) {
		return getFilenameWithSuffix(fileName, "_thumb");
	}
}
