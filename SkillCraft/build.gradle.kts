import org.gradle.kotlin.dsl.*

plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

buildscript {
    configurations.all {
        resolutionStrategy.cacheChangingModulesFor(0, "seconds")
    }
}

configurations.all {
    resolutionStrategy.cacheChangingModulesFor(0, "seconds")
}

repositories {
    mavenCentral()
    maven { url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/") }
    maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots") }
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.20.4-R0.1-SNAPSHOT") {
        isChanging = true
    }
}

tasks.test {
    useJUnitPlatform()
}