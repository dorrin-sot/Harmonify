plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.kotlin.compose)
  alias(libs.plugins.hilt.android)
  kotlin("kapt")
  id("kotlin-parcelize")
}

android {
  namespace = "com.dorrin.harmonify"
  compileSdk = 35

  defaultConfig {
    applicationId = "com.dorrin.harmonify"
    minSdk = 24
    targetSdk = 35
    versionCode = 1
    versionName = "1.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
  }
  kotlinOptions {
    jvmTarget = "11"
  }
  buildFeatures {
    compose = true
  }
}

dependencies {
  implementation(libs.retrofit)
  implementation(libs.converter.gson)
  implementation(libs.androidx.navigation.compose)
  implementation(libs.androidx.material)
  implementation(libs.androidx.runtime.livedata)
  implementation(libs.compose)
  implementation(libs.androidx.media3.session)
  androidTestImplementation(libs.androidx.espresso.core)
  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(libs.androidx.ui.test.junit4)
  androidTestImplementation(platform(libs.androidx.compose.bom))
  debugImplementation(libs.androidx.ui.test.manifest)
  debugImplementation(libs.androidx.ui.tooling)
  implementation(libs.androidx.activity.compose)
  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.lifecycle.runtime.ktx)
  implementation(libs.androidx.material.icons.core)
  implementation(libs.androidx.material.icons.extended)
  implementation(libs.androidx.material3)
  implementation(libs.androidx.navigation.fragment.ktx)
  implementation(libs.androidx.navigation.ui.ktx)
  implementation(libs.androidx.ui)
  implementation(libs.androidx.ui.graphics)
  implementation(libs.androidx.ui.tooling.preview)
  implementation(platform(libs.androidx.compose.bom))
  testImplementation(libs.junit)

  implementation(libs.androidx.hilt.navigation.compose)

  implementation(libs.hilt.android)
  testImplementation(libs.hilt.android.testing)
  androidTestImplementation(libs.hilt.android.testing)
  kapt(libs.hilt.compiler)
  kaptTest(libs.hilt.compiler)
  kaptAndroidTest(libs.hilt.compiler)

  implementation(libs.androidx.media3.exoplayer)
  implementation(libs.androidx.media3.exoplayer.dash)
  implementation(libs.androidx.media3.ui)
  implementation(libs.androidx.media3.ui.compose)
}