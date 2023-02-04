plugins {
    `java-library`
    alias(libs.plugins.shadow)
    alias(libs.plugins.minecrell)
    alias(libs.plugins.runpaper)
}

repositories {
    mavenLocal()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")

    maven("https://repo.triumphteam.dev/snapshots/")
    maven("https://repo.unnamed.team/repository/unnamed-public/")
    mavenCentral()
}

dependencies {
    compileOnly(libs.spigot)
    compileOnly(libs.placeholder)

    implementation(libs.inject)
    implementation(libs.command)
}

bukkit {
    main = "${rootProject.group}.miniannouncer.${rootProject.name}"
    name = rootProject.name
    apiVersion = "1.13"
    version = "${project.version}"
    description = "Simple plugin for automatic announcers"
    website = "https://github.com/jonakls"

    author = "Jonakls"
    softDepend = listOf("PlaceholderAPI")
}

tasks {
    clean {
        delete("run")
    }

    shadowJar {
        archiveFileName.set("${rootProject.name}-${project.version}.jar")
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
