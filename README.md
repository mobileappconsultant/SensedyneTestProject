# Sensyne Test For Android





## Features 🎨

- **100% Kotlin-only project**.
-  Espresso,Mockwebserver REST Api Integration,Dagger2,Moshi,AndroidX,MVVM,Okhttp,RxBinding4,Rxjava3 Instrumentation & JUnit tests.
- 100% Gradle Kotlin DSL setup.
- Dependency versions managed via `buildSrc`.
- CI Setup with GitHub Actions.
- Kotlin Static Analysis via `ktlint` and `detekt`.
- Publishing Ready.
- Issues project (bug report + feature request)




### OUTSTANING TASK AND IMPROVEMENTS
- Increase Unit tests around current use of Mockwebserver
- More Unit tests
- Better Search view options
- Better arrangement of information current recyclerview
- Finishing adding PullToRefresh
- More robust regex testing
- Make a better UI


## Gradle Setup 🐘

This project is using [**Gradle Kotlin DSL**](https://docs.gradle.org/current/userguide/kotlin_dsl.html) as well as the [Plugin DSL](https://docs.gradle.org/current/userguide/plugins.html#sec:plugins_block) to setup the build.

Dependencies are centralized inside the [Dependencies.kt](buildSrc/src/main/java/Dependencies.kt) file in the `buildSrc` folder. This provides convenient auto-completion when writing your gradle files.

## Static Analysis 🔍

This project is using [**ktlint**](https://github.com/pinterest/ktlint) with the [ktlint-gradle](https://github.com/jlleitschuh/ktlint-gradle) plugin to format your code. To reformat all the source code as well as the buildscript you can run the `ktlintFormat` gradle task.

This project is also using [**detekt**](https://github.com/detekt/detekt) to analyze the source code, with the configuration that is stored in the [detekt.yml](config/detekt/detekt.yml) file (the file has been generated with the `detektGenerateConfig` task).

## CI ⚙️


There are currently the following workflows available:
- [Validate Gradle Wrapper](.github/workflows/gradle-wrapper-validation.yml) - Will check that the gradle wrapper has a valid checksum
- [Android Pull Request & Master CI](.github/workflows/workflow.yaml) - Will run the `build`, `check` and `assembleDebug` tasks.

## How To Build
Pull the code on this branch, import into Android Studio, from there you can run it like a standard
android project project or run ./gradlew assembleDebug. Further notes can be found here https://developer.android.com/studio/build/building-cmdline#DebugMode


## Testing
To test the **release** build variant locally:
1. Create key store file outside of the project directory. Please do not commit your keystore file to git.

2. Create or update `~/.gradle/gradle.properties` with:
```aidl
RELEASE_STORE_FILE=<path to key store file>
RELEASE_STORE_PASSWORD=<password>
RELEASE_KEY_ALIAS=<alias>
RELEASE_KEY_PASSWORD=<password>
```
3. Uncomment `signingConfigs` and `release.signingConfig` in app's `build.gradle`


## Publishing 🚀

The project is setup to be **ready to publish** a library/artifact on a Maven Repository. If you're using JitPack, you don't need any further configuration and you can just configure the repo on JitPack. If you're using another repository (MavenCentral/JCenter/etc.), you need to specify the publishing coordinates.
