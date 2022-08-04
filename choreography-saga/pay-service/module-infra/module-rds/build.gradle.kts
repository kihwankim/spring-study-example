allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
}

val springDataKotlinJdslVersion: String by project

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.hibernate:hibernate-spatial")
    implementation("com.linecorp.kotlin-jdsl:spring-data-kotlin-jdsl-starter:${springDataKotlinJdslVersion}")

    runtimeOnly("com.h2database:h2")

    implementation(project(":module-common"))

    compileOnly(project(":pay-service:module-domain"))
    testCompileOnly(project(":pay-service:module-domain"))
}
