plugins {
    kotlin("jvm")
    application
}

application {
    mainClass.set("io.github.iromul.mkstransfer.cli.CliKt")
}

dependencies {
    implementation(projects.core)
    implementation("com.github.ajalt.clikt:clikt:3.3.0")
}