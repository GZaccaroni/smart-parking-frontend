@file:Suppress("UnstableApiUsage")

plugins {
    `android-base-lib`
}

android {
    namespace = "it.unibolss.smartparking.presentation"
    buildFeatures {
        compose = true
    }
    buildTypes.all {
        enableUnitTestCoverage = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Deps.BuildPlugins.Versions.compilerExtensionVersion
    }
    packagingOptions {
        resources.excludes.addAll(
            listOf(
                "META-INF/DEPENDENCIES",
                "META-INF/NOTICE",
                "META-INF/LICENSE",
                "META-INF/LICENSE.md",
                "META-INF/LICENSE-notice.md",
                "META-INF/LICENSE.txt",
                "META-INF/NOTICE.txt"
            )
        )
    }
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
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
    testImplementation(Deps.TestLibraries.turbine)
    testImplementation(Deps.TestLibraries.espressoCore)
    testImplementation(Deps.TestLibraries.roboelectric)
    testImplementation(Deps.TestLibraries.uiTestJunit4)
    testImplementation(Deps.DebugLibraries.composeUiTestManifest)

    debugImplementation(Deps.DebugLibraries.composeUiTooling)
    debugImplementation(Deps.DebugLibraries.composeUiTestManifest)
}
