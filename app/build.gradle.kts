plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    namespace = "com.example.cinepolis"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.cinepolis"
        minSdk = 21
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
        multiDexEnabled = true
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
        //isCoreLibraryDesugaringEnabled = true
    }

    packaging {
        resources {
            excludes += "META-INF/native-image/org.mongodb/bson/native-image.properties"
            excludes += "META-INF/native-image/org.mongodb/mongodb-driver-core/native-image.properties"
            excludes += "META-INF/proguard/androidx-annotations.pro"
            excludes += "META-INF/proguard/io.realm.realm.pro"
        }
    }

    /*plugins {
        id("com.android.application") version "8.2.0" apply false
    }*/

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.15.0") // Versión estable y actual
    implementation("androidx.appcompat:appcompat:1.7.0") // Versión estable
    implementation("com.google.android.material:material:1.12.0") // Versión estable
    implementation("androidx.constraintlayout:constraintlayout:2.2.0") // Versión estable
    testImplementation("junit:junit:4.13.2") // Versión estable para pruebas unitarias
    androidTestImplementation("androidx.test.ext:junit:1.2.1") // Actualización de junit para pruebas de Android
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1") // Actualización de Espresso para pruebas

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

    //Controlador MongoDB
    implementation("io.realm:realm-android-library:10.10.0")
    implementation("org.mongodb:mongodb-driver-sync:4.5.0") {
        exclude(group = "org.mongodb", module = "bson-record-codec")
    }
    //coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:1.1.5")
    implementation ("org.litote.kmongo:kmongo:4.5.0")
    implementation ("androidx.multidex:multidex:2.0.1")

    //kapt
    kapt("com.github.bumptech.glide:compiler:4.14.2")
    kapt("androidx.room:room-compiler:2.5.0")




}
