package e.ib.flighttracker1.model

data class FlightTrackDAO(

    val icao24 : String,
    val startTime : Long,
    val endTime : Long,
    val callsign : String?,
    private val path : Array<Array<String>>
) {

    private var initialized = false

    var track : Array<FlightTrackElementDAO>? = null
        get() {
            if (!initialized) {
                track = FlightTrackElementFactory.createArray(path)
                initialized = true
            }
            return field
        }
        set(value) {}

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FlightTrackDAO

        if (icao24 != other.icao24) return false
        if (startTime != other.startTime) return false
        if (endTime != other.endTime) return false
        if (callsign != other.callsign) return false
        if (!path.contentDeepEquals(other.path)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = icao24.hashCode()
        result = 31 * result + startTime.hashCode()
        result = 31 * result + endTime.hashCode()
        result = 31 * result + (callsign?.hashCode() ?: 0)
        result = 31 * result + path.contentDeepHashCode()
        return result
    }
}

data class FlightTrackElementDAO(
    val time : Long,
    val latitude : Float?,
    val longitude : Float?,
    val baro_altitude : Float?,
    val true_track : Float?,
    val on_ground : Boolean
) {

    override fun toString(): String {
        return "FlightTrackElementDAO(time=$time, latitude=$latitude, longitude=$longitude, baro_altitude=$baro_altitude, true_track=$true_track, on_ground=$on_ground)"
    }
}




@Deprecated("Endpoint disabled 7 months ago") object FlightTrackElementFactory {

    fun createArray(data : Array<Array<String>>) : Array<FlightTrackElementDAO> {
        val output = arrayListOf<FlightTrackElementDAO>()
        for (d in data) {
            val element = create(d)
            output.add(element)
        }
        return output.toArray(arrayOfNulls<FlightTrackElementDAO>(output.size))
    }

    private fun create(data : Array<String>) : FlightTrackElementDAO {
        return FlightTrackElementDAO(
            data[0].toLong(),
            if (data[1] == null) null else data[1].toFloat(),
            if (data[2] == null) null else data[2].toFloat(),
            if (data[3] == null) null else data[3].toFloat(),
            if (data[4] == null) null else data[4].toFloat(),
            data[5].toBoolean()
        )
    }

}