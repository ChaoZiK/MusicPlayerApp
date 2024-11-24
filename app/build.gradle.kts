plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.example.musicplayer"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.musicplayer"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

    buildFeatures{
        // For viewBinding
        viewBinding = true

        // For showing build version name
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //val room_version = "2.6.1"

    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)

    //val lifecycle_version = "2.8.7"
    //val arch_version = "2.2.0"

    // ViewModel utilities for Compose
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.livedata.ktx)


    //val fragment_version = "1.8.5"

    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.fragment.compose)

    // Coroutines
    implementation(libs.kotlinx.coroutines.android)

    // Splash Screen API
    implementation(libs.androidx.core.splashscreen)
}