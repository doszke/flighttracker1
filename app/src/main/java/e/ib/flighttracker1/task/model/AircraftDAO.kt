package e.ib.flighttracker1.task.model

data class AircraftDAO(
    val iataCode : String,
    val icao24 : String,
    val icaoCode : String,
    val regNumber : String
)