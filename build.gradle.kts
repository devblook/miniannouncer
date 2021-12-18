plugins {
    `java-library`
}

repositories {
    mavenLocal()
    maven("https://papermc.io/repo/repository/maven-public/")
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    mavenCentral()
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.17.1-R0.1-SNAPSHOT")

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
