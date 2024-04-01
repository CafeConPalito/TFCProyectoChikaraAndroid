plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)

    //DaggerHilt
    //alias(libs.plugins.ksp)
    //alias(libs.plugins.hilt)
    //Es lo mismo
    id("com.google.devtools.ksp")version "1.9.10-1.0.13"
    id("com.google.dagger.hilt.android")version "2.50"

    //Safeargs
    id("androidx.navigation.safeargs.kotlin")

}

android {
    namespace = "com.cafeconpalito.chikara"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.cafeconpalito.chikara"
        minSdk = 27
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    //Tipos de Configuracion en funcion del estado de la aplicacion.
    //Permite tener diferentes Ficheros de config segun lo que hacemos y Datos en estos
    //Importante BASE_URL para atacar a la API DE debug y de produccion.
    //Es necesario poner las Buidl Features -> BuildConfig = True
    buildTypes {
        getByName("release"){
            isMinifyEnabled = false
            isDebuggable = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            buildConfigField("String","BASE_URL" , "\"https://newastro.vercel.app/\"")

        }
        getByName("debug") {
            isDebuggable = true
            buildConfigField("String","BASE_URL" , "\"https://newastro.vercel.app/\"")
        }

    }

    buildFeatures{
        buildConfig = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    //Para activar el View Binding
    viewBinding{
        enable=true
    }
}

dependencies {

    //navigation Fragments
    val navVersion = "2.7.7"
    implementation("androidx.navigation:navigation-fragment-ktx:$navVersion")
    implementation("androidx.navigation:navigation-ui-ktx:$navVersion")

    //DaggerHilt
    //ksp(libs.hilt.android.compiler)
    //implementation(libs.hilt.android)
    //Es lo mismo
    implementation("com.google.dagger:hilt-android:2.50")
    ksp("com.google.dagger:hilt-android-compiler:2.50")

    //Retrofit2
    val retrofitVersion = "2.9.0"
    implementation("com.squareup.retrofit2:retrofit:${retrofitVersion}")
    implementation("com.squareup.retrofit2:converter-gson:${retrofitVersion}")
    //interceptor Guarda en el log las respuestas de la API, o Añadir info a los envios AUTH, va con retrofit
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
    //implementation("com.google.code.gson:gson:2.10.1")

    //Camera X
    val cameraVersion = "1.3.2"
    implementation ("androidx.camera:camera-core:${cameraVersion}")
    implementation ("androidx.camera:camera-camera2:${cameraVersion}")
    implementation ("androidx.camera:camera-lifecycle:${cameraVersion}")
    implementation ("androidx.camera:camera-view:${cameraVersion}")
    implementation ("androidx.camera:camera-extensions:${cameraVersion}")


    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}