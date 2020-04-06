package e.ib.flighttracker1.task.model

data class FlightScheduleDAO(
    val airline: AirlineDAO,
    val arrival: ArrDepDAO,
    val codeshared : FlightScheduleDAO?,
    val departure: ArrDepDAO,
    val flight : FlightDAO,
    val status : String,
    val type : String
) {
    override fun toString(): String {
        return "FlightScheduleDAO(airline=$airline, arrival=$arrival, codeshared=$codeshared, departure=$departure, flight=$flight, status='$status', type='$type')"
    }
}