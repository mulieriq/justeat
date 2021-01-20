plugins {
    id(BuildPlugins.androidLibrary)
    id(BuildPlugins.kotlinAndroid)
    id(BuildPlugins.kotlinAndroidExtensions)
    id(BuildPlugins.kapt)
}

android {
    compileSdkVersion(AndroidSdk.compileSdkVersion)

    android.buildFeatures.dataBinding = true
    android.buildFeatures.viewBinding = true

    defaultConfig {
        minSdkVersion(AndroidSdk.minSdkVersion)
        targetSdkVersion(AndroidSdk.targetSdkVersion)
        vectorDrawables.useSupportLibrary = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildTypes {
        getByName("debug") {
            isDebuggable = true
        }

        getByName("release") {
            isMinifyEnabled = true
            isUseProguard = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(project(BuildModules.coreModule))
    implementation(project(BuildModules.dataModule))
    implementation(project(BuildModules.domainModule))

    implementation(Libraries.kotlinStdLib)
    implementation(Libraries.coreKtx)

    // Material and AndroidX
    implementation(Libraries.constraintLayout)
    implementation(Libraries.appCompat)
    implementation(Libraries.swiperefreshlayout)
    implementation(Libraries.material)

    // Lottie
    implementation(Libraries.lottie)

    // Lifecycle
    implementation(Libraries.viewModel)
    implementation(Libraries.livedata)
    implementation(Libraries.lifecycle)
    implementation(Libraries.viewModelSavedState)

    // DI - KOIN
    implementation(Libraries.koin)
    implementation(Libraries.koinViewModel)

    // Debug - for debug builds only
    implementation(Libraries.timber)

    // UI Tests
    androidTestImplementation(TestLibraries.espresso)
    androidTestImplementation(TestLibraries.kakao)

    // Instrumentation Tests
    androidTestImplementation(TestLibraries.koinTest)
    androidTestImplementation(TestLibraries.androidXJUnit)

    // Unit Tests
    testImplementation(TestLibraries.jUnit)
    testImplementation(TestLibraries.coroutinesTest)
    testImplementation(TestLibraries.archComponentTest)
    testImplementation(TestLibraries.liveDataTesting)
    testImplementation(TestLibraries.mockK)
    testImplementation(TestLibraries.koinTest)
}