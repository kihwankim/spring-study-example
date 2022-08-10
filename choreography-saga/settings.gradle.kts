rootProject.name = "choreography-saga"

include("module-common")

include(
    "pay-service:module-api",
    "pay-service:module-domain",
    "pay-service:module-infra:module-rds",
    "pay-service:module-infra:module-kafka"
)

include(
    "order-service:module-api",
    "order-service:module-domain",
    "order-service:module-infra:module-rds",
    "order-service:module-infra:module-kafka",
    "order-service:module-infra:module-infra-common",
)

pluginManagement {
    val kotlinVersion: String by settings
    val springBootVersion: String by settings
    val springDependencyManagementVersion: String by settings

    resolutionStrategy {
        eachPlugin {
            when (requested.id.id) {
                "org.jetbrains.kotlin.jvm" -> useVersion(kotlinVersion)
                "org.jetbrains.kotlin.plugin.spring" -> useVersion(kotlinVersion)
                "org.jetbrains.kotlin.plugin.jpa" -> useVersion(kotlinVersion)
                "org.springframework.boot" -> useVersion(springBootVersion)
                "io.spring.dependency-management" -> useVersion(springDependencyManagementVersion)
            }
        }
    }
}