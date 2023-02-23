@file:Suppress("UnstableApiUsage")

plugins {
    `android-base-lib`
}

android {
    namespace = "it.unibolss.smartparking.presentation"
    buildFeatures {
        compose = true
    }
    buildTypes {
        debug {
            enableUnitTestCoverage = true
        }
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Deps.BuildPlugins.Versions.compilerExtensionVersion
    }
}

dependencies {
    implementation(project(":domain"))

    implementation(Deps.Libraries.koin)
    implementation(Deps.Libraries.koinAndroid)
    implementation(Deps.Libraries.koinAndroidCompose)

    implementation(Deps.Libraries.androidXCore)
    implementation(Deps.Libraries.androidXLifecycleRuntime)
    implementation(Deps.Libraries.androidXActivity)
    implementation(Deps.Libraries.androidXComposeUi)
    implementation(Deps.Libraries.androidXComposeUiToolingPreview)
    implementation(Deps.Libraries.androidXComposeMaterial)
    implementation(Deps.Libraries.androidXComposeNavigation)

    testImplementation(Deps.TestLibraries.junit)
    testImplementation(Deps.TestLibraries.kotlinCoroutines)
    testImplementation(Deps.TestLibraries.mockk)

    androidTestImplementation(Deps.TestLibraries.junitAndroidExt)
    androidTestImplementation(Deps.TestLibraries.espressoCore)
    androidTestImplementation(Deps.TestLibraries.uiTestJunit4)

    debugImplementation(Deps.DebugLibraries.composeUiTooling)
    debugImplementation(Deps.DebugLibraries.composeUiTestManifest)
}