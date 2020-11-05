//version constants for the Kotlin DSL dependencies
object Versions {
    //app level
    const val gradle = "4.1.0"
    const val kotlin = "1.4.10"

    //libs
    const val coreKtx = "1.2.0"
    const val constraintLayout = "2.0.3"
    const val rxjava3_version = "3.0.7"
    const val rxandroid_version = "3.0.0"
    const val rxbinding_version = "4.0.0"

    //Navigation
    const val navigation_fragment_ktx_version = "2.3.1"
    const val navigation_ui_ktx_version = navigation_fragment_ktx_version

    //Dagger
    const val dagger2_version = "2.29.1"

    //Retrofit
    const val retrofit_coroutines_adapter_version = "0.9.2"

    const val retrofit_version = "2.9.0"


    //Okhttp

    val okhttp_version = "4.9.0"

    //Moshi
    val moshi_version = "1.9.2"

    //Coroutines
    val kotlin_coroutines_version = "1.4.0"

    //Glide
    val glide_version = "4.10.0"

    //Detekt
    val detekt_version = "1.14.2"

    //Support Libraries
    val recyclerview_version = "1.1.0"
    val card_view_version = "1.0.0"
    val material_version = "1.3.0-alpha03"
    val room_database_version = "2.2.5"


    //Test


    // Dependencies for local unit tests
    val junit_version = "4.13.1"
    val extJunit_version = "1.1.2"
    val roboelectric_version = "4.3.1"
    val mockito_version = "3.6.0"


    // Dependencies for Android instrumented unit tests
    val room_testing_version = "2.2.5"
    val dexmaker_version = "2.12.1"
    val idlingresource_version = "1.0.0"
    val espresso_core_version = "3.3.0"
    val timber= "4.7.1"

    val glass_fish_version = "10.0-b28"

    // AndroidX Test - JVM testing
    val test_core_ktx_version = "1.3.0"
    val leakcanary_version = "2.5"

    // AndroidX Test - Instrumented testing
    val arch_core_testing = "2.1.0"


    val arguments = mapOf(
        "room.schemaLocation" to "${"projectDir/schemas"}",
        "room.incremental" to "true",
        "room.expandProjection" to "true"
    )



    const val klint_version = "9.4.1"
    //Navigation
    const val navigation_version = "2.2.1"
    const val lifecycle_version = "2.2.0"
}
