dependencies {
    implementation((project(":module-service")))
    implementation(project(":module-rds"))
    implementation(project(":module-common"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    testImplementation(testFixtures(project(":module-service")))
}