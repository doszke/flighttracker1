package e.ib.flighttracker1.task.model

//w takiej formie dostaję JSON'y, to w takiej przyjmuję
data class AirportsSearchResultDAO(
    val airportsByCities : Array<AirportDAO>
) {



    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AirportsSearchResultDAO

        if (!airportsByCities.contentEquals(other.airportsByCities)) return false

        return true
    }

    override fun hashCode(): Int {
        return airportsByCities.contentHashCode()
    }

}