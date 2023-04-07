plugins {
    alias(libs.plugins.shadow)
    alias(libs.plugins.minecrell)
    alias(libs.plugins.runpaper)
}

repositories {
    mavenLocal()
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    maven("https://repo.triumphteam.dev/snapshots/")
    mavenCentral()
}

dependencies {
    compileOnly(libs.spigot)
    compileOnly(libs.placeholder)
    compileOnly(libs.configurate)
    compileOnly(project(":api"))

    implementation(project(":core"))
    implementation(libs.inject)
    implementation(libs.command)
}

bukkit {
    main = "${rootProject.group}.miniannouncer.paper.${rootProject.name}Paper"
    name = rootProject.name
    apiVersion = "1.13"
    version = "${project.version}"
    description = "Simple plugin for automatic announcers"
    website = "https://github.com/jonakls"

    author = "Jonakls"
    softDepend = listOf("PlaceholderAPI")
    loadBefore = listOf("PlaceholderAPI")
}

tasks {
    clean {
        delete("run")
    }

    build {
        dependsOn("shadowJar")
    }

    shadowJar {
        archiveFileName.set("${rootProject.name}-paper-${project.version}.jar")
        archiveClassifier.set("")

        // Relocations
        arrayOf(
            "team.unnamed.inject",
            "dev.triumphteam.cmd",
            "javax.inject"
        ).forEach {
            relocate(it, "${rootProject.group}.miniannouncer.libs.$it")
        }

        exclude("org/jetbrains/annotations/*")
    }

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(17))
        }
    }

    runServer {
        minecraftVersion("1.19.3")
    }
}