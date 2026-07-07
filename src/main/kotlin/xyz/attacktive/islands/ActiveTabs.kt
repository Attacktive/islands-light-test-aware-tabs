package xyz.attacktive.islands

import com.intellij.openapi.components.Service
import com.intellij.openapi.vfs.VirtualFile

/**
 * Snapshot of the files whose editor tabs are currently selected (one per split window).
 * Written on the EDT by [ActiveTabListener], read by [TestFileIconProvider] from any thread.
 */
@Service(Service.Level.PROJECT)
class ActiveTabs {
	@Volatile
	private var selectedFiles: Set<VirtualFile> = emptySet()

	fun isSelected(file: VirtualFile): Boolean {
		return selectedFiles.contains(file)
	}

	internal fun setSelectedFiles(files: Set<VirtualFile>) {
		selectedFiles = files
	}
}
