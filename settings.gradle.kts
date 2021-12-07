@file:Suppress("UnstableApiUsage")

rootProject.name = "mks-wifi-transfer"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
enableFeaturePreview("VERSION_CATALOGS")

include("core")
include("libraries:gcode-file")
include("libraries:javafx-commons")
include("libraries:stdlib-commons")
include("ui-app")

object Versions {

    const val kotlin = "1.5.31"
    const val tornadofx = "1.7.20"
    const val jna = "5.10.0"
    const val slf4j = "1.7.5"
    const val byteunits = "0.9.1"
    const val ktor = "1.6.4"
    const val junitBom = "5.8.2"
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }

    versionCatalogs {
        create("libs") {
            version("javafxplugin", "0.0.10")
            version("kotlin", Versions.kotlin)
            alias("assertk-jvm").to("com.willowtreeapps.assertk:assertk-jvm:0.25")
            alias("byteunits").to("com.jakewharton.byteunits:byteunits:${Versions.byteunits}")
            alias("jna").to("net.java.dev.jna:jna:${Versions.jna}")
            alias("jna-platform").to("net.java.dev.jna:jna-platform:${Versions.jna}")
            alias("junit-bom").to("org.junit:junit-bom:5.8.2")
            alias("koin-core").to("io.insert-koin:koin-core:3.1.4")
            alias("koin-logger-slf4j").to("io.insert-koin:koin-logger-slf4j:3.1.4")
            alias("koin-test").to("io.insert-koin:koin-test:3.1.4")
            alias("kotest-assertions-core").to("io.kotest:kotest-assertions-core:5.0.1")
            alias("kotlin-logging-jvm").to("io.github.microutils:kotlin-logging-jvm:2.0.10")
            alias("kotlin-reflect").to("org.jetbrains.kotlin:kotlin-reflect:${Versions.kotlin}")
            alias("kotlin-stdlib").to("org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}")
            alias("kotlin-stdlib-jdk7").to("org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}")
            alias("kotlin-stdlib-jdk8").to("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}")
            alias("ktor-client-apache").to("io.ktor:ktor-client-apache:${Versions.ktor}")
            alias("ktor-network").to("io.ktor:ktor-network:${Versions.ktor}")
            alias("slf4j-api").to("org.slf4j:slf4j-api:${Versions.slf4j}")
            alias("slf4j-log4j12").to("org.slf4j:slf4j-log4j12:${Versions.slf4j}")
            alias("tornadofx").to("no.tornado:tornadofx:${Versions.tornadofx}")
        }
    }
}