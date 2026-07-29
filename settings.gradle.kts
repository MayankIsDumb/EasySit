pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.fabricmc.net/")
        maven("https://maven.kikugie.dev/releases")
        maven("https://maven.neoforged.net/releases")
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
    id("dev.kikugie.stonecutter") version "0.9.4"
}

rootProject.name = "sit"

stonecutter {
    centralScript.set("build.gradle.kts")
    kotlinController.set(true)

    shared {
        // Shared configuration for all versions
    }

    // Define all target versions
    create(project(":")) {
        versions("1.21.1", "1.21.6", "1.21.11", "26.1", "26.1.1", "26.1.2", "26.2")
    }
}
