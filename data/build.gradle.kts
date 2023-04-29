@file:Suppress("UnstableApiUsage")

plugins {
    `android-base-lib`
    alias(libs.plugins.kotlin.serialization)
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
    implementation(libs.retrofit.core)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.retrofit.kotlin.serialization)
    implementation(libs.androidx.security.ktx)

    testImplementation(kotlin("test"))
    testImplementation(libs.koin.test)
    testImplementation(libs.robolectric)
    testImplementation(libs.junit4)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockk)
}