package e.ib.flighttracker1.model

import java.util.*

data class StateDAO(
    val time : Long,
    val states : Array<Array<String>>? //response zwraca array róznych obiektów
) {

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