import org.gradle.api.artifacts.dsl.DependencyHandler

object TestImplementationDependencies {

    //test libs
    val junit = "junit:junit:${Versions.junit_version}"
     val extJUnit = "androidx.test.ext:junit:${Versions.extJunit_version}"
    val espressoCore =
        "androidx.test.espresso:espresso-core:${Versions.espresso_core_version}"
     val core_testing = "androidx.arch.core:core-testing:${Versions.arch_core_testing}"
    private val robolectric = "org.robolectric:robolectric:${Versions.roboelectric_version}"
    private val mockito_inline = "org.mockito:mockito-inline:${Versions.mockito_version}"
    private val coroutine_test =
        "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.kotlin_coroutines_version}"
    val mockwebserver = "com.squareup.okhttp3:mockwebserver:${Versions.okhttp_version}"
    private val core_ktx = "androidx.test:core-ktx:${Versions.coreKtx}"
    private val junit_ext = "androidx.test.ext:junit:${Versions.extJunit_version}"

    val testLibraries = arrayListOf<String>().apply {
        add(junit)
        add(extJUnit)
        add(espressoCore)
        add(core_testing)
        add(robolectric)
        add(mockito_inline)
        add(coroutine_test)
        add(mockwebserver)
        add("org.mockito:mockito-core:${Versions.mockito_version}")
        add(core_ktx)
        add(junit_ext)
    }


    fun DependencyHandler.testImplementation(list: List<String>) {
        list.forEach { dependency ->
            add("testImplementation", dependency)
        }
    }

}