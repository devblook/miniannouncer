plugins {
    alias(libs.plugins.shadow)
}

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("org.jetbrains:annotations:24.0.1")

    implementation(libs.configurate)
    implementation(project(":api"))
}

tasks {
    shadowJar {
        archiveFileName.set("${rootProject.name}-core-${project.version}.jar")
        archiveClassifier.set("")

        // Relocations
        arrayOf(
            "team.unnamed.inject",
            "org.jetbrains.annotations",
            "org.spongepowered.configurate"
        ).forEach {
            relocate(it, "${rootProject.group}.miniannouncer.libs.$it")
        }

    }

    build {
        dependsOn("shadowJar")
    }
}