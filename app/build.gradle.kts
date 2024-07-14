plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.jetbrains.kotlin.serialization)
    alias(libs.plugins.hilt)
    id ("kotlin-kapt")
}

android {
    namespace = "com.android.mvvmcomposetest"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.android.mvvmcomposetest"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
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
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    sourceSets {
        getByName("main") {
            assets {
                srcDirs("src\\main\\assets", "src\\main\\assets")
            }
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
    implementation(libs.androidx.constraintlayout.compose)
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.navigation.compose)
    implementation(libs.kotlinx.serialization.json)

    //coroutines
    implementation (libs.kotlinx.coroutines.core)
    implementation (libs.kotlinx.coroutines.android)

    implementation(libs.room.runtime)
    implementation (libs.room.ktx)
    implementation(libs.androidx.junit.ktx)
    kapt(libs.room.compiler)

    implementation(libs.hilt.android)
    annotationProcessor(libs.hilt.compiler)
    kapt(libs.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    // Retrofit
    implementation(libs.squareup.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.gson)
    implementation(libs.logging.interceptor)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    androidTestImplementation(libs.core.testing)

    androidTestImplementation ("com.google.dagger:hilt-android-testing:2.49")
    kaptAndroidTest ("com.google.dagger:hilt-android-compiler:2.49")
    kaptAndroidTest ("com.google.dagger:hilt-compiler:2.49")

    // For unit testing
    testImplementation ("com.google.dagger:hilt-android-testing:2.49")
    kaptTest ("com.google.dagger:hilt-compiler:2.49")

    androidTestImplementation ("androidx.test:core:1.6.1")
    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.0")
    testImplementation ("androidx.arch.core:core-testing:2.2.0")
    testImplementation ("org.mockito:mockito-core:4.0.0")
    testImplementation ("org.mockito:mockito-inline:3.9.0")
    testImplementation ("org.mockito:mockito-android:3.9.0")
    testImplementation ("org.mockito.kotlin:mockito-kotlin:4.0.0")

}