import org.gradle.api.artifacts.dsl.DependencyHandler

object DebugImplementationDependencies {

    val leak_canary = "com.squareup.leakcanary:leakcanary-android:${Versions.leakcanary_version}"
    val android_fragment_test = "androidx.fragment:fragment-testing:${Versions.fragment_version}"

    val debugImplentationLibraries = arrayListOf<String>().apply {
        add(leak_canary)
     //add(android_fragment_test)
    }


    fun DependencyHandler.debugImplementation(list: List<String>) {
        list.forEach { dependency ->
            add("debugImplementation", dependency)
        }
    }
}