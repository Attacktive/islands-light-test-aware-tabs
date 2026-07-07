package xyz.attacktive.islands

import com.intellij.icons.AllIcons
import com.intellij.ide.FileIconProvider
import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.TestSourcesFilter
import com.intellij.openapi.vfs.VirtualFile
import javax.swing.Icon

/**
 * Gives the file in the ACTIVE editor tab the IDE's own Tests scope icon (red/green triangles) when it lives under a test source root — language-agnostic.
 * Inactive tabs keep their regular icons; [ActiveTabListener] triggers the recompute on every tab switch.
 */
class TestFileIconProvider : FileIconProvider {
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

		if (!TestSourcesFilter.isTestSources(file, project)) {
			return null
		}

		return AllIcons.Scope.Tests
	}
}
