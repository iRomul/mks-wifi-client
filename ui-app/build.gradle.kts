plugins {
    application
    kotlin("jvm")
    id("org.openjfx.javafxplugin") version libs.versions.javafxplugin
}

javafx {
    version = "17"
    modules = listOf("javafx.controls", "javafx.graphics", "javafx.fxml")
}

application {
    mainClass.set("io.github.iromul.mkstransfer.app.MksWifiUiApp")
    applicationDefaultJvmArgs += listOf(
        "--add-opens", "javafx.graphics/javafx.scene=ALL-UNNAMED",
        "--add-opens", "javafx.graphics/com.sun.glass.ui=ALL-UNNAMED"
    )
}

dependencies {
    implementation(libs.jna)
    implementation(libs.jna.platform)
    implementation(libs.koin.core)
    implementation(libs.koin.logger.slf4j)
    implementation(libs.kotlin.logging.jvm)
    implementation(libs.kotlin.reflect)
    implementation(libs.kotlin.stdlib.jdk8)
    implementation(libs.slf4j.api)
    implementation(libs.slf4j.log4j12)
    implementation(libs.tornadofx)
    implementation(projects.core)
    implementation(projects.libraries.gcodeFile)
    implementation(projects.libraries.javafxCommons)
    implementation(projects.libraries.stdlibCommons)
    testImplementation(libs.koin.test)
    testImplementation(platform(libs.junit.bom))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation(libs.assertk.jvm)
}
