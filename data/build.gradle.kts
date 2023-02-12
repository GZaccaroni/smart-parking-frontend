import org.jetbrains.kotlin.ir.backend.js.compile

plugins {
    `android-base-lib`
}

android {
    namespace = "it.unibolss.smartparking.data"
}

dependencies {
    implementation(project(":domain"))

    implementation(Deps.Libraries.koin)
    testImplementation(Deps.TestLibraries.junit)
}