package e.ib.flighttracker1.task.model

import android.content.Context
import org.springframework.web.util.UriComponents
import org.springframework.web.util.UriComponentsBuilder
import java.lang.Exception
import java.util.*

object AviationEdgeUriFactory {

    private var root: String = "https://aviation-edge.com/v2/public"
    private lateinit var key: String

    private const val cities = "/cityDatabase"
    private const val flight = "/flights"
    private const val nearby = "/nearby"
    private const val timetable = "/timetable"
    private const val autocomplete = "/autocomplete"
    private const val airportDatabase = "/airportDatabase"

    private var isInitialized = false

    fun init(context: Context) {
        val p = Properties()
        val assetManager = context.assets
        val inputStream = assetManager.open("aviation.properties")
        p.load(inputStream)
        key = p.getProperty("key")
        isInitialized = true
    }

    private fun createUri(builder: UriComponentsBuilder, map: Map<String, String>): UriComponents {
        builder.queryParam("key", key)
        for (k in map.keys) {
            builder.queryParam(k, map[k])
        }
        return builder.build()
    }

    fun citiesURI(map: Map<String, String>?): String {
        if (map != null) {
            val builder = UriComponentsBuilder.fromHttpUrl(root + cities)
            return createUri(builder, map).toUriString()
        } else throw Exception("Passed no arguments to UriFactory")
    }


    fun nearbyURI(map: Map<String, String>?): String {
        if (map != null) {
            val builder = UriComponentsBuilder.fromHttpUrl(root + nearby)
            return createUri(builder, map).toUriString()
        } else throw Exception("Passed no arguments to UriFactory")
    }

    fun timetablesURI(map: Map<String, String>?): String {
        if (map != null) {
            val builder = UriComponentsBuilder.fromHttpUrl(root + timetable)
            return createUri(builder, map).toUriString()
        } else throw Exception("Passed no arguments to UriFactory")
    }
    fun autocompleteURI(map: Map<String, String>?) : String {
        if (map != null) {
            val builder = UriComponentsBuilder.fromHttpUrl(root + autocomplete)
            return createUri(builder, map).toUriString()
        } else throw Exception("Passed no arguments to UriFactory")
    }

    fun airportDatabaseURI(map: Map<String, String>?) : String {
        if (map != null) {
            val builder = UriComponentsBuilder.fromHttpUrl(root + airportDatabase)
            return createUri(builder, map).toUriString()
        } else throw Exception("Passed no arguments to UriFactory")
    }

    fun flightURI(map: Map<String, String>?) : String {
        if (map != null) {
            val builder = UriComponentsBuilder.fromHttpUrl(root + flight)
            return createUri(builder, map).toUriString()
        } else throw Exception("Passed no arguments to UriFactory")
    }

}