import org.jetbrains.kotlin.ir.backend.js.compile

plugins {
    `android-base-lib`
    id(Deps.BuildPlugins.kotlinSerialization) version Deps.BuildPlugins.Versions.androidKotlin
}

android {
    namespace = "it.unibolss.smartparking.data"
}

dependencies {
    implementation(project(":domain"))

    implementation(Deps.Libraries.kotlinCoroutines)

    implementation(Deps.Libraries.koin)
    implementation(Deps.Libraries.retrofit)
    implementation(Deps.Libraries.kotlinSerializationJson)
    implementation(Deps.Libraries.kotlinRetrofitSerialization)

    testImplementation(Deps.TestLibraries.junit)
}