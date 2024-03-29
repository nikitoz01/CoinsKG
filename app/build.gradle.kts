plugins {
    id("org.jetbrains.kotlin.android")
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")
    id("kotlin-parcelize")
}

android {
    compileSdk = 33

    defaultConfig {
        applicationId = "kg.mobile.coins"
        minSdk = 27
        targetSdk = 33
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
    compileOptions {
        sourceCompatibility(JavaVersion.VERSION_11)
        targetCompatibility(JavaVersion.VERSION_11)
    }
    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        viewBinding = true
    }
    namespace = "kg.mobile.coins"
}

dependencies {

    // Android
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.5.1")
    implementation("com.google.android.material:material:1.7.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // Fragment and activity
    implementation("androidx.fragment:fragment-ktx:1.5.5")
    implementation("androidx.activity:activity-ktx:1.6.1")

    // Splashscreen
    implementation("androidx.core:core-splashscreen:1.0.0")

    // Fragment navigation
    implementation("androidx.navigation:navigation-fragment-ktx:2.5.3")
    implementation("androidx.navigation:navigation-ui-ktx:2.5.3")
//    implementation ("androidx.navigation:navigation-safe-args-gradle-plugin:2.4.2")
    // Lifecycle and ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.5.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.5.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-savedstate:2.5.1")

    implementation("androidx.cardview:cardview:1.0.0")

    implementation("androidx.legacy:legacy-support-v4:1.0.0")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")

    // Retrofit and GSON
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.retrofit2:converter-jackson:2.9.0")

    // Glade
    implementation("com.github.bumptech.glide:glide:4.13.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.13.0")

    //Paging3
    implementation("androidx.paging:paging-runtime-ktx:3.1.1")

    // Dagger2
    implementation("com.google.dagger:dagger:2.44.2")
    kapt("com.google.dagger:dagger-compiler:2.44.2")

    // Room
    implementation("androidx.room:room-runtime:2.4.3")
    annotationProcessor("androidx.room:room-compiler:2.4.3")
    kapt("androidx.room:room-compiler:2.4.3")

    // optional - Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:2.4.3")
    // optional - Paging 3 Integration
    implementation("androidx.room:room-paging:2.4.3")

    // WorkManager
    implementation("androidx.work:work-runtime-ktx:2.7.1")

    // Skeleton
    implementation("com.github.EudyContreras:Skeleton-Bones:v1.4.5")

    // Test
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation("androidx.test:runner:1.4.0")
    androidTestImplementation("androidx.test:rules:1.4.0")
    // Optional -- UI testing with Espresso
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation("com.google.truth:truth:1.1.3")


    testImplementation("junit:junit:4.13.2")
    // Optional -- Robolectric environment
    testImplementation("androidx.test:core:1.5.0-alpha01")
    // Optional -- Mockito framework
    testImplementation("org.mockito:mockito-core:4.6.0")
    // Optional -- mockito-kotlin
    testImplementation("org.mockito.kotlin:mockito-kotlin:3.2.0")
    // Optional -- Mockk framework
    testImplementation("io.mockk:mockk:1.12.4")

    // LeakCanary
// debugImplementation ("com.squareup.leakcanary:leakcanary-android:2.9.1")

}