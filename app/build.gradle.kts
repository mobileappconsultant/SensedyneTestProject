import AndroidTestImplementationDependencies.androidTestImplementation
import AppCompileOnlyDependencies.compileOnly
import AppCoreDependencies.implementation
import DebugImplementationDependencies.debugImplementation
import DetektDependencies.detekt
import KaptDependencies.kapt
import KaptDependencies.kaptAndroidTest
import KaptDependencies.kaptTest
import TestImplementationDependencies.testImplementation

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")
    id("io.gitlab.arturbosch.detekt")
    id("org.jlleitschuh.gradle.ktlint")
}

android {
    compileSdkVersion(AppConfig.compileSdk)
    buildToolsVersion(AppConfig.buildToolsVersion)

    defaultConfig {
        applicationId(AppConfig.applicationId)
        minSdkVersion(AppConfig.minSdk)
        targetSdkVersion(AppConfig.targetSdk)
        versionCode(AppConfig.versionCode)
        versionName(AppConfig.versionName)
        testInstrumentationRunner(AppConfig.androidTestInstrumentation)

        javaCompileOptions {
            annotationProcessorOptions {
                arguments(Versions.arguments)
            }
        }
    }

    buildTypes {
        getByName("release") {
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
        //    useIR = true
    }

/*    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.compose_version
        kotlinCompilerVersion = "1.4.0"
    }*/
}

dependencies {

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation(AppCoreDependencies.appLibraries)

    testImplementation(TestImplementationDependencies.testLibraries)

    kapt(KaptDependencies.kaptLibs)

    kaptAndroidTest(KaptDependencies.kaptAndroidTestLibs)

    kaptTest(KaptDependencies.kaptTestLibs)

    compileOnly(AppCompileOnlyDependencies.compileOnlyLibraries)

    detekt(DetektDependencies.detektLibraries)

    debugImplementation(DebugImplementationDependencies.debugImplentationLibraries)

    androidTestImplementation(AndroidTestImplementationDependencies.androidDebugImplentationLibraries)
}
