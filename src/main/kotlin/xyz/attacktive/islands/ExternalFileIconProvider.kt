package xyz.attacktive.islands

import com.intellij.icons.AllIcons
import com.intellij.ide.FileIconProvider
import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.ProjectFileIndex
import com.intellij.openapi.vfs.VirtualFile
import javax.swing.Icon

/**
 * Gives the file in the ACTIVE editor tab a warning icon when it is a non-project file living outside the project's content and library roots — the tabs the IDE would otherwise only tint yellow.
 * Inactive tabs keep their regular icons; [ActiveTabListener] triggers the recompute on every tab switch.
 */
class ExternalFileIconProvider : FileIconProvider {
	override fun getIcon(file: VirtualFile, flags: Int, project: Project?): Icon? {
		if (project == null) {
			return null
		}

		if (file.isDirectory) {
			return null
		}

		if (!project.getService(ActiveTabs::class.java).isSelected(file)) {
			return null
		}

		val fileIndex = ProjectFileIndex.getInstance(project)
		if (fileIndex.isInContent(file) || fileIndex.isInLibrary(file)) {
			return null
		}

		return AllIcons.General.Warning
	}
}
