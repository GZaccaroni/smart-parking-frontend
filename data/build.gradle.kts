import org.jetbrains.kotlin.ir.backend.js.compile

plugins {
    `android-base-lib`
}

android {
    namespace = "it.unibo.smart_parking.data"
}

dependencies {
    implementation(project(":domain"))

    implementation(Deps.Libraries.koin)
    testImplementation(Deps.TestLibraries.junit)
}