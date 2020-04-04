package e.ib.flighttracker1.task.model

data class AirlineDAO(
    val iataCode : String,
    val icaoCode : String,
    val name : String
) {
    override fun toString(): String {
        return "AirlineDAO(iataCode='$iataCode', icaoCode='$icaoCode', name='$name')"
    }
}