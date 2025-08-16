plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.dagger.hilt.android") // Plugin do Hilt
    kotlin("kapt") // Para o processador de anotações do Hilt
}

android {
    namespace = "com.ichin23.salbum"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.ichin23.salbum"
        minSdk = 26
        targetSdk = 35
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
    buildFeatures {
        compose = true
    }
}

kapt {
    correctErrorTypes = true
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
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.compose.ui:ui-graphics-android:1.8.3") // Use a versão do seu projeto
    implementation("io.coil-kt.coil3:coil-compose:3.3.0") //Load Images
    implementation("io.coil-kt.coil3:coil-network-ktor3:3.3.0") //Load Images netowrk
    implementation("io.ktor:ktor-client-android:3.2.3")
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.1") //constraint layout


    // Hilt
    implementation("com.google.dagger:hilt-android:2.56.2") // Ou a versão do Hilt que você definiu no project-level
    kapt("com.google.dagger:hilt-android-compiler:2.56.2")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0") // Integração Hilt com Navigation Compose
    kapt("androidx.hilt:hilt-compiler:1.1.0")

    // Navigation Compose
    implementation("androidx.navigation:navigation-compose:2.8.0-beta02") // Ou a versão mais recente
    implementation("androidx.compose.animation:animation:1.7.0-alpha07")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:3.0.0")
    implementation("com.squareup.retrofit2:converter-gson:3.0.0") // Converter para JSON (Gson)
    implementation("com.squareup.okhttp3:logging-interceptor:5.1.0") // Opcional: para logs de requisições

    // Kotlin Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.10.2") // Ou a versão mais recente
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-swing:1.10.2") // Ou a versão mais recente

    // DataStore (Preferences DataStore)
    implementation("androidx.datastore:datastore-preferences:1.1.7") // Ou a versão mais recente

    // Lifecycle (ViewModel)
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.9.2") // Ou a versão mais recente
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.9.2") // ViewModel para Compose

}