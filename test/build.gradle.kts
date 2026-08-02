plugins {
    alias(libs.plugins.kotlin.jvm)
}

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    implementation(libs.kotlinx.serialization)
    implementation("io.github.xueria:fabric-json-serialization:1.0.0-alpha.1")
}