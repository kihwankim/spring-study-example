tasks.getByName("bootJar") {
    enabled = true
}

tasks.getByName("jar") {
    enabled = false
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
//            artifact(tasks.named("bootJar"))
            artifact(tasks.named("verifierStubsJar")) // stub jar file creation
            versionMapping { // version setting
                usage("java-api") { // java api setting version
                    fromResolutionOf("runtimeClasspath")
                }
                usage("java-runtime") {
                    fromResolutionResult()
                }
            }
        }
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    testImplementation("org.springframework.cloud:spring-cloud-starter-contract-verifier")
}

contracts {
    baseClassForTests.set("com.example.springcloudcontract.ProducerBase")
}
