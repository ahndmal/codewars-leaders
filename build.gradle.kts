plugins {
    id("java")
    kotlin("jvm") version "1.9.0-Beta"
}

group = "com.andmal"
version = "0.0.1"

repositories {
    mavenCentral()
    gradlePluginPortal()
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "com.andmal.MainKt"
    }
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    from(sourceSets.main.get().output)
    dependsOn(configurations.runtimeClasspath)
    from({
        configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
    })
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    runtimeOnly("org.jetbrains.kotlin:kotlin-stdlib:1.8.21")
    implementation("org.jsoup:jsoup:1.16.1")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("com.google.cloud.functions:functions-framework-api:1.0.1")
    compileOnly("gradle.plugin.com.github.johnrengelman:shadow:7.1.2")
    // https://mvnrepository.com/artifact/org.jetbrains.kotlin/kotlin-stdlib
}

kotlin {
    jvmToolchain(17)
}
