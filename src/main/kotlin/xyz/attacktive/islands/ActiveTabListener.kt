package xyz.attacktive.islands

import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.fileEditor.FileEditorManagerEvent
import com.intellij.openapi.fileEditor.FileEditorManagerListener
import com.intellij.openapi.fileEditor.ex.FileEditorManagerEx
import com.intellij.openapi.vfs.VirtualFile

/**
 * Keeps [ActiveTabs] in sync with the editor selection and makes the tab strips recompute their icons on every switch, so [TestFileIconPatcher] and [ExternalFileIconPatcher] decorate only the active tab.
 * Tab icons are otherwise only recomputed on file-level events, never on selection changes.
 */
class ActiveTabListener: FileEditorManagerListener {
	override fun selectionChanged(event: FileEditorManagerEvent) {
		refresh(event.manager)
	}

	override fun fileOpened(manager: FileEditorManager, file: VirtualFile) {
		refresh(manager)
	}

	private fun refresh(manager: FileEditorManager) {
		val project = manager.project
		if (project.isDisposed) {
			return
		}

		project.getService(ActiveTabs::class.java)
			.setSelectedFiles(manager.selectedFiles.toSet())

		FileEditorManagerEx.getInstanceEx(project)
			.refreshIcons()
	}
}
