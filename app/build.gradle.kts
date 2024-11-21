plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    namespace = "com.example.cinepolis"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.cinepolis"
        minSdk = 21
        targetSdk = 34
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

    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.13.1") // Versión estable y actual
    implementation("androidx.appcompat:appcompat:1.7.0") // Versión estable
    implementation("com.google.android.material:material:1.12.0") // Versión estable
    implementation("androidx.constraintlayout:constraintlayout:2.1.4") // Versión estable
    testImplementation("junit:junit:4.13.2") // Versión estable para pruebas unitarias
    androidTestImplementation("androidx.test.ext:junit:1.1.5") // Actualización de junit para pruebas de Android
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1") // Actualización de Espresso para pruebas

    // Librería Glide (añadir kapt si usas anotaciones)
    implementation("com.github.bumptech.glide:glide:4.14.2")
    kapt("com.github.bumptech.glide:compiler:4.14.2") // Versión de compilador correspondiente

    // Librerías Retrofit y OkHttp3
    implementation("com.squareup.retrofit2:retrofit:2.9.0") // Versión estable
    implementation("com.squareup.retrofit2:converter-gson:2.9.0") // Versión estable
    implementation("com.squareup.okhttp3:okhttp:4.11.0") // Versión estable
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0") // Mismo versionado que okhttp

    // Exoplayer
    implementation("com.google.android.exoplayer:exoplayer:2.19.1") // Versión estable

    // YouTube Android Player (puedes elegir usar la dependencia de JAR)
    //implementation(files("libs/youtube-android-player-api.jar")) // Incluye el archivo JAR en la carpeta libs
}

