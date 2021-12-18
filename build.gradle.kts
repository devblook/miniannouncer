plugins {
    `java-library`
    id("com.github.johnrengelman.shadow") version("7.1.1")
}

repositories {
    mavenLocal()
    maven("https://repo.codemc.io/repository/nms/")
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    mavenCentral()
}

dependencies {
    compileOnly("com.destroystokyo.paper:paper:1.16.5-R0.1-SNAPSHOT")

    implementation("net.kyori:adventure-text-minimessage:4.2.0-SNAPSHOT") {
        exclude("net.kyori", "adventure-api")
    }

    compileOnly("me.clip:placeholderapi:2.10.10")
}

tasks {
    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(16))
        }
    }

    processResources {
        filesMatching("**/*.yml") {
            filter<org.apache.tools.ant.filters.ReplaceTokens>(
                "tokens" to mapOf("version" to project.version)
            )
        }
    }
}
