plugins {
    kotlin("jvm")
}

dependencies {
    implementation(libs.kotlin.stdlib.jdk8)
    testImplementation(platform(libs.junit.bom))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation(libs.kotest.assertions.core)
}