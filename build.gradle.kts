import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.10"
    id("jacoco")
    application
}

group = "me.paologalli"
version = "1.0-SNAPSHOT"

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation("io.horizen:sidechains-sdk:0.3.0")
    implementation("com.google.code.gson:gson:2.8.9")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.1")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClassName = "MainKt"
}