@file:Suppress("UnstableApiUsage")

plugins {
    id(Deps.BuildPlugins.androidApplication)
    id(Deps.BuildPlugins.androidKotlin)

    id(ScriptPlugins.quality)
}

android {
    namespace = AppConfig.applicationId
    compileSdk = AppConfig.AndroidSdk.compile

    defaultConfig {
        applicationId = AppConfig.applicationId
        minSdk = AppConfig.AndroidSdk.min
        targetSdk = AppConfig.AndroidSdk.target
        versionCode = 1
        versionName = rootProject.file("version.txt").readText().trim()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Deps.BuildPlugins.Versions.compilerExtensionVersion
    }
    packagingOptions {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":data"))
    implementation(project(":presentation"))

    implementation(Deps.Libraries.koin)
    implementation(Deps.Libraries.koinAndroid)

    testImplementation(Deps.TestLibraries.junit)
    androidTestImplementation(Deps.TestLibraries.junitAndroidExt)
    androidTestImplementation(Deps.TestLibraries.espressoCore)
    androidTestImplementation(Deps.TestLibraries.uiTestJunit4)
    debugImplementation(Deps.DebugLibraries.composeUiTooling)
    debugImplementation(Deps.DebugLibraries.composeUiTestManifest)
}