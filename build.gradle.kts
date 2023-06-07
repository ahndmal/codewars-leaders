plugins {
    id("application")
    kotlin("jvm") version "1.9.0-Beta"
}

repositories {
    mavenCentral()
}

application {
    mainClass.set("com.andmal.Mainn")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jsoup:jsoup:1.16.1")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("com.google.cloud.functions:functions-framework-api:1.0.1")
}

kotlin {
    jvmToolchain(17)
}