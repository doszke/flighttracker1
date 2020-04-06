package e.ib.flighttracker1.task.model

import java.io.Serializable

data class CityDAO(
    val GMT : Float,
    val cityId : Long,
    val codeIataCity : String,
    val codeIso2Country : String,
    val geonameId : Long,
    val latitudeCity : Float,
    val longitudeCity : Float,
    val nameCity : String,
    val timezone : String
) : Serializable {

    companion object {
        const val serialVersionUID = 1L
        val EMPTY = CityDAO(0f, 0L, "", "", 0L, 0f, 0f, "", "")
    }

    override fun toString(): String {
        return "CityDAO(GMT=$GMT, cityId=$cityId, codeIataCity='$codeIataCity', codeIso2Country='$codeIso2Country', geonameId=$geonameId, latitudeCity=$latitudeCity, longitudeCity=$longitudeCity, nameCity='$nameCity', timezone='$timezone')"
    }
}