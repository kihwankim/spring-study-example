tasks.getByName("bootJar") {
    enabled = true
}

tasks.getByName("jar") {
    enabled = false
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")

    implementation(project(":module-common"))
    implementation(project(":order-service:module-domain"))

    runtimeOnly(project(":order-service:module-infra:module-rds"))
    runtimeOnly(project(":order-service:module-infra:module-event-producer"))
    runtimeOnly(project(":order-service:module-infra:module-event-consumer"))
}
