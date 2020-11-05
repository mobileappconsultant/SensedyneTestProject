import org.gradle.api.artifacts.dsl.DependencyHandler

object AppCompileOnlyDependencies {
    private val glassfish = "org.glassfish:javax.annotation:${Versions.glass_fish_version}"
    val compileOnlyLibraries = arrayListOf<String>().apply {
        add(glassfish)
    }
    fun DependencyHandler.compileOnly(list: List<String>) {
        list.forEach { dependency ->
            add("compileOnly", dependency)
        }
    }
}