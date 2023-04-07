import org.apache.tools.ant.taskdefs.condition.Os

plugins {
    `java-library`
}

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://repo.unnamed.team/repository/unnamed-public/")
    maven("https://oss.sonatype.org/content/repositories/snapshots/")
    mavenLocal()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.0")
}

tasks {
    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(17))
        }
    }

    withType<JavaCompile>().configureEach {
        if (Os.isFamily(Os.FAMILY_WINDOWS)) {
            options.encoding = "UTF-8"
        }
    }

    compileJava {
        options.compilerArgs.add("-parameters")
    }

}