dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.kafka:spring-kafka")
    testImplementation("org.springframework.kafka:spring-kafka-test")

    implementation(project(":module-common"))
    implementation(project(":order-service:module-infra:module-infra-common"))

    compileOnly(project(":order-service:module-domain"))
    testCompileOnly(project(":order-service:module-domain"))
}
