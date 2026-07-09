package xyz.attacktive.islands

import com.intellij.icons.AllIcons
import com.intellij.ide.FileIconPatcher
import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.TestSourcesFilter
import com.intellij.openapi.vfs.VirtualFile
import javax.swing.Icon

/**
 * Replaces the ACTIVE editor tab's icon with the IDE's own Tests scope icon (red/green triangles) when the file lives under a test source root — language-agnostic.
 * This is a patcher rather than a provider because a provider's icon is only the starting point: the platform then runs every [FileIconPatcher], and for a `.java` test file `com.intellij.ide.JavaFileIconPatcher` rewrites the tab to the PSI class icon, wiping out a provider's triangles.
 * Registered `order="after javaFileIconPatcher"` so it overrides that class icon while still yielding to the scratch-file patcher (`order="last"`).
 * Inactive tabs keep their regular icons; [ActiveTabListener] triggers the recompute on every tab switch.
 */
class TestFileIconPatcher: FileIconPatcher {
	override fun patchIcon(icon: Icon, file: VirtualFile, flags: Int, project: Project?): Icon {
		if (project == null) {
			return icon
		}

		if (file.isDirectory) {
			return icon
		}

		if (!project.getService(ActiveTabs::class.java).isSelected(file)) {
			return icon
		}

		if (!TestSourcesFilter.isTestSources(file, project)) {
			return icon
		}

		return AllIcons.Scope.Tests
	}
}
