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
            const val dokka = "1.8.10"
            const val sonarqube = "4.0.0.2929"
            const val compilerExtensionVersion = "1.4.0"
            const val googleMapsSecrets = "2.0.1"
        }
        const val androidApplication = "com.android.application"
        const val buildTools = "com.android.tools.build:gradle"
        const val androidLibrary = "com.android.library"
        const val androidKotlin = "org.jetbrains.kotlin.android"
        const val dokka = "org.jetbrains.dokka"
        const val sonarqube = "org.sonarqube"
        const val kotlinSerialization = "org.jetbrains.kotlin.plugin.serialization";
        const val googleMapsSecrets = "com.google.android.libraries.mapsplatform.secrets-gradle-plugin";
    }

    object Libraries {
        internal object Versions {
            const val androidXCore = "1.9.0"
            const val androidXLifecycleRuntime = "2.5.1"
            const val androidXActivity = "1.6.1"
            const val androidXCompose = "1.3.3"
            const val androidXComposeMaterial = "1.3.1"
            const val androidXComposeNavigation = "2.5.3"
            const val androidXSecurity = "1.1.0-alpha05"
            const val androidLifecycleCompose = "2.5.1"

            const val koin = "3.3.3"
            const val koinAndroidCompose = "3.4.2"
            const val kotlinCoroutines = "1.6.4"
            const val kotlinDateTime = "0.4.0"
            const val arrowStack = "1.0.1"
            const val retrofit = "2.9.0"
            const val kotlinSerializationJson = "1.4.1"
            const val kotlinRetrofitSerialization = "0.8.0"

            const val googleMaps = "2.10.0"
            const val playServicesMaps = "18.1.0"
            const val playServicesLocation = "21.0.1"
            const val accompanistPermissions = "0.28.0"
        }
        const val androidXCore = "androidx.core:core-ktx:${Versions.androidXCore}"
        const val androidXLifecycleRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.androidXLifecycleRuntime}"
        const val androidXActivity = "androidx.activity:activity-compose:${Versions.androidXActivity}"
        const val androidXComposeUi = "androidx.compose.ui:ui:${Versions.androidXCompose}"
        const val androidXComposeUiToolingPreview = "androidx.compose.ui:ui-tooling-preview:${Versions.androidXCompose}"
        const val androidXComposeMaterial = "androidx.compose.material:material:${Versions.androidXComposeMaterial}"
        const val androidXComposeNavigation = "androidx.navigation:navigation-compose:${Versions.androidXComposeNavigation}"
        const val androidXSecurity = "androidx.security:security-crypto-ktx:${Versions.androidXSecurity}"
        const val androidLifecycleCompose = "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.androidLifecycleCompose}";

        const val koin = "io.insert-koin:koin-core:${Versions.koin}"
        const val koinAndroid = "io.insert-koin:koin-android:${Versions.koin}"
        const val koinAndroidCompose = "io.insert-koin:koin-androidx-compose:${Versions.koinAndroidCompose}"
        const val kotlinCoroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinCoroutines}"
        const val kotlinDateTime = "org.jetbrains.kotlinx:kotlinx-datetime:${Versions.kotlinDateTime}"
        const val arrowCore = "io.arrow-kt:arrow-core:${Versions.arrowStack}"
        const val arrowFxCoroutines = "io.arrow-kt:arrow-fx-coroutines:${Versions.arrowStack}"

        const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}";
        const val kotlinSerializationJson = "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.kotlinSerializationJson}"
        const val kotlinRetrofitSerialization = "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:${Versions.kotlinRetrofitSerialization}"

        const val googleMaps = "com.google.maps.android:maps-compose:${Versions.googleMaps}"
        const val playServicesMaps = "com.google.android.gms:play-services-maps:${Versions.playServicesMaps}"
        const val playServicesLocation = "com.google.android.gms:play-services-location:${Versions.playServicesLocation}"
        const val accompanistPermissions = "com.google.accompanist:accompanist-permissions:${Versions.accompanistPermissions}"
    }

    object TestLibraries {
        private object Versions {
            const val junit = "4.13.2"
            const val junitAndroidExt = "1.1.5"
            const val espressoCore = "3.5.1"
            const val mockk = "1.13.4"
            const val turbine = "0.12.1"
            const val roboelectric = "4.9"
        }
        const val junit = "junit:junit:${Versions.junit}"
        const val junitAndroidExt = "androidx.test.ext:junit-ktx:${Versions.junitAndroidExt}"
        const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espressoCore}"
        const val uiTestJunit4 = "androidx.compose.ui:ui-test-junit4:${Libraries.Versions.androidXCompose}"
        const val kotlinCoroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Libraries.Versions.kotlinCoroutines}"
        const val mockk = "io.mockk:mockk:${Versions.mockk}"
        const val turbine = "app.cash.turbine:turbine:${Versions.turbine}"
        const val roboelectric = "org.robolectric:robolectric:${Versions.roboelectric}"
    }

    object DebugLibraries {
        const val composeUiTooling = "androidx.compose.ui:ui-tooling:${Libraries.Versions.androidXCompose}"
        const val composeUiTestManifest = "androidx.compose.ui:ui-test-manifest:${Libraries.Versions.androidXCompose}"
    }
}

object ScriptPlugins {
    const val quality = "scripts.quality"
}