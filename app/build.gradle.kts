plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.dagger.hilt.android)
    alias(libs.plugins.androidx.room)
    id("kotlin-kapt")
}

android {
    namespace = "com.soft.robotics"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.soft.robotics"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        room {
            schemaDirectory("$projectDir/schemas")
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
        viewBinding = true
        dataBinding = true
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

    // Navigation
    implementation (libs.navigation.fragment)
    implementation (libs.navigation.ui)

    // Hilt DI
    implementation(libs.dagger.hilt)
    kapt(libs.dagger.hilt.compiler)
//    implementation ("androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha03")

    // Kotlin Coroutines
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")
    implementation(libs.kotlinx.coroutines)

    // RecycleView
    implementation(libs.recyclerview)
//    implementation(libs.recyclerview.selection)

    // Livedata
    implementation(libs.lifecycle.livedata)

    // ViewModel
    implementation(libs.lifecycle.viewmodel)

    // Runtime
    implementation(libs.lifecycle.runtime)

    // Retrofit
    implementation (libs.retrofit)
    implementation (libs.retrofit.converter.gson)

    // Room Database
    implementation(libs.room.runtime)
    annotationProcessor(libs.room.compiler)
    // To use Kotlin annotation processing tool (kapt)
    kapt(libs.room.compiler)
    // optional - Kotlin Extensions and Coroutines support for Room
    implementation(libs.room.ktx)
    // optional - RxJava2 support for Room
    implementation(libs.room.rxjava2)
    // optional - RxJava3 support for Room
    implementation(libs.room.rxjava3)
    // optional - Guava support for Room, including Optional and ListenableFuture
    implementation(libs.room.guava)
    // optional - Test helpers
    testImplementation(libs.room.testing)
    // optional - Paging 3 Integration
    implementation(libs.room.paging)
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}