plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "edu.vojago.app_healthcheckinsystem"
    compileSdk = 35

    defaultConfig {
        applicationId = "edu.vojago.app_healthcheckinsystem"
        minSdk = 24
        targetSdk = 35
        versionCode = 3
        versionName = "0.2.0"

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
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.retrofit)  // 添加 Retrofit 依赖
    implementation(libs.retrofit.converter.gson)  // 添加 Gson 转换器依赖
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}