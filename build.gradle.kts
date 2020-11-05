// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        jcenter()
        maven(url = uri("https://plugins.gradle.org/m2/"))
    }
    dependencies {
        classpath("com.android.tools.build:gradle:4.1.0")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigation_version}")
        classpath("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:${Versions.detekt_version}")
        classpath("org.jlleitschuh.gradle:ktlint-gradle:${Versions.klint_version}")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
/*    plugins {
        id( "org.jlleitschuh.gradle.ktlint")
    }*/
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}