plugins {
    application
    kotlin("jvm")
    id("org.openjfx.javafxplugin") version "0.0.10"
}

javafx {
    version = "17"
    modules = listOf("javafx.controls", "javafx.graphics", "javafx.fxml")
}

application {
    mainClass.set("io.github.iromul.mkstransfer.app.HelloFX")
    applicationDefaultJvmArgs += listOf(
        "--add-opens", "javafx.graphics/javafx.scene=ALL-UNNAMED",
        "--add-opens", "javafx.graphics/com.sun.glass.ui=ALL-UNNAMED"
    )
}

dependencies {
    implementation(projects.core)
    implementation(kotlin("stdlib-jdk8"))
    implementation("no.tornado:tornadofx:1.7.20")
    implementation("com.jakewharton.byteunits:byteunits:0.9.1")
    implementation("net.java.dev.jna:jna:5.10.0")
    implementation("net.java.dev.jna:jna-platform:5.10.0")
    implementation("org.slf4j:slf4j-api:1.7.5")
    implementation("org.slf4j:slf4j-log4j12:1.7.5")
    implementation("io.github.microutils:kotlin-logging-jvm:2.0.10")
    // Koin for Kotlin apps
    implementation("io.insert-koin:koin-core:3.1.4")
    implementation("io.insert-koin:koin-logger-slf4j:3.1.4")
    // Testing
    testImplementation("io.insert-koin:koin-test:3.1.4")
    testImplementation(platform("org.junit:junit-bom:5.8.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("com.willowtreeapps.assertk:assertk-jvm:0.25")
}
