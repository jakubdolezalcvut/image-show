plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
}

android {
    compileSdkVersion(28)

    defaultConfig {
        minSdkVersion(21)
        targetSdkVersion(28)
        applicationId = "com.dolezal.image"
        versionName = "1.0.0"
        versionCode = 1
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    dexOptions {
        javaMaxHeapSize = "2g"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildTypes {
        getByName("debug") {
            isDebuggable = true
        }
        getByName("release") {
            isDebuggable = false
        }
    }
}

androidExtensions {
    isExperimental = true
}

kapt {
    arguments {
        arg("toothpick_registry_package_name", "com.dolezal.image")
    }
}

dependencies {
    // KOTLIN RUNTIME
    implementation(embeddedKotlin("stdlib"))

    // ANDROIDX -- JETPACK
    implementation("androidx.appcompat:appcompat:1.0.2")
    implementation("androidx.constraintlayout:constraintlayout:1.1.3")
    implementation("androidx.lifecycle:lifecycle-viewmodel:2.0.0")
    implementation("androidx.lifecycle:lifecycle-livedata:2.0.0")
    implementation("androidx.lifecycle:lifecycle-extensions:2.0.0")
    implementation("com.google.android.material:material:1.1.0-alpha08")

    // UI LIBRARIES
    implementation("me.zhanghai.android.materialprogressbar:library:1.6.1")

    // NETWORKING LIBRARIES
    val okHttpVersion = "3.14.2"
    implementation("com.squareup.okhttp3:okhttp:$okHttpVersion")
    implementation("com.squareup.okhttp3:logging-interceptor:$okHttpVersion")

    // TOOTHPICK
    implementation("com.github.stephanenicolas.toothpick:toothpick-runtime:2.1.0")
    kapt("com.github.stephanenicolas.toothpick:toothpick-compiler:2.1.0")

    // RX JAVA
    implementation("io.reactivex.rxjava2:rxjava:2.2.7")
    implementation("io.reactivex.rxjava2:rxkotlin:2.3.0")
    implementation("io.reactivex.rxjava2:rxandroid:2.1.1")

    // TESTING LIBRARIES
    testImplementation("junit:junit:4.12")
    testImplementation("org.mockito:mockito-core:2.23.0")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.2.0")
    androidTestImplementation("androidx.test:rules:1.2.0")
    androidTestImplementation("androidx.test:runner:1.2.0")
}