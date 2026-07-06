package xyz.attacktive.islands;

import com.intellij.icons.AllIcons;
import com.intellij.ide.IconProvider;
import com.intellij.openapi.roots.TestSourcesFilter;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.Icon;

/**
 * Marks top-level Java classes in test source roots with the IDE's own Tests scope icon, so the project view (where Java files render as class nodes) matches WebStorm's *.spec.ts treatment. Loaded only in Java-capable IDEs via the optional module dependency.
 */
public final class JavaTestClassIconProvider extends IconProvider {
	@Override
	@Nullable
	public Icon getIcon(@NotNull PsiElement element, int flags) {
		if (!(element instanceof PsiClass)) {
			return null;
		}

		if (!(element.getParent() instanceof PsiFile)) {
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
