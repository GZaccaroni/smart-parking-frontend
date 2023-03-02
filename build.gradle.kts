@file:Suppress("UnstableApiUsage")

plugins {
    id(Deps.BuildPlugins.dokka) version Deps.BuildPlugins.Versions.dokka
    id(Deps.BuildPlugins.sonarqube) version Deps.BuildPlugins.Versions.sonarqube
    id(Deps.BuildPlugins.googleMapsSecrets) version Deps.BuildPlugins.Versions.googleMapsSecrets apply false
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