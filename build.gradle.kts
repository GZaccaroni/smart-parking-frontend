@file:Suppress("UnstableApiUsage")

plugins {
    alias(libs.plugins.google.maps.secrets) apply false

    alias(libs.plugins.dokka)
    alias(libs.plugins.sonarqube)
}
sonarqube {
    properties {
        property("sonar.projectKey", "GZaccaroni_smart-parking-frontend")
        property("sonar.organization", "gzaccaroni")
        property("sonar.host.url", "https://sonarcloud.io")
    }
}
subprojects {
    apply(plugin = rootProject.libs.plugins.dokka.get().pluginId)
}