import com.android.sensyneapplication.domain.database.DBConstants.retrieveAllRegexAsList
var string = "HU17 0FA"
fun main() {
    var fla = retrieveAllRegexAsList()
        .filter { it.second.matcher(string).matches() }.map { it.first }.toList()
    fla.forEach {
        println(it)
    }
}
