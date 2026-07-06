package xyz.attacktive.islands;

import com.intellij.openapi.components.Service;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * Snapshot of the files whose editor tabs are currently selected (one per split window). Written on the EDT by {@link ActiveTabListener}, read by {@link TestFileIconProvider} from any thread.
 */
@Service(Service.Level.PROJECT)
public final class ActiveTabs {
	private volatile Set<VirtualFile> selectedFiles = Set.of();

	public boolean isSelected(@NotNull VirtualFile file) {
		return selectedFiles.contains(file);
	}

	void setSelectedFiles(@NotNull Set<VirtualFile> files) {
		selectedFiles = files;
	}
}
