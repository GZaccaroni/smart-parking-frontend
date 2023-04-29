@file:Suppress("UnstableApiUsage")
plugins {
    `android-base-lib`
}

android {
    namespace = "it.unibolss.smartparking.domain"
}

dependencies {
    api(libs.arrow.core)
    api(libs.kotlinx.datetime)

    implementation(libs.koin.core)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.arrow.fx.coroutines)

    testImplementation(libs.junit4)
    testImplementation(libs.koin.test)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockk)
}