@file:Suppress("UnstableApiUsage")

buildscript {
    extra.apply {
        set("compose_ui_version", "1.3.3")
    }
}// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "7.4.1" apply false
    id("com.android.library") version "7.4.1" apply false
    id("org.jetbrains.kotlin.android") version "1.8.0" apply false
    id("org.jetbrains.dokka") version "1.7.20"
    id("org.sonarqube") version "3.5.0.2730"
}
sonarqube {
    properties {
        property("sonar.projectKey", "GZaccaroni_smart-parking-frontend")
        property("sonar.organization", "gzaccaroni")
        property("sonar.host.url", "https://sonarcloud.io")
    }
}
subprojects {
    apply(plugin = "org.jetbrains.dokka")
}