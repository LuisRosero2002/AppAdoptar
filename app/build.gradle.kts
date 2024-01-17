plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.appfirebaselogin"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.appfirebaselogin"
        minSdk = 24
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures{
        viewBinding = true
    }
}

dependencies {
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-auth-ktx:22.3.0")
    implementation("com.google.firebase:firebase-database:20.3.0")
    implementation ("androidx.cardview:cardview:1.0.0")
    implementation ("com.google.code.gson:gson:2.8.9")
    implementation("com.android.support:support-annotations:28.0.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation(platform("com.google.firebase:firebase-bom:32.3.1"))
    implementation ("com.google.android.material:material:1.5.0")
    implementation("androidx.core:core-splashscreen:1.0.1")
    implementation ("androidx.databinding:databinding-runtime:7.0.0")
    implementation ("com.squareup.retrofit2:converter-scalars:2.9.0")
    implementation("androidx.fragment:fragment-ktx:1.6.2")
    implementation("androidx.activity:activity-ktx:1.7.1")
    implementation ("androidx.recyclerview:recyclerview:1.2.1")
    implementation ("androidx.recyclerview:recyclerview:1.2.1")

}