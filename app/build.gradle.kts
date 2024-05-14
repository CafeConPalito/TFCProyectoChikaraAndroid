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
    id("kotlin-kapt")
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

            buildConfigField("String","BASE_URL" , "\"https://chikaradev.azurewebsites.net/api/v1/\"")

        }
        getByName("debug") {
            isDebuggable = true
            //buildConfigField("String","BASE_URL" , "\"http://25.0.232.178:8000/api/v1/\"")
            buildConfigField("String","BASE_URL" , "\"https://chikaradev.azurewebsites.net/api/v1/\"")
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

//    testOptions {
//        // Encapsulates options for local unit tests.
//        unitTests {
//            //includeAndroidResources = true
//        }
//    }

}

dependencies {

    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
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


    //DataStore
    implementation ("androidx.datastore:datastore-preferences:1.0.0")

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    //TEST

    //UnitTesting
    testImplementation(libs.junit)
    testImplementation ("io.kotlintest:kotlintest-runner-junit5:3.4.2")
    // Optional -- Mockk framework
    testImplementation ("io.mockk:mockk:1.13.10")
    // Optional -- Robolectric environment
    testImplementation ("androidx.test:core:1.5.0")
    // Optional -- Mockito framework
    testImplementation ("org.mockito:mockito-core:5.12.0")
    // Optional -- mockito-kotlin
    testImplementation ("org.mockito.kotlin:mockito-kotlin:5.3.1")
    //
    testImplementation("com.google.dagger:hilt-android-testing:2.48")


    //UITesting
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.test.espresso:espresso-contrib:3.5.1")
    androidTestImplementation ("androidx.test.espresso:espresso-intents:3.4.0")
    androidTestImplementation ("com.google.dagger:hilt-android-testing:2.48")
    androidTestImplementation ("androidx.fragment:fragment-testing:1.6.1")
    kaptAndroidTest("com.google.dagger:hilt-android-compiler:2.48")
}