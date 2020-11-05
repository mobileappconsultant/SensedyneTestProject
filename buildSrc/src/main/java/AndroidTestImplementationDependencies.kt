import org.gradle.api.artifacts.dsl.DependencyHandler

object AndroidTestImplementationDependencies {
// AndroidX Test - Instrumented testing

    val idling_resource =
        "com.jakewharton.espresso:okhttp3-idling-resource:${Versions.idlingresource_version}"
    val dexmaker = "com.linkedin.dexmaker:dexmaker-mockito:${Versions.dexmaker_version}"
    val room_testing = "android.arch.persistence.room:testing:${Versions.room_testing_version}"
    private val mockito_core = "org.mockito:mockito-inline:${Versions.mockito_version}"
    val androidDebugImplentationLibraries = arrayListOf<String>().apply {
        add(TestImplementationDependencies.junit)
        add(mockito_core)
        add(room_testing)
        add(dexmaker)
        add(TestImplementationDependencies.espressoCore)
        add(TestImplementationDependencies.mockwebserver)
        add(TestImplementationDependencies.core_testing)
        add(TestImplementationDependencies.extJUnit)
        add(idling_resource)
    }

    fun DependencyHandler.androidTestImplementation(list: List<String>) {
        list.forEach { dependency ->
            add("androidTestImplementation", dependency)
        }
    }
}