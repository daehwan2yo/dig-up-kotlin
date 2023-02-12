group = "org.playground"
version = "1.0.0"

plugins {
    kotlin("jvm") apply true
}

repositories {
    mavenCentral()
}

buildscript {
    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "kotlin")

    repositories {
        mavenCentral()
    }

    tasks.getByName<Test>("test") {
        useJUnitPlatform()
    }

    dependencies {
        testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
        testImplementation("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    }
}