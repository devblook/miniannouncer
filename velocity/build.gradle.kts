plugins {
    alias(libs.plugins.shadow)
}

repositories {
    maven("https://repo.aikar.co/content/groups/aikar/")
}

dependencies {
    compileOnly("com.velocitypowered:velocity-api:3.1.1")
    //compileOnly(libs.configurate)
    compileOnly(project(":api"))

    implementation(project(":core")) {
        exclude(group = "org.spongepowered", module = "configurate-yaml")
    }
    implementation("co.aikar:acf-velocity:0.5.1-SNAPSHOT")

    annotationProcessor("com.velocitypowered:velocity-api:3.1.1")
}

tasks {

    build {
        dependsOn("shadowJar")
    }

    shadowJar {
        archiveFileName.set("${rootProject.name}-velocity-${project.version}.jar")
        archiveClassifier.set("")

        // Relocations
        arrayOf(
            "co.aikar.commands",
            "javax.inject"
        ).forEach {
            relocate(it, "${rootProject.group}.miniannouncer.libs.$it")
        }

        exclude("org/jetbrains/annotations/*")
    }

}