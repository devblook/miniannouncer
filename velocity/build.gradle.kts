repositories {
    maven("https://repo.aikar.co/content/groups/aikar/")
}

dependencies {
    compileOnly("com.velocitypowered:velocity-api:3.1.1")
    //compileOnly(libs.configurate)
    compileOnly(project(":api"))

    implementation(project(":core"))
    implementation("co.aikar:acf-velocity:0.5.1-SNAPSHOT")

    annotationProcessor("com.velocitypowered:velocity-api:3.1.1")
}