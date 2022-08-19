dependencies {
    implementation("org.springframework.kafka:spring-kafka")
    testImplementation("org.springframework.kafka:spring-kafka-test")

    implementation(project(":module-common"))

    compileOnly(project(":pay-service:module-domain"))
    testCompileOnly(project(":pay-service:module-domain"))
}
