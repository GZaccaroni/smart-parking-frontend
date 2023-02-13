import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

plugins {
    `android-base-lib`
}

android {
    namespace = "it.unibolss.smartparking.domain"
}

dependencies {
    implementation(Deps.Libraries.koin)
    implementation(Deps.Libraries.kotlinCoroutines)
    implementation(Deps.Libraries.arrowCore)
    implementation(Deps.Libraries.arrowFxCoroutines)
    testImplementation(Deps.TestLibraries.junit)
    testImplementation(Deps.TestLibraries.kotlinCoroutines)
    testImplementation(Deps.TestLibraries.mockk)
}