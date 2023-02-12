import org.gradle.kotlin.dsl.`kotlin-dsl`

plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}

repositories {
    mavenCentral()
    google()
}
dependencies {
    implementation("com.android.tools.build:gradle:7.4.1")
    implementation(kotlin("gradle-plugin", "1.8.0"))
}