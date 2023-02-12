plugins {
    `android-base-lib`
}

android {
    namespace = "it.unibo.smart_parking.domain"
}

dependencies {
    implementation(Deps.Libraries.koin)
    testImplementation(Deps.TestLibraries.junit)
}