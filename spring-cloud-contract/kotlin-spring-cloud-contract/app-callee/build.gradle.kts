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
    repositories {
        maven {
            val baseUrl = project.properties["nexusUrl"] as String
            val nexusUrl = "http://$baseUrl/repository/maven-releases"
            url = uri(nexusUrl)
            isAllowInsecureProtocol = true
            credentials {
                username = project.properties["nexusUsername"].toString()
                password = project.properties["nexusPassword"].toString()
            }
        }
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    testImplementation("org.springframework.cloud:spring-cloud-starter-contract-verifier")
    testImplementation("org.springframework.cloud:spring-cloud-contract-spec-kotlin")
}

contracts {
    baseClassForTests.set("com.example.springcloudcontract.ProducerBase")
}
