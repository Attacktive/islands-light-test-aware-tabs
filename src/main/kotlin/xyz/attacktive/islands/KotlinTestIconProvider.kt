package xyz.attacktive.islands

import com.intellij.icons.AllIcons
import com.intellij.ide.IconProvider
import com.intellij.openapi.roots.TestSourcesFilter
import com.intellij.psi.PsiElement
import javax.swing.Icon
import org.jetbrains.kotlin.psi.KtClassOrObject
import org.jetbrains.kotlin.psi.KtFile

/**
 * Marks Kotlin files and top-level Kotlin classes in test source roots with the IDE's own Tests scope icon.
 * Registered order="first" because the Kotlin plugin's own icon providers (Fe10KotlinIconProvider/FirKotlinIconProvider) would otherwise answer before this one.
 * Loaded only when the Kotlin plugin is present via the optional dependency.
 */
class KotlinTestIconProvider : IconProvider() {
	override fun getIcon(element: PsiElement, flags: Int): Icon? {
		val isTopLevelClass = element is KtClassOrObject && element.parent is KtFile
		if (element !is KtFile && !isTopLevelClass) {
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
