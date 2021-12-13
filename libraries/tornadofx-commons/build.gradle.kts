plugins {
    kotlin("jvm")
    id("org.openjfx.javafxplugin") version libs.versions.javafxplugin
}

javafx {
    version = "17"
    modules = listOf("javafx.controls", "javafx.graphics", "javafx.fxml")
}

dependencies {
    implementation(projects.libraries.javafxCommons)
    implementation(projects.libraries.stdlibCommons)
    implementation(libs.tornadofx)
}