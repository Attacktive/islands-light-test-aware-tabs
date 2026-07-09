package xyz.attacktive.islands

import com.intellij.icons.AllIcons
import com.intellij.ide.FileIconPatcher
import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.ProjectFileIndex
import com.intellij.openapi.vfs.VirtualFile
import javax.swing.Icon

/**
 * Replaces the ACTIVE editor tab's icon with a warning icon when the file is a non-project file living outside the project's content and library roots — the tabs the IDE would otherwise only tint yellow.
 * This is a patcher rather than a provider because a provider's icon is only the starting point: the platform then runs every [FileIconPatcher], and `com.intellij.ide.JavaFileIconPatcher` rewrites an external `.java` tab to the "java outside source" icon, wiping out a provider's warning.
 * Registered `order="after javaFileIconPatcher"` so it overrides that java icon while still yielding to the scratch-file patcher (`order="last"`).
 * Inactive tabs keep their regular icons; [ActiveTabListener] triggers the recompute on every tab switch.
 */
class ExternalFileIconPatcher: FileIconPatcher {
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

		val fileIndex = ProjectFileIndex.getInstance(project)
		if (fileIndex.isInContent(file) || fileIndex.isInLibrary(file)) {
			return icon
		}

		return AllIcons.General.Warning
	}
}
