package e.ib.flighttracker1

import android.os.AsyncTask
import android.util.Log
import com.google.gson.Gson
import e.ib.flighttracker1.model.FlightDAO
import e.ib.flighttracker1.model.OpenSkyRestAPIUrl
import e.ib.flighttracker1.model.RestTemplateProvider
import e.ib.flighttracker1.model.StateDAO


private val gson = Gson()


class FlightsAllTask : AsyncTask<String, String, Array<FlightDAO>>() {
    override fun doInBackground(vararg params: String?): Array<FlightDAO> {
        val builder = OpenSkyRestAPIUrl.flightsAllUriComponentsBuilder()
        builder.queryParam("begin", params[0])
        builder.queryParam("end", params[1])
        val uri = builder.build()
        val result = RestTemplateProvider.provide().getForObject(uri.toUriString(), String::class.java)
        val otp = gson.fromJson(result, Array<FlightDAO>::class.java)
        return otp
    }
}


class StatesAllTask : AsyncTask<String, String, StateDAO>() {
    override fun doInBackground(vararg params: String?): StateDAO {
        val builder = OpenSkyRestAPIUrl.statesAllUriComponentsBuilder()
        builder.queryParam("time", params[0])
        builder.queryParam("icao24", params[1])
        val uri = builder.build()
        val result = RestTemplateProvider.provide().getForObject(uri.toUriString(), String::class.java)
        Log.d("resulttttt", result)
        val otp = gson.fromJson(result, StateDAO::class.java)
        return otp
    }
}

class FlightsAircraftTask : AsyncTask<String, String, StateDAO>() {
    override fun doInBackground(vararg params: String?): StateDAO {
        var builder = OpenSkyRestAPIUrl.flightsAircraftsUriComponentsBuilder()
        builder.queryParam("time", params[0])
        builder.queryParam("icao24", params[1])
        val uri = builder.build()
        val result = RestTemplateProvider.provide().getForObject(uri.toUriString(), String::class.java)
        Log.d("resulttttt", result)
        val otp = gson.fromJson(result, StateDAO::class.java)
        return otp
    }

}