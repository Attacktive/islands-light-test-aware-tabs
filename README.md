# Islands Light with Test-Aware Tabs

A fork of JetBrains' Islands Light UI theme that makes test files impossible to miss, bundled with a few tiny icon providers in a single plugin jar.

<img width="1280" height="760" alt="Screenshot 2026-07-06 18:08:37" src="https://github.com/user-attachments/assets/4a35b145-578c-40fe-ab19-ebd542d69b99" />

## What it adds on top of stock Islands Light

- **Active tab**: a file under a test source root shows the IDE's own Tests scope icon (the red◀ green▶ triangles) while its tab is selected; inactive tabs keep their regular file icons, so the tab strip stays readable. The stock green file-color background still marks inactive test tabs.
- **Translucent active-tab fill**: the island fill of the selected tab is slightly translucent, so a test file's scope color tints the active tab — a language-agnostic backup signal.
- **Project view**: Java and Kotlin test classes carry the same Tests icon on their class nodes, like WebStorm's `*.spec.ts` treatment.

Test detection is path-based (`TestSourcesFilter`), so it works for any language whose test roots are marked — and only for files under marked test source roots.

## How it works

- A `fileIconProvider` (with `order="first"`) answers the Tests scope icon for files in test source roots, but only while the file is a selected tab; a `FileEditorManagerListener` snapshots the selection on every tab switch and calls `FileEditorManagerEx.refreshIcons()` so the tab strip actually re-asks.
- Two optional `iconProvider`s (loaded only when the Java/Kotlin plugins are present) mark test class nodes in the project view. The Kotlin one needs `order="first"` to beat the Kotlin plugin's own icon providers.
- The theme itself only redirects the two underlined-tab background keys to translucent variants.

## Changes from the stock theme (Apache 2.0 §4(b) notice)

`theme/IslandsLightTestAwareTabs.theme.json` is a modified copy of `ManyIslandsLight.theme.json` from the IntelliJ Platform. The full delta:

- `name` and `author` fields changed to identify the fork
- two added palette keys: `tab-selected-bg-active-translucent` (`#D0DFFE8C`) and `tab-selected-bg-inactive-translucent` (`#D7D9E08C`)
- `EditorTabs.underlinedTabBackground` and `EditorTabs.inactiveUnderlinedTabBackground` redirected to those keys

The file is reformatted to this repo's `.editorconfig` (tab indentation, no blank separator lines); apart from that, everything else in the theme file is content-identical to stock. The Java sources under `src/` are original to this project.

## Building

`build.sh` compiles the providers against a locally installed IntelliJ IDEA Ultimate (set the `IDE` environment variable, or edit its default in the script, to point at your install — the compile classpath needs `lib/`, `plugins/java/lib/`, and `plugins/Kotlin/lib/`), zips the plugin jar, and copies it into every `~/.local/share/JetBrains/<IDE>2026.1/` directory it finds.

```zsh
./build.sh
```

Restart the IDE, then Settings → Appearance & Behavior → Appearance → Theme → **Islands Light with Test-Aware Tabs**.

## Releasing

Pushing a tag that exactly matches `<version>` in `META-INF/plugin.xml` triggers the release workflow: it compiles against a downloaded IntelliJ IDEA, attaches the jar to a GitHub Release with generated notes, and uploads it to JetBrains Marketplace. The Marketplace step needs a `MARKETPLACE_TOKEN` repository secret holding a [permanent token](https://plugins.jetbrains.com/author/me/tokens); remember to bump `<version>` first — Marketplace refuses reused version numbers.

## License

Apache License 2.0 — see [LICENSE](LICENSE) and [NOTICE](NOTICE). The theme content originates from the [IntelliJ Platform](https://github.com/JetBrains/intellij-community), © JetBrains s.r.o., Apache 2.0.
