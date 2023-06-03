plugins {
  `java-library`
  alias(libs.plugins.shadow)
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

  compileOnly(libs.inject)
  compileOnly(libs.command)
}

tasks {
  clean {
    delete("run")
  }

  shadowJar {
    archiveFileName.set("${rootProject.name}-${project.version}.jar")
    archiveClassifier.set("")

    exclude("org/jetbrains/annotations/*")
  }

  java {
    toolchain {
      languageVersion.set(JavaLanguageVersion.of(17))
    }
  }

  processResources {
    filesMatching("paper-plugin.yml") {
      expand("version" to project.version)
    }
  }

  runServer {
    minecraftVersion("1.19.3")
  }
}
