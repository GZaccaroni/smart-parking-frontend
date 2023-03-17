@file:Suppress("UnstableApiUsage")

plugins {
    id(Deps.BuildPlugins.androidApplication)
    id(Deps.BuildPlugins.androidKotlin)
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
    buildTypes.all {
        enableUnitTestCoverage = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
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
    testImplementation(Deps.TestLibraries.koin)
    testImplementation(Deps.TestLibraries.roboelectric)
}