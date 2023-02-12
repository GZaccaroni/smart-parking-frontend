plugins {
    `android-base-lib`
}

android {
    namespace = "it.unibolss.smartparking.domain"
}

dependencies {
    implementation(Deps.Libraries.koin)
    testImplementation(Deps.TestLibraries.junit)
}