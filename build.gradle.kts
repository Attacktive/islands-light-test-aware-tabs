plugins {
	kotlin("jvm") version "2.4.0"
	id("org.jetbrains.intellij.platform") version "2.17.0"
}

group = providers.gradleProperty("pluginGroup").get()
version = providers.gradleProperty("pluginVersion").get()

repositories {
	mavenCentral()

	intellijPlatform {
		defaultRepositories()
	}
}

dependencies {
	intellijPlatform {
		intellijIdeaUltimate(providers.gradleProperty("platformVersion"))
		pluginVerifier()
	}
}

intellijPlatform {
	pluginConfiguration {
		version = providers.gradleProperty("pluginVersion")

		ideaVersion {
			sinceBuild = providers.gradleProperty("pluginSinceBuild")

			// Open-ended compatibility — matches the original hand-rolled plugin.xml, which set only since-build. Without this the plugin defaults untilBuild to <branch>.*.
			untilBuild = provider { null }
		}
	}

	pluginVerification {
		ides {
			recommended()
		}
	}

	publishing {
		token = providers.environmentVariable("PUBLISH_TOKEN")
	}
}

kotlin {
	jvmToolchain(21)
}
