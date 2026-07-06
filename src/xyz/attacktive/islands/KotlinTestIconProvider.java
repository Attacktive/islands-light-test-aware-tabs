package xyz.attacktive.islands;

import com.intellij.icons.AllIcons;
import com.intellij.ide.IconProvider;
import com.intellij.openapi.roots.TestSourcesFilter;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtFile;

import javax.swing.Icon;

/**
 * Marks Kotlin files and top-level Kotlin classes in test source roots with the IDE's own Tests scope icon. Registered order="first" because the Kotlin plugin's own icon providers (Fe10KotlinIconProvider/FirKotlinIconProvider) would otherwise answer before this one. Loaded only when the Kotlin plugin is present via the optional dependency.
 */
public final class KotlinTestIconProvider extends IconProvider {
	@Override
	@Nullable
	public Icon getIcon(@NotNull PsiElement element, int flags) {
		boolean isTopLevelClass = element instanceof KtClassOrObject && element.getParent() instanceof KtFile;

		if (!(element instanceof KtFile) && !isTopLevelClass) {
			return null;
		}

		PsiFile file = element.getContainingFile();
		if (file == null) {
			return null;
		}

		VirtualFile virtualFile = file.getVirtualFile();
		if (virtualFile == null) {
			return null;
		}

		if (!TestSourcesFilter.isTestSources(virtualFile, element.getProject())) {
			return null;
		}

		return AllIcons.Scope.Tests;
	}
}
