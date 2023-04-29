@file:Suppress("UnstableApiUsage")

plugins {
    `android-base-lib`
    id(Deps.BuildPlugins.kotlinSerialization) version Deps.BuildPlugins.Versions.androidKotlin
}

android {
    namespace = "it.unibolss.smartparking.data"

    defaultConfig {
        buildConfigField("String", "API_ENDPOINT", """"http://10.0.2.2:8080/"""")
    }
}

dependencies {
    implementation(project(":domain"))

    implementation(libs.kotlinx.coroutines.core)

    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.retrofit)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.retrofit.kotlin.serialization)
    implementation(libs.androidx.security)

    testImplementation(kotlin("test"))
    testImplementation(libs.koin.test)
    testImplementation(libs.roboelectric)
    testImplementation(libs.junit4)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockk)
}