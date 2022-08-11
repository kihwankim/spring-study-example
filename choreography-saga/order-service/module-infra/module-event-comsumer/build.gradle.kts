dependencies {
    implementation("org.springframework.kafka:spring-kafka")
    testImplementation("org.springframework.kafka:spring-kafka-test")

    implementation(project(":module-common"))

    compileOnly(project(":order-service:module-domain"))
    testCompileOnly(project(":order-service:module-domain"))
}
