package storing;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;

public class DirectoryWatcher implements Runnable {

	Path directoryToWatch;
	WatchService watchService;

	public DirectoryWatcher(Path directoryToWatch) {
		this.directoryToWatch = directoryToWatch;
		try {
			watchService = directoryToWatch.getFileSystem().newWatchService();
			registerDirectories(watchService, directoryToWatch);
			new Thread(this).run();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void registerDirectories(WatchService watchService,
			Path directoryToWatch) {
		try {
			directoryToWatch.register(watchService,
					StandardWatchEventKinds.ENTRY_CREATE,
					StandardWatchEventKinds.ENTRY_DELETE);
			registerSubdirectories(watchService, directoryToWatch);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void testForDirectoryChange() {
		try {

			final WatchKey key = watchService.poll();
			if (key == null) {
				return;
			}

			List<WatchEvent<?>> list = key.pollEvents();

			for (WatchEvent<?> watchEvent : list) {
				Kind<?> kind = watchEvent.kind();
				// Overflow event
				if (kind == StandardWatchEventKinds.OVERFLOW) {
					continue; // loop
				} else if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
					WatchEvent<Path> eventPath = (WatchEvent<Path>) watchEvent;
					Path path = eventPath.context();

				}
			}
			key.reset();

		} catch (Exception e) {
			System.out.println("Error: " + e.toString());
		}
	}

	public static void registerSubdirectories(WatchService watchService,
			Path myDir) {
		try {
			for (File subDirectory : myDir.toFile().listFiles()) {
				if (subDirectory.isDirectory()) {
					subDirectory.toPath().register(watchService,
							StandardWatchEventKinds.ENTRY_CREATE,
							StandardWatchEventKinds.ENTRY_DELETE);
					registerSubdirectories(watchService, subDirectory.toPath());
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (true) {
			try {
				testForDirectoryChange();
				Thread.sleep(100);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
