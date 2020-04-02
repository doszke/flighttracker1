package e.ib.flighttracker1.model

import android.util.Log
import java.util.*

data class StateDAO(
    val time : Long,
    val states : Array<Array<String>>? //response zwraca array arrayów róznych obiektów
) {

    /*DAO state vector, inicjalizowane w dość paskudny sposób, tak wygląda response dla tego obiektu:
{"time":1581668849,"states":[["3004c7","SIO512  ","Italy",1581668653,1581668675,13.323,43.6361,20604.48,false,157.41,77.74,-8.45,null,2019.3,"1000",false,0]]}
    states może być nullem
    przy próbie pobirania StatesAllStateVectorDAO, pole jest inicjalizowane 2d arrayem
    inicjalizacja pola w init blocku skutkuje zawsze nullem
    */
    var statesAllStateVector : StatesAllStateVectorDAO? = null
        get() {
            if (!initialized && states != null){
                statesAllStateVector = StatesAllStateVectorFactory.create(states.get(0))
                initialized = true
            }
            return field
        }
    private var initialized = false





    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as StateDAO

        if (time != other.time) return false
        if (states != null) {
            if (other.states == null) return false
            if (!states.contentEquals(other.states)) return false
        } else if (other.states != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = time.hashCode()
        result = 31 * result + (states?.contentHashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "StateDAO(time=$time, states=${Arrays.toString(states)}, initialized=$initialized)"
    }
}


data class StatesAllStateVectorDAO(
    val icao24 : String,
    val callsign : String?,
    val origin_country : String,
    val time_position : Long?,
    val last_contact : Long,
    val longitude : Float?,
    val lattitude : Float?,
    val baro_altitude : Float?,
    val on_ground : Boolean,
    val velocity : Float?,
    val true_track : Float?,
    val verical_rate : Float?, // +leci do góry
    val sensors : Array<Int>?,
    val geo_altitude : Float?,
    val squawk : String?,
    val spi : Boolean,
    val position_source : Int
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as StatesAllStateVectorDAO

        if (icao24 != other.icao24) return false
        if (callsign != other.callsign) return false
        if (origin_country != other.origin_country) return false
        if (time_position != other.time_position) return false
        if (last_contact != other.last_contact) return false
        if (longitude != other.longitude) return false
        if (lattitude != other.lattitude) return false
        if (baro_altitude != other.baro_altitude) return false
        if (on_ground != other.on_ground) return false
        if (velocity != other.velocity) return false
        if (true_track != other.true_track) return false
        if (verical_rate != other.verical_rate) return false
        if (sensors != null) {
            if (other.sensors == null) return false
            if (!sensors.contentEquals(other.sensors)) return false
        } else if (other.sensors != null) return false
        if (geo_altitude != other.geo_altitude) return false
        if (squawk != other.squawk) return false
        if (spi != other.spi) return false
        if (position_source != other.position_source) return false

        return true
    }

    override fun hashCode(): Int {
        var result = icao24.hashCode()
        result = 31 * result + (callsign?.hashCode() ?: 0)
        result = 31 * result + origin_country.hashCode()
        result = 31 * result + (time_position?.hashCode() ?: 0)
        result = 31 * result + last_contact.hashCode()
        result = 31 * result + (longitude?.hashCode() ?: 0)
        result = 31 * result + (lattitude?.hashCode() ?: 0)
        result = 31 * result + (baro_altitude?.hashCode() ?: 0)
        result = 31 * result + on_ground.hashCode()
        result = 31 * result + (velocity?.hashCode() ?: 0)
        result = 31 * result + (true_track?.hashCode() ?: 0)
        result = 31 * result + (verical_rate?.hashCode() ?: 0)
        result = 31 * result + (sensors?.contentHashCode() ?: 0)
        result = 31 * result + (geo_altitude?.hashCode() ?: 0)
        result = 31 * result + (squawk?.hashCode() ?: 0)
        result = 31 * result + spi.hashCode()
        result = 31 * result + position_source
        return result
    }

    override fun toString(): String {
        return "StatesAllStateVectorDAO(icao24='$icao24', callsign=$callsign, origin_country='$origin_country', time_position=$time_position, last_contact=$last_contact, longitude=$longitude, lattitude=$lattitude, baro_altitude=$baro_altitude, on_ground=$on_ground, velocity=$velocity, true_track=$true_track, verical_rate=$verical_rate, sensors=${Arrays.toString(
            sensors
        )}, geo_altitude=$geo_altitude, squawk=$squawk, spi=$spi, position_source=$position_source)"
    }
}

object StatesAllStateVectorFactory {

    fun create(_i : Array<String>) : StatesAllStateVectorDAO {
        return StatesAllStateVectorDAO(
            _i[0],
            if (_i[1] == null) null else _i[1],
            _i[2],
            if (_i[3] == null) null else _i[3].toLong(),
            _i[4].toLong(),
            if (_i[5] == null) null else _i[5].toFloat(),
            if (_i[6] == null) null else _i[6].toFloat(),
            if (_i[7] == null) null else _i[7].toFloat(),
            _i[8].toBoolean(),
            if (_i[9] == null) null else _i[9].toFloat(), //pokazuje, że never happens, a bez tego wywala błąd :)
            if (_i[10] == null) null else _i[10].toFloat(),
            if (_i[11] == null) null else _i[11].toFloat(),
            null,
            if (_i[13] == null) null else _i[13].toFloat(),
            _i[14],
            _i[15].toBoolean(),
            _i[16].toInt()
        )
    }

}