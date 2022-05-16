import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.21"
    application
}

group = "dev.antonius"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))

    implementation("org.slf4j:slf4j-log4j12:1.7.32")
    implementation("com.github.tsantalis:refactoring-miner:2.2.0")
    implementation("org.eclipse.jgit:org.eclipse.jgit:6.1.0.202203080745-r")
    implementation("com.github.gumtreediff:core:3.0.0")
    implementation("com.github.gumtreediff:client:3.0.0")

    // Add dependencies to support parsing java files
    implementation("com.github.gumtreediff:gen.javaparser:3.0.0")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

application {
    mainClass.set("MainKt")
}
