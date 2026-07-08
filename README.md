# Islands Light with Test-Aware Tabs

A fork of JetBrains' Islands Light UI theme that makes test files and non-project files impossible to miss, bundled with a pair of tiny icon providers in a single plugin jar.

![IntelliJ IDEA with the theme: the active tab of a test file (UtilityAndPerformanceTests.kt) shows the red/green Tests scope icon](https://plugins.jetbrains.com/files/32771/screenshot_019d0051-3ee0-448f-b24f-5f1cbfdd3dfa)

![Editor tab strip: the active tab of a non-project file (random_external_file.md) shows a yellow warning triangle, while a test file's tab shows the red/green Tests scope icon](https://plugins.jetbrains.com/files/32771/screenshot_02099d2d-15ce-4544-a215-b7731f872f5d)

## What it adds on top of stock Islands Light

- **Active tab (test files)**: a file under a test source root shows the IDE's own Tests scope icon (the red◀ green▶ triangles) while its tab is selected; inactive tabs keep their regular file icons, so the tab strip stays readable. The stock green file-color background still marks inactive test tabs.
- **Active tab (non-project files)**: a file from outside the project — one under no content or library root, the kind the IDE tints yellow — shows a warning icon (the yellow triangle) while its tab is selected, so an external file you've opened stands out once it's active instead of being masked by the selection color.
- **Translucent active-tab fill**: the island fill of the selected tab is slightly translucent, so a test file's scope color tints the active tab — a language-agnostic backup signal.

Test detection is path-based (`TestSourcesFilter`), so it works for any language whose test roots are marked — and only for files under marked test source roots.

## How it works

- Two `fileIconProvider`s (both `order="first"`) answer these icons — the Tests scope icon for files in test source roots, the warning icon for non-project files — but only while the file is a selected tab; a `FileEditorManagerListener` snapshots the selection on every tab switch and calls `FileEditorManagerEx.refreshIcons()` so the tab strip actually re-asks.
- The theme itself only redirects the two underlined-tab background keys to translucent variants.

## Changes from the stock theme (Apache 2.0 §4(b) notice)

`src/main/resources/theme/IslandsLightTestAwareTabs.theme.json` is a modified copy of `ManyIslandsLight.theme.json` from the IntelliJ Platform. The full delta:

- `name` and `author` fields changed to identify the fork
- two added palette keys: `tab-selected-bg-active-translucent` (`#D0DFFE8C`) and `tab-selected-bg-inactive-translucent` (`#D7D9E08C`)
- `EditorTabs.underlinedTabBackground` and `EditorTabs.inactiveUnderlinedTabBackground` redirected to those keys

The file is reformatted to this repo's `.editorconfig` (tab indentation, no blank separator lines); apart from that, everything else in the theme file is content-identical to stock. The Kotlin sources under `src/main/kotlin/` are original to this project.

## Building

The plugin builds with Gradle via the [IntelliJ Platform Gradle Plugin](https://plugins.jetbrains.com/docs/intellij/tools-intellij-platform-gradle-plugin.html); the wrapper pins the Gradle version, so all you need locally is a JDK 21.

```sh
./gradlew buildPlugin   # the installable zip lands in build/distributions/
./gradlew runIde        # launch a sandbox IDE with the plugin loaded
```

Install the built zip via Settings → Plugins → ⚙ → **Install Plugin from Disk…**, then Settings → Appearance & Behavior → Appearance → Theme → **Islands Light with Test-Aware Tabs**.

## Releasing

The release workflow runs on every tag push and fails fast unless the tag exactly matches `pluginVersion` in `gradle.properties`; on a match it verifies the plugin with the JetBrains Plugin Verifier, builds the zip, attaches it to a GitHub Release with generated notes, and publishes it to JetBrains Marketplace. The Marketplace step needs a `MARKETPLACE_TOKEN` repository secret holding a [permanent token](https://plugins.jetbrains.com/author/me/tokens); remember to bump `pluginVersion` first — Marketplace refuses reused version numbers.

## License

Apache License 2.0 — see [LICENSE](LICENSE) and [NOTICE](NOTICE). The theme content originates from the [IntelliJ Platform](https://github.com/JetBrains/intellij-community), © JetBrains s.r.o., Apache 2.0.
