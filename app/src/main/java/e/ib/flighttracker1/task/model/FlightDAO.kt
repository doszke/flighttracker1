package e.ib.flighttracker1.task.model

data class FlightDAO(
    val iataNumber : String,
    val icaoNumber : String,
    val number : String
) {
    override fun toString(): String {
        return "FlightDAO(iataNumber='$iataNumber', icaoNumber='$icaoNumber', number=$number)"
    }
}