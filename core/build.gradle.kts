plugins {
    kotlin("jvm")
}

dependencies {
    implementation(libs.kotlin.stdlib.jdk8)
    implementation(libs.ktor.client.apache)
    implementation(libs.ktor.network)
}
