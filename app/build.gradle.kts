plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.kotlin.compose)

  kotlin("plugin.serialization")
  id("com.google.devtools.ksp")
  id("com.google.dagger.hilt.android")
}

android {
  namespace = "com.yi_555555555.codelingo"
  compileSdk = 36

  defaultConfig {
    applicationId = "com.yi_555555555.codelingo"
    minSdk = 28
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
  kotlin {
    compilerOptions {
      jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11)
    }
  }
  buildFeatures {
    compose = true
  }
  packaging {
    resources {
      excludes += listOf("META-INF/versions/9/OSGI-INF/MANIFEST.MF")
    }
  }
}

dependencies {
  // Credential Manager
  implementation("androidx.credentials:credentials:1.6.0")
  implementation("androidx.credentials:credentials-play-services-auth:1.6.0")

  // Google ID
  implementation("com.google.android.libraries.identity.googleid:googleid:1.2.0")

  implementation("com.colintheshots:twain:0.3.2")

  implementation("io.coil-kt.coil3:coil-compose:3.4.0")
  implementation("io.coil-kt.coil3:coil-svg:3.4.0")
  implementation("io.coil-kt.coil3:coil-network-okhttp:3.4.0")

  implementation("dev.snipme:kodeview:0.9.0")

  implementation(libs.logging.interceptor)
  implementation(libs.okhttp)
  implementation(libs.retrofit)
  implementation(libs.converter.gson)

  implementation(libs.androidx.hilt.navigation.compose)
  implementation(libs.hilt.android)
  ksp(libs.hilt.android.compiler)

  implementation(libs.androidx.lifecycle.viewmodel.compose)
  implementation(libs.androidx.navigation.compose)
  implementation(libs.kotlinx.serialization.json)

  implementation(libs.androidx.compose.material.icons.core)

  implementation(libs.androidx.room.runtime)
  ksp(libs.androidx.room.compiler)
  implementation(libs.androidx.room.ktx)

  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.lifecycle.runtime.ktx)
  implementation(libs.androidx.activity.compose)
  implementation(platform(libs.androidx.compose.bom))
  implementation(libs.androidx.ui)
  implementation(libs.androidx.ui.graphics)
  implementation(libs.androidx.ui.tooling.preview)
  implementation(libs.androidx.material3)
  testImplementation(libs.junit)
  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(libs.androidx.espresso.core)
  androidTestImplementation(platform(libs.androidx.compose.bom))
  androidTestImplementation(libs.androidx.ui.test.junit4)
  debugImplementation(libs.androidx.ui.tooling)
  debugImplementation(libs.androidx.ui.test.manifest)
}