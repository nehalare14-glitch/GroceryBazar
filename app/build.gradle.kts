plugins {
    id("com.google.gms.google-services")
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.grocerybazaar"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "com.example.grocerybazaar"
        minSdk = 24
        targetSdk = 37
        versionCode = 2
        versionName = "1.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        create("release") {
            storeFile = file(System.getenv("GB_KEYSTORE_PATH")
                ?: error("GB_KEYSTORE_PATH is missing"))
            storePassword = System.getenv("GB_KEYSTORE_PASSWORD")
                ?: error("GB_KEYSTORE_PASSWORD is missing")
            keyAlias = "grocerybazaar"
            keyPassword = System.getenv("GB_KEY_PASSWORD")
                ?: error("GB_KEY_PASSWORD is missing")
        }
    }
    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("release")
            optimization {
                enable = true
                packageScope = setOf("androidx.**", "kotlin.**", "kotlinx.**")
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation("androidx.recyclerview:recyclerview:1.4.0")
    implementation(platform("com.google.firebase:firebase-bom:34.6.0"))
    implementation("com.google.firebase:firebase-auth")
    implementation(libs.activity.ktx)
    implementation(libs.appcompat)
    implementation(libs.constraintlayout)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.ext.junit)
}