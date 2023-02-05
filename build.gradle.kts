@file:Suppress("UnstableApiUsage")

plugins {
    id(Deps.BuildPlugins.androidApplication) version Deps.BuildPlugins.Versions.buildTools apply false
    id(Deps.BuildPlugins.androidLibrary) version Deps.BuildPlugins.Versions.buildTools apply false
    id(Deps.BuildPlugins.androidKotlin) version Deps.BuildPlugins.Versions.androidKotlin apply false
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