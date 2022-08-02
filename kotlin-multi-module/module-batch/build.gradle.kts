tasks.getByName("bootJar") {
    enabled = true
}

tasks.getByName("jar") {
    enabled = false
}

dependencies {
    implementation(project(":module-domain"))
    implementation(project(":module-common"))

    implementation("org.springframework.boot:spring-boot-starter-batch")
    implementation("org.springframework.boot:spring-boot-starter-validation")

    runtimeOnly(project(":module-rds"))

    testImplementation("org.springframework.batch:spring-batch-test")
}