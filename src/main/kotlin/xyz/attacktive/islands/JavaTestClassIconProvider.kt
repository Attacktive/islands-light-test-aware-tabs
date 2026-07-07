package xyz.attacktive.islands

import com.intellij.icons.AllIcons
import com.intellij.ide.IconProvider
import com.intellij.openapi.roots.TestSourcesFilter
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import javax.swing.Icon

/**
 * Marks top-level Java classes in test source roots with the IDE's own Tests scope icon, so the project view (where Java files render as class nodes) matches WebStorm's *.spec.ts treatment.
 * Loaded only in Java-capable IDEs via the optional module dependency.
 */
class JavaTestClassIconProvider : IconProvider() {
	override fun getIcon(element: PsiElement, flags: Int): Icon? {
		if (element !is PsiClass) {
			return null
		}

		if (element.parent !is PsiFile) {
			return null
		}

		val file = element.containingFile ?: return null
		val virtualFile = file.virtualFile ?: return null

		if (!TestSourcesFilter.isTestSources(virtualFile, element.project)) {
			return null
		}

		return AllIcons.Scope.Tests
	}
}
