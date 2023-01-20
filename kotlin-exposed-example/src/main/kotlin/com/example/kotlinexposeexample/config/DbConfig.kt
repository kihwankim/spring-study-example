package com.example.kotlinexposeexample.config

import com.example.kotlinexposeexample.entity.PaymentTable
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import jakarta.annotation.PostConstruct
import mu.KotlinLogging
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.AutoConfigurationPackages
import org.springframework.boot.autoconfigure.AutoConfigureAfter
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.*
import org.springframework.core.Ordered
import org.springframework.core.type.filter.AssignableTypeFilter
import org.springframework.core.type.filter.RegexPatternTypeFilter
import org.springframework.transaction.annotation.Transactional
import java.util.regex.Pattern

private val logger = KotlinLogging.logger { }

@Configuration
class DbConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    fun hikariConfig(): HikariConfig {
        return HikariConfig()
    }

    @Bean
    @Primary
    fun dataSurce(): HikariDataSource {
        return HikariDataSource(hikariConfig())
    }

    @Bean
    fun database(): Database = Database.connect(dataSurce())
}

@Configuration
class DDLLocalConfig {
    @Autowired
    lateinit var database: Database

    @PostConstruct
    fun init() {
        transaction(database) {
            SchemaUtils.create(PaymentTable)
        }
    }
}

@Configuration
@Profile("dev")
@AutoConfigureAfter(DataSourceAutoConfiguration::class)
class DDLConfig(
    private val applicationContext: ApplicationContext
) {
    @Value("\${spring.exposed.excluded-packages:}#{T(java.util.Collections).emptyList()}")
    private lateinit var excludedPackages: List<String>

    @Bean
    @ConditionalOnProperty("spring.exposed.generate-ddl", havingValue = "true", matchIfMissing = false)
    fun databaseInitializer() = DatabaseInitializer(applicationContext, excludedPackages)
}

open class DatabaseInitializer(
    private val applicationContext: ApplicationContext,
    private val excludedPackages: List<String>
) : ApplicationRunner, Ordered {

    companion object {
        const val DATABASE_INITIALIZER_ORDER = 0
    }

    override fun getOrder(): Int = DATABASE_INITIALIZER_ORDER

    @Transactional
    override fun run(args: ApplicationArguments?) {
        val exposedTables = discoverExposedTables(applicationContext, excludedPackages)
        logger.info("Schema generation for tables '{}'", exposedTables.map { it.tableName })

        logger.info("ddl {}", exposedTables.map { it.ddl }.joinToString())
        SchemaUtils.create(*exposedTables.toTypedArray())
    }
}

fun discoverExposedTables(applicationContext: ApplicationContext, excludedPackages: List<String>): List<Table> {
    val provider = ClassPathScanningCandidateComponentProvider(false)
    provider.addIncludeFilter(AssignableTypeFilter(Table::class.java)) // table type filter
    excludedPackages.forEach { provider.addExcludeFilter(RegexPatternTypeFilter(Pattern.compile(it.replace(".", "\\.") + ".*"))) }
    val packages = AutoConfigurationPackages.get(applicationContext)
    val components = packages.map { provider.findCandidateComponents(it) }.flatten()
    return components.map { Class.forName(it.beanClassName).kotlin.objectInstance as Table }
}
