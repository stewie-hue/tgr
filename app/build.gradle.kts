plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "dev.tgram.reborn"
    compileSdk = 35
    defaultConfig {
        applicationId = "dev.tgram.reborn"
        minSdk = 23
        targetSdk = 35
        versionCode = 3
        versionName = "2.1.0-alpha1"
    }
    buildTypes { release { isMinifyEnabled = false } }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions { jvmTarget = "17" }
}

dependencies {
    // Compile against the classic Xposed API; LSPosed supports this API for legacy modules.
    compileOnly("de.robv.android.xposed:api:82")
}
