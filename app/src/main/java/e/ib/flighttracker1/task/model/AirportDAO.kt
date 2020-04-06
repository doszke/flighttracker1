package e.ib.flighttracker1.task.model

import java.io.Serializable


class AirportDAO(
    val GMT : Float,
    val codeIataAirport : String,
    val codeIataCity : String,
    val codeIcaoAirport : String,
    val codeIso2Country : String,
    val distance : Float,
    val geonameId : Int,
    val latitudeAirport : Float,
    val longitudeAirport : Float,
    val nameAirport : String,
    val nameCountry : String,
    val phone : String,
    val timezone : String
) : Serializable {
    companion object {
        const val serialVersionUID = 1L
        val EMPTY  = AirportDAO(
            0f,
            "",
            "",
            "",
            "",
            0f,
            0,
            0f,
            0f,
            "",
            "",
            "",
            ""
        )
    }

    override fun toString(): String {
        return "AirportDAO(GMT=$GMT, codeIataAirport='$codeIataAirport', codeIataCity='$codeIataCity', codeIcaoAirport='$codeIcaoAirport', codeIso2Country='$codeIso2Country', distance=$distance, geonameId=$geonameId, latitudeAirport=$latitudeAirport, longitudeAirport=$longitudeAirport, nameAirport='$nameAirport', nameCountry='$nameCountry', phone='$phone', timezone='$timezone')"
    }
}