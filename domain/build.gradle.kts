plugins {
    `android-base-lib`
}

android {
    namespace = "it.unibo.smart_parking.domain"
}

dependencies {
    testImplementation(Deps.TestLibraries.junit)
}