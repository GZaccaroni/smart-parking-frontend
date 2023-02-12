plugins {
    `android-base-lib`
}

android {
    namespace = "it.unibo.smart_parking.presentation"
}

dependencies {

    implementation(Deps.Libraries.androidXCore)
    testImplementation(Deps.TestLibraries.junit)
    androidTestImplementation(Deps.TestLibraries.junitAndroidExt)
    androidTestImplementation(Deps.TestLibraries.espressoCore)
}