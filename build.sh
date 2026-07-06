#!/usr/bin/env zsh
# Builds the theme plugin jar (theme + icons + compiled icon provider) and installs it into every JetBrains 2026.1 IDE's plugins directory. Restart the IDEs afterwards.
set -euo pipefail
cd "$(dirname "$0")"

IDE=/opt/jetbrains-toolbox/apps/intellij-idea-ultimate
JAR=islands-light-test-aware-tabs.jar

rm -rf out
mkdir -p out
javac --release 21 -proc:none -cp "$IDE/lib/*:$IDE/plugins/java/lib/*:$IDE/plugins/Kotlin/lib/*" -d out src/xyz/attacktive/islands/*.java

rm -f "$JAR"
zip -q -r "$JAR" META-INF theme
(cd out && zip -q -r "../$JAR" xyz)

for ide in IntelliJIdea CLion GoLand WebStorm DataGrip PyCharm; do
	target="$HOME/.local/share/JetBrains/${ide}2026.1"

	if [[ -d "$target" ]]; then
		cp "$JAR" "$target/"
		echo "installed -> $target"
	else
		echo "skipped (missing) -> $target"
	fi
done

echo "Done. Restart the IDE(s), then Settings → Appearance & Behavior → Appearance → Theme → 'Islands Light with Test-Aware Tabs'."
