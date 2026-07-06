package attacktive.islands;

import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.FileEditorManagerEvent;
import com.intellij.openapi.fileEditor.FileEditorManagerListener;
import com.intellij.openapi.fileEditor.ex.FileEditorManagerEx;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Set;

/**
 * Keeps {@link ActiveTabs} in sync with the editor selection and makes the tab strips recompute their icons on every switch, so {@link TestFileIconProvider} decorates only the active tab. Tab icons are otherwise only recomputed on file-level events, never on selection changes.
 */
public final class ActiveTabListener implements FileEditorManagerListener {
	@Override
	public void selectionChanged(@NotNull FileEditorManagerEvent event) {
		refresh(event.getManager());
	}

	@Override
	public void fileOpened(@NotNull FileEditorManager manager, @NotNull VirtualFile file) {
		refresh(manager);
	}

	private static void refresh(FileEditorManager manager) {
		Project project = manager.getProject();
		if (project.isDisposed()) {
			return;
		}

		project.getService(ActiveTabs.class).setSelectedFiles(Set.copyOf(Arrays.asList(manager.getSelectedFiles())));
		FileEditorManagerEx.getInstanceEx(project).refreshIcons();
	}
}
