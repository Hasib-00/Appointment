plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.gms.google.services)
    id("com.google.devtools.ksp")
}

android {
    buildFeatures{
        viewBinding = true
    }
    namespace = "com.example.appointment"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.example.appointment"
        minSdk = 24
        targetSdk = 36
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.googleid)
    implementation(libs.firebase.auth)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation("com.github.bumptech.glide:glide:4.13.0")
    implementation("de.hdodenhof:circleimageview:3.1.0")

    //room database
    implementation("com.github.bumptech.glide:glide:4.16.0")
    ksp("androidx.room:room-compiler:2.8.3")
    implementation("androidx.room:room-runtime:2.8.3")
    implementation("androidx.room:room-ktx:2.8.3")
    val lifecycle_version = "2.9.4"


    // ViewModel
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
    // ViewModel utilities for Compose
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycle_version")
    // LiveData
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version")
    // Lifecycles only (without ViewModel or LiveData)
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycle_version")
    dependencies {
        // For viewModels() and activityViewModels() delegates in Fragments
        implementation("androidx.fragment:fragment-ktx:1.7.0") // Use the latest stable version

        // You also still need the core ViewModel dependency
        implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.4") // Use the latest stable version
    }

}