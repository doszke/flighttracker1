package e.ib.flighttracker1.task.model

data class ArrDepDAO(
    val actualRunway : String?,
    val actualTime : String?,
    val baggage : String?,
    val delay : String?,
    val estimatedRunway : String?,
    val estimatedTime : String?,
    val gate : String?,
    val iataCode : String?,
    val icaoCode : String?,
    val scheduledTime : String?,
    val terminal : String?
) {
    override fun toString(): String {
        return "ArrDepDAO(actualRunway=$actualRunway, actualTime=$actualTime, baggage=$baggage, delay=$delay, estimatedRunway=$estimatedRunway, estimatedTime=$estimatedTime, gate=$gate, iataCode=$iataCode, icaoCode=$icaoCode, scheduledTime=$scheduledTime, terminal=$terminal)"
    }
}