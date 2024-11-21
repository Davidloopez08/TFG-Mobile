buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.8.2")
        classpath("com.android.tools.build:gradle:8.1.4")
    }
}

plugins {
    id("com.android.application") version "8.1.1" apply false
    id("com.android.library") version "7.2.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
}
