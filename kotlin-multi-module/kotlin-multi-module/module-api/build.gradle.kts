import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
	id("org.springframework.boot")
	id("io.spring.dependency-management")
	kotlin("jvm")
	kotlin("plugin.spring")
}

dependencies {
	implementation(project(":module-rds"))
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
}

tasks.getByName<BootJar>("bootJar") {
	enabled = true
	mainClass.set("com.example.kotlinmultimodule.ModuleApiApplication.kt")
}

tasks.getByName<Jar>("jar") {
	enabled = false
}

tasks.register("prepareKotlinBuildScriptModel")