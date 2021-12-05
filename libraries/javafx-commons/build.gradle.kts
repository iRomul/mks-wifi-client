plugins {
    kotlin("jvm")
    id("org.openjfx.javafxplugin") version "0.0.10"
}

javafx {
    version = "17"
    modules = listOf("javafx.controls", "javafx.graphics", "javafx.fxml")
}

dependencies {
    implementation("com.jakewharton.byteunits:byteunits:0.9.1")
}