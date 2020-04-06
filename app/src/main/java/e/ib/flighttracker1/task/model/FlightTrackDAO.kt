package e.ib.flighttracker1.task.model

data class FlightTrackDAO(
    val aircraft : AircraftDAO,
    val airline : AirlineDAO,
    val arrival : ArrDepDAO,
    val departure : ArrDepDAO,
    val flight : FlightDAO,
    val geography : GeographyDAO,
    val speed : SpeedDAO,
    val status : String,
    val system : SystemDAO
)

