@file:Suppress("UnstableApiUsage")

plugins {
    `android-base-lib`
    id(Deps.BuildPlugins.kotlinSerialization) version Deps.BuildPlugins.Versions.androidKotlin
}

android {
    namespace = "it.unibolss.smartparking.data"

    buildTypes.all {
        enableUnitTestCoverage = true
    }
}

dependencies {
    implementation(project(":domain"))

    implementation(Deps.Libraries.kotlinCoroutines)

    implementation(Deps.Libraries.koin)
    implementation(Deps.Libraries.retrofit)
    implementation(Deps.Libraries.kotlinSerializationJson)
    implementation(Deps.Libraries.kotlinRetrofitSerialization)
    implementation(Deps.Libraries.androidXSecurity)

    testImplementation(kotlin("test"))
    testImplementation(Deps.TestLibraries.roboelectric)
    testImplementation(Deps.TestLibraries.junit)
    testImplementation(Deps.TestLibraries.kotlinCoroutines)
    testImplementation(Deps.TestLibraries.mockk)
}