package e.ib.flighttracker1.model

import android.content.Context
import org.springframework.web.util.UriComponentsBuilder
import java.util.*

object OpenSkyRestAPIUrl {

    private var root : String = ""
    private const val flightsAll = "/flights/all"
    private const val statesAll = "/states/all"
    private const val flightsAircraft = "/flights/aircraft"

    private var isInitialized = false

    fun init(context : Context) {
        val p = Properties()
        val assetManager = context.assets
        val inputStream = assetManager.open("opensky.properties")
        p.load(inputStream)
        val username = p.getProperty("username")
        val password = p.getProperty("password")
        root = "https://$username:$password@opensky-network.org/api"
        isInitialized = true
    }

    fun flightsAllUriComponentsBuilder() : UriComponentsBuilder {
        return UriComponentsBuilder.fromHttpUrl(root + flightsAll)
    }

    fun statesAllUriComponentsBuilder() : UriComponentsBuilder {
        return UriComponentsBuilder.fromHttpUrl(root + statesAll)
    }

    fun flightsAircraftsUriComponentsBuilder(): UriComponentsBuilder {
        return UriComponentsBuilder.fromHttpUrl(root + flightsAircraft)
    }

}