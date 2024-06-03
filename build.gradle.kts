// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false

    //DaggerHilt
    //alias(libs.plugins.ksp) apply false
    //alias(libs.plugins.hilt) apply false
    //Lo mismo
    //id("com.google.devtools.ksp")version "1.9.10-1.0.13" apply false
    id("com.google.dagger.hilt.android")version "2.50" apply false

    //Safeargs
    id("androidx.navigation.safeargs.kotlin") version "2.7.7" apply false

}