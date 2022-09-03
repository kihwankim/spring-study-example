tasks.getByName("bootJar") {
    enabled = true
}

tasks.getByName("jar") {
    enabled = false
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
}

//contracts {
//    contractsDslDir = new File("../ftgo-order-service-contracts/src/main/resources/contracts")
//    packageWithBaseClasses = 'net.chrisrichardson.ftgo.orderservice.contract'
//    generatedTestSourcesDir = project.file("${project.buildDir}/generated-integration-test-sources/contracts")
//}