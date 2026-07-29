plugins {
    id("fabric-loom") version "1.15-SNAPSHOT"
    id("dev.kikugie.stonecutter")
}

val minecraftVersion = project.property("minecraft_version") as String
val modVersion = project.property("mod_version") as String
val modId = project.property("mod_id") as String
val loaderVersion = project.property("loader_version") as String
val fabricVersion = project.property("fabric_version") as String
val clothConfigVersion = project.property("cloth_config_version") as String
val modmenuVersion = project.property("modmenu_version") as String
val javaVer = (project.property("java_version") as String).toInt()

repositories {
    maven("https://maven.shedaniel.me/")
    maven("https://maven.terraformersmc.com/releases")
}

val is26x = minecraftVersion.startsWith("26.")

dependencies {
    add("minecraft", "com.mojang:minecraft:$minecraftVersion")

    if (!is26x) {
        add("mappings", loom.layered {
            officialMojangMappings()
        })
        add("modImplementation", "net.fabricmc:fabric-loader:$loaderVersion")
        add("modImplementation", "net.fabricmc.fabric-api:fabric-api:$fabricVersion")
        (add("modApi", "me.shedaniel.cloth:cloth-config-fabric:$clothConfigVersion") as ExternalModuleDependency).apply {
            exclude(group = "net.fabricmc.fabric-api")
        }
        add("modImplementation", "com.terraformersmc:modmenu:$modmenuVersion")
    } else {
        add("implementation", "net.fabricmc:fabric-loader:$loaderVersion")
        add("implementation", "net.fabricmc.fabric-api:fabric-api:$fabricVersion")
        (add("implementation", "me.shedaniel.cloth:cloth-config-fabric:$clothConfigVersion") as ExternalModuleDependency).apply {
            exclude(group = "net.fabricmc.fabric-api")
        }
        add("implementation", "com.terraformersmc:modmenu:$modmenuVersion")
    }
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(javaVer)
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.release.set(javaVer)
}

tasks.named<ProcessResources>("processResources") {
    inputs.property("version", modVersion)
    inputs.property("minecraft_version_range", minecraftVersion)
    val mcVersion = project.property("minecraft_version") as String

    fun isVersionAtLeast(version: String, target: String): Boolean {
        val vParts = version.split(".").map { it.toIntOrNull() ?: 0 }
        val tParts = target.split(".").map { it.toIntOrNull() ?: 0 }
        val maxLen = maxOf(vParts.size, tParts.size)
        for (i in 0 until maxLen) {
            val v = vParts.getOrElse(i) { 0 }
            val t = tParts.getOrElse(i) { 0 }
            if (v != t) return v > t
        }
        return true
    }

    val mcRange = when {
        isVersionAtLeast(mcVersion, "1.21.11") -> ">=1.21.11"
        isVersionAtLeast(mcVersion, "1.21.6") -> ">=1.21.6"
        else -> ">=1.21.1"
    }

    val extraMixins = if (is26x) ",\n    \"ServerPlayerMixin\"" else ""

    filesMatching("fabric.mod.json") {
        expand("version" to modVersion, "minecraft_version_range" to mcRange)
    }
    filesMatching("sit.mixins.json") {
        expand("extra_mixins" to extraMixins)
    }
}

tasks.jar {
    from(rootProject.file("LICENSE")) {
        rename { "${it}_$modId" }
    }
}

base {
    archivesName.set("$modId-$minecraftVersion")
}
