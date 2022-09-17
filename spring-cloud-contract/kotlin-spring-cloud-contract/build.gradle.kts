import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    val kotlinVerifierVersion: String by project

    repositories {
        mavenCentral()
        mavenLocal()
    }

    dependencies {
        classpath("org.springframework.cloud:spring-cloud-contract-spec-kotlin:$kotlinVerifierVersion")
    }
}

plugins {
    kotlin("jvm")
    kotlin("plugin.spring") apply false
    id("org.springframework.boot") apply false
    id("io.spring.dependency-management")
    id("maven-publish")
    id("org.springframework.cloud.contract") apply false
}

java.sourceCompatibility = JavaVersion.VERSION_11
val projectGroup: String by project
val applicationVersion: String by project
val kotlinLoggingVersion: String by project

allprojects {
    group = projectGroup
    version = applicationVersion

    repositories {
        mavenCentral()
        mavenLocal()
    }
}

subprojects {

    apply {
        plugin("org.springframework.boot")
        plugin("io.spring.dependency-management")
        plugin("java")
        plugin("kotlin")
        plugin("kotlin-spring")
        plugin("maven-publish")
        plugin("org.springframework.cloud.contract")
    }

    dependencyManagement {
        val springCloudVersion: String by project
        imports {
            mavenBom("org.springframework.cloud:spring-cloud-dependencies:${springCloudVersion}")
        }
    }

    dependencies {
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

        implementation("io.github.microutils:kotlin-logging-jvm:${kotlinLoggingVersion}")
        testImplementation("org.springframework.boot:spring-boot-starter-test")
    }

    tasks.getByName("bootJar") {
        enabled = false
    }

    tasks.getByName("jar") {
        enabled = true
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