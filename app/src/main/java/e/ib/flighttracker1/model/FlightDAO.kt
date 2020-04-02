package e.ib.flighttracker1.model

data class FlightDAO(
    val icao24 : String,
    val firstSeen : Int,
    val estDepartureAirport : String?,
    val lastSeen : Int,
    val estArrivalAirport : String?,
    val callsign : String?,
    val estDepartureAirportHorizDistance : Int,
    val estArrivalAirportHorizDistance : Int,
    val estDepartureAirportVertDistance : Int,
    val estArrivalAirportVertDistance : Int,
    val departureAirportCandidatesCount : Int,
    val arrivalAirportCandidatesCount : Int) {


    override fun toString(): String {
        return "FlightDAO(icao24='$icao24', firstSeen=$firstSeen, estDepartureAirport=$estDepartureAirport, lastSeen=$lastSeen, estArrivalAirport=$estArrivalAirport, callsign=$callsign, estDepartureAirportHorizDistance=$estDepartureAirportHorizDistance, estArrivalAirportHorizDistance=$estArrivalAirportHorizDistance, estDepartureAirportVertDistance=$estDepartureAirportVertDistance, estArrivalAirportVertDistance=$estArrivalAirportVertDistance, departureAirportCandidatesCount=$departureAirportCandidatesCount, arrivalAirportCandidatesCount=$arrivalAirportCandidatesCount)"
    }
}