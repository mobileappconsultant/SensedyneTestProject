import org.gradle.api.artifacts.dsl.DependencyHandler

object KaptDependencies {

    private val roomKapt = "androidx.room:room-compiler:${Versions.room_database_version}"

    val kaptLibs = arrayListOf<String>().apply {
        add(roomKapt)
        add(daggerKapt)

    }


    //util functions for adding the different type dependencies from build.gradle file
    fun DependencyHandler.kapt(list: List<String>) {
        list.forEach { dependency ->
            add("kapt", dependency)
        }
    }




    private val daggerKapt = "com.google.dagger:dagger-compiler:${Versions.dagger2_version}"

    val kaptTestLibs = arrayListOf<String>().apply {
        add(daggerKapt)
    }


    fun DependencyHandler.kaptTest(list: List<String>) {
        list.forEach { dependency ->
            add("kaptTest", dependency)
        }
    }





    private val daggerAndroidKapt = "com.google.dagger:dagger-compiler:${Versions.dagger2_version}"
    val kaptAndroidTestLibs = arrayListOf<String>().apply {
        add(daggerAndroidKapt)
    }

    fun DependencyHandler.kaptAndroidTest(list: List<String>) {
        list.forEach { dependency ->
            add("kaptAndroidTest", dependency)
        }
    }

}