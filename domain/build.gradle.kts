import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

plugins {
    `android-base-lib`
}

android {
    namespace = "it.unibolss.smartparking.domain"
}

dependencies {
    api(Deps.Libraries.arrowCore)
    api(Deps.Libraries.kotlinDateTime)

    implementation(Deps.Libraries.koin)
    implementation(Deps.Libraries.kotlinCoroutines)
    implementation(Deps.Libraries.arrowFxCoroutines)
    testImplementation(Deps.TestLibraries.junit)
    testImplementation(Deps.TestLibraries.kotlinCoroutines)
    testImplementation(Deps.TestLibraries.mockk)
}