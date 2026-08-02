import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val libraryReadableName = providers.gradleProperty("libraryReadableName").get()
val libraryDescription = providers.gradleProperty("libraryDescription").get()
val libraryGroup = providers.gradleProperty("libraryGroup").get()
val libraryName = providers.gradleProperty("libraryName").get()
val libraryVersion = providers.gradleProperty("libraryVersion").get()

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.vanniktech.maven.publish)
    signing
}

group = libraryGroup
version = libraryVersion
description = libraryDescription

base {
    this@base.archivesName = libraryName
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.kotlinx.serialization)

    testImplementation(libs.kotlin.test)
}

java {
    withSourcesJar()
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
        moduleName.set(libraryName)
    }
}

signing {
    useGpgCmd()
}

mavenPublishing {
    publishToMavenCentral()
    signAllPublications()

    coordinates(groupId = libraryGroup, artifactId = libraryName, version = libraryVersion)

    pom {
        name = libraryReadableName
        description = libraryDescription
        url = "https://github.com/Xueria/fabric-json-serialization"

        licenses {
            license {
                name = "MIT License"
                url = "https://opensource.org/licenses/MIT"
            }
        }

        developers {
            developer {
                name = "Xueria"
            }
        }

        scm {
            url = "https://github.com/Xueria/fabric-json-serialization"
        }
    }
}