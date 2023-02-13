@file:Suppress("UnstableApiUsage")

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id(Deps.BuildPlugins.dokka) version Deps.BuildPlugins.Versions.dokka
    id(Deps.BuildPlugins.sonarqube) version Deps.BuildPlugins.Versions.sonarqube
}
sonarqube {
    properties {
        property("sonar.projectKey", "GZaccaroni_smart-parking-frontend")
        property("sonar.organization", "gzaccaroni")
        property("sonar.host.url", "https://sonarcloud.io")
    }
}
subprojects {
    apply(plugin = Deps.BuildPlugins.dokka)
}
allprojects {
    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = "1.8"

            freeCompilerArgs = freeCompilerArgs + listOf(
                "-opt-in=kotlin.time.ExperimentalTime",
                "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
            )
        }
    }
}