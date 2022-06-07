import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    val kotlinVersion = "1.6.21"

    id("org.springframework.boot") version "2.7.0"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"

    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    kotlin("plugin.jpa") version kotlinVersion
}

repositories {
    mavenCentral()
}

java.sourceCompatibility = JavaVersion.VERSION_11
val kotlinLoggingVersion = "2.1.20"

allprojects {
    repositories {
        mavenCentral()
    }
}

subprojects {
    group = "com.example"
    version = "0.0.1-SNAPSHOT"

    repositories {
        mavenCentral()
    }

    apply {
        plugin("org.springframework.boot")
        plugin("io.spring.dependency-management")
        plugin("java")
        plugin("kotlin")
        plugin("kotlin-kapt")
        plugin("kotlin-jpa")
        plugin("kotlin-spring")
    }

    dependencies {
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

        // kotlin logging
        implementation("io.github.microutils:kotlin-logging-jvm:${kotlinLoggingVersion}")

        testImplementation("org.springframework.boot:spring-boot-starter-test")
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "11"
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}