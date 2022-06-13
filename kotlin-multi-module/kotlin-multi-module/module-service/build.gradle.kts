tasks.jar {
    enabled = true
}

tasks.bootJar {
    enabled = false
}

plugins {
    `java-test-fixtures`
}

dependencies {
    runtimeOnly(project(":module-rds"))
}