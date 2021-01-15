plugins {
    id(BuildPlugins.androidLibrary)
    id(BuildPlugins.kotlinAndroid)
    id(BuildPlugins.kotlinAndroidExtensions)
    id(BuildPlugins.kapt)
}

android {
    compileSdkVersion(AndroidSdk.compileSdkVersion)

    defaultConfig {
        minSdkVersion(AndroidSdk.minSdkVersion)
        targetSdkVersion(AndroidSdk.targetSdkVersion)
        vectorDrawables.useSupportLibrary = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    implementation(project(BuildModules.domainModule))

    implementation(Libraries.kotlinStdLib)
    implementation(Libraries.coreKtx)

    // Debug - for debug builds only
    implementation(Libraries.timber)

    // Unit Tests
    testImplementation(TestLibraries.jUnit)
    testImplementation(TestLibraries.roomTest)
    testImplementation(TestLibraries.koinTest)
    testImplementation(TestLibraries.mockK)
    testImplementation(TestLibraries.mockWebServer)
    testImplementation(TestLibraries.roboelectric)
    testImplementation(TestLibraries.truth)
    testImplementation(TestLibraries.runner)
    testImplementation(TestLibraries.androidXJUnit)
    testImplementation(TestLibraries.coroutinesTest)
    testImplementation(TestLibraries.archComponentTest)
    testImplementation(TestLibraries.liveDataTesting)
}