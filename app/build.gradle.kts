
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-kapt")
}

android {
    namespace = "com.assignment.bookspot"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.assignment.bookspot"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    signingConfigs{
        create("bookSpotRelease"){
            storeFile  = file("bookSpot.keystore")
            storePassword = "bookSpot"
            keyAlias = "bookSpot"
            keyPassword ="bookSpot"
        }
    }

    buildTypes {
        getByName("release") {
            signingConfig = signingConfigs.getByName("bookSpotRelease")
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    flavorDimensions.add("version")
    productFlavors {
        create("bookSpot") {
            dimension = "version"
            applicationIdSuffix = ".bookSpot"
            applicationId = "com.assignment.bookspot"
            versionNameSuffix = "-bookSpot"
            resValue("string", "app_name", "BookSpot ROW")
            signingConfig = signingConfigs.getByName("bookSpotRelease")
        }
        create("bookSpot_us"){
            dimension = "version"
            applicationIdSuffix = ".bookSpot"
            applicationId = "com.assignment.bookspot_us"
            versionNameSuffix = "-bookSpot"
            resValue("string", "app_name", "BookSpot US")
            signingConfig = signingConfigs.getByName("bookSpotRelease")
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
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.runtime.livedata)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.room.common)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Retrofit for REST API calls
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    // OkHttp for HTTP client functionality
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)

    // Kotlin Coroutines for asynchronous programming
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    // Lifecycle components for ViewModel and LiveData (Optional, but recommended)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.viewmodel.ktx.v261)
    implementation(libs.androidx.lifecycle.livedata.ktx.v261)

    // Image rendering
    implementation(libs.coil.compose)

    // Room database
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.runtime)
    annotationProcessor(libs.androidx.room.compiler.v261)

    //Navigation
    implementation(libs.androidx.navigation.compose)


}