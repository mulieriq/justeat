plugins {
    id(BuildPlugins.androidApplication)
    id(BuildPlugins.kotlinAndroid)
    id(BuildPlugins.kotlinAndroidExtensions)
    id(BuildPlugins.ktlintPlugin)
    id(BuildPlugins.kapt)
    id(BuildPlugins.jacocoAndroid)
}

jacoco {
    toolVersion = Versions.jacoco
}

android {

    compileSdkVersion(AndroidSdk.compileSdkVersion)
    buildToolsVersion("30.0.2")

    android.buildFeatures.dataBinding = true
    android.buildFeatures.viewBinding = true

    defaultConfig {
        applicationId = "com.justeat"
        minSdkVersion(AndroidSdk.minSdkVersion)
        targetSdkVersion(AndroidSdk.targetSdkVersion)
        versionCode = AndroidSdk.versionCode
        versionName = AndroidSdk.versionName
        vectorDrawables.useSupportLibrary = true
        testInstrumentationRunner = "com.justeat.runner.MockTestRunner"
    }

    testOptions {
        animationsDisabled = true
        unitTests.apply {
            isReturnDefaultValues = true
            isIncludeAndroidResources = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    signingConfigs {
        getByName("debug") {
            storeFile = file("../keystore/debug.keystore")
            keyAlias = "androiddebugkey"
            keyPassword = "android"
            storePassword = "android"
        }
    }

    buildTypes {
        getByName("debug") {
            isDebuggable = true
            versionNameSuffix = " - debug"
            applicationIdSuffix = ".debug"
            signingConfig = signingConfigs.getByName("debug")
        }

        getByName("release") {
            isShrinkResources = true
            isMinifyEnabled = true
        }
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(project(BuildModules.coreModule))
    implementation(project(BuildModules.dataModule))
    implementation(project(BuildModules.domainModule))
    implementation(project(BuildModules.presentationModule))

    implementation(Libraries.kotlinStdLib)
    implementation(Libraries.coreKtx)

    // Material
    implementation(Libraries.material)

    // DI - KOIN
    implementation(Libraries.koin)
    implementation(Libraries.koinViewModel)

    // Lifecycle
    implementation(Libraries.viewModel)
    implementation(Libraries.livedata)
    implementation(Libraries.lifecycle)
    implementation(Libraries.viewModelSavedState)

    // Debug - for debug builds only
    implementation(Libraries.timber)
    debugImplementation(Libraries.leakCanary)

    // UI Tests
    androidTestImplementation(TestLibraries.espresso)
    androidTestImplementation(TestLibraries.kakao)
    androidTestImplementation(TestLibraries.androidMockK)

    // Instrumentation Tests
    androidTestImplementation(TestLibraries.koinTest)
    androidTestImplementation(TestLibraries.androidXJUnit)
}