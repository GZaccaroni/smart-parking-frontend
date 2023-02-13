object Deps {
    object AndroidSdk {
        const val min = 26
        const val compile = 29
        const val target = compile
    }

    object BuildPlugins {
        object Versions {
            const val buildTools = "7.4.1"
            const val androidKotlin = "1.8.0"
            const val dokka = "1.7.20"
            const val sonarqube = "3.5.0.2730"
            const val compilerExtensionVersion = "1.4.0"
        }
        const val androidApplication = "com.android.application"
        const val buildTools = "com.android.tools.build:gradle"
        const val androidLibrary = "com.android.library"
        const val androidKotlin = "org.jetbrains.kotlin.android"
        const val dokka = "org.jetbrains.dokka"
        const val sonarqube = "org.sonarqube"
    }

    object Libraries {
        internal object Versions {
            const val androidXCore = "1.9.0"
            const val androidXLifecycleRuntime = "2.5.1"
            const val androidXActivity = "1.6.1"
            const val androidXCompose = "1.3.3"
            const val androidXComposeMaterial = "1.3.1"

            const val koin = "3.3.3"
            const val kotlinCoroutines = "1.6.4"
            const val arrowStack = "1.0.1"
        }
        const val androidXCore = "androidx.core:core-ktx:${Versions.androidXCore}"
        const val androidXLifecycleRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.androidXLifecycleRuntime}"
        const val androidXActivity = "androidx.activity:activity-compose:${Versions.androidXActivity}"
        const val androidXComposeUi = "androidx.compose.ui:ui:${Versions.androidXCompose}"
        const val androidXComposeUiToolingPreview = "androidx.compose.ui:ui-tooling-preview:${Versions.androidXCompose}"
        const val androidXComposeMaterial = "androidx.compose.material:material:${Versions.androidXComposeMaterial}"

        const val koin = "io.insert-koin:koin-core:${Versions.koin}"
        const val koinAndroid = "io.insert-koin:koin-android:${Versions.koin}"
        const val kotlinCoroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinCoroutines}"
        const val arrowCore = "io.arrow-kt:arrow-core:${Versions.arrowStack}"
        const val arrowFxCoroutines = "io.arrow-kt:arrow-fx-coroutines:${Versions.arrowStack}"

    }

    object TestLibraries {
        private object Versions {
            const val junit = "4.13.2"
            const val junitAndroidExt = "1.1.5"
            const val espressoCore = "3.5.1"
        }
        const val junit = "junit:junit:${Versions.junit}"
        const val junitAndroidExt = "androidx.test.ext:junit-ktx:${Versions.junitAndroidExt}"
        const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espressoCore}"
        const val uiTestJunit4 = "androidx.compose.ui:ui-test-junit4:${Libraries.Versions.androidXCompose}"
    }

    object DebugLibraries {
        const val composeUiTooling = "androidx.compose.ui:ui-tooling:${Libraries.Versions.androidXCompose}"
        const val composeUiTestManifest = "androidx.compose.ui:ui-test-manifest:${Libraries.Versions.androidXCompose}"
    }
}

object ScriptPlugins {
    const val quality = "scripts.quality"
}