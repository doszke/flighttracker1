package e.ib.flighttracker1.model

import java.util.*

data class StateDAO(
    val time : Long,
    val states : Array<Array<String>>? //response zwraca array arrayów róznych obiektów
) {

    /*DAO state vector, inicjalizowane w dość paskudny sposób, tak wygląda response dla tego obiektu:
{"time":1581668849,"states":[["3004c7","SIO512  ","Italy",1581668653,1581668675,13.323,43.6361,20604.48,false,157.41,77.74,-8.45,null,2019.3,"1000",false,0]]}
    states może być nullem
    states jest inicjalizowane 2d arrayem, przy próbie pobirania StateVectorDAO, pole jest inicjalizowane 2d arrayem
    inicjalizacja pola w init blocku skutkuje zawsze nullem
    */
    var stateVector : StateVectorDAO? = null
        get() {
            if (!initialized){
                stateVector = StateVectorDAO.parse(states)
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