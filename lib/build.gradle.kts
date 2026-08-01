import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
}

val libraryName = providers.gradleProperty("libraryName").get()
val libraryVersion = providers.gradleProperty("libraryVersion").get()

base {
    this@base.archivesName = libraryName
    version = libraryVersion
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.kotlinx.serialization)

    testImplementation(libs.kotlin.test)
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

tasks.withType(JavaCompile::class).configureEach {
    options.release.set(8)
}

tasks.withType<KotlinCompile>().configureEach {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_1_8)
        moduleName.set("lib")
    }
}