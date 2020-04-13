package e.ib.flighttracker1

import java.io.Serializable

enum class GoogleMapsMode : Serializable {

    ALL_FLIGHTS_IN_RADIUS, ALL_AIRPORTS_IN_RADIUS, ONE_FLIGHT;

    companion object {
        const val serialVersionUID = 1L
    }

}