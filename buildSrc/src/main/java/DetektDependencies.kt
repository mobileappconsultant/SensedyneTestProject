import org.gradle.api.artifacts.dsl.DependencyHandler

object DetektDependencies {

    private val detektFormatting =
        "io.gitlab.arturbosch.detekt:detekt-formatting:${Versions.detekt_version}"
    private val detektCli = "io.gitlab.arturbosch.detekt:detekt-cli:${Versions.detekt_version}"
    val detektLibraries = arrayListOf<String>().apply {
        add(detektFormatting)
        add(detektCli)
    }
    fun DependencyHandler.detekt(list: List<String>) {
        list.forEach { dependency ->
            add("detekt", dependency)
        }
    }
}