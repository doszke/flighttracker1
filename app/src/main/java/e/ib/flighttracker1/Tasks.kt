package e.ib.flighttracker1

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import e.ib.flighttracker1.model.*


private val gson = Gson()

private var throwable : Throwable? = null

private abstract class MyAsyncTask : AsyncTask<String, String, Any>() {

}

class FlightsAllTask() : AsyncTask<String, String, Any>() {
    override fun doInBackground(vararg params: String?): Any {
        val builder = OpenSkyRestAPIUrl.flightsAllUriComponentsBuilder()
        builder.queryParam("begin", params[0])
        builder.queryParam("end", params[1])
        val uri = builder.build()
        val result: String
        try {
            result = RestTemplateProvider.provide().getForObject(uri.toUriString(), String::class.java)
        } catch (t : Throwable) {
            return t
        }
        val otp = gson.fromJson(result, Array<FlightDAO>::class.java)
        return otp
    }
}


class StatesAllTask(context : Context) : AsyncTask<String, String, Any>() {
    override fun doInBackground(vararg params: String?): Any {
        val builder = OpenSkyRestAPIUrl.statesAllUriComponentsBuilder()
        builder.queryParam("time", params[0])
        builder.queryParam("icao24", params[1])
        val uri = builder.build()
        val result: String
        try {
            result = RestTemplateProvider.provide().getForObject(uri.toUriString(), String::class.java)
        } catch (t : Throwable) {
            return t
        }
        val otp = gson.fromJson(result, StateDAO::class.java)
        return otp
    }
}

class FlightsAircraftTask(context : Context) : AsyncTask<String, String, Any>() {
    override fun doInBackground(vararg params: String?): Any {
        val builder = OpenSkyRestAPIUrl.flightsAircraftUriComponentsBuilder()
        builder.queryParam("begin", params[0])
        builder.queryParam("end", params[1])
        builder.queryParam("icao24", params[2])
        val uri = builder.build()
        val result: String
        try {
            result = RestTemplateProvider.provide().getForObject(uri.toUriString(), String::class.java)
        } catch (t : Throwable) {
            return t
        }
        val otp = gson.fromJson(result, Array<FlightDAO>::class.java)
        return otp
    }
}

class FlightsArrivalTask(context : Context) : AsyncTask<String, String, Any>() {
    override fun doInBackground(vararg params: String?): Any {
        val builder = OpenSkyRestAPIUrl.flightsArrivalUriComponentsBuilder()
        builder.queryParam("airport", params[0])
        builder.queryParam("begin", params[1])
        builder.queryParam("end", params[2])
        val uri = builder.build()
        val result: String
        try {
            result = RestTemplateProvider.provide().getForObject(uri.toUriString(), String::class.java)
        } catch (t : Throwable) {
            return t
        }
        val otp = gson.fromJson(result, Array<FlightDAO>::class.java)
        return otp
    }
}

class FlightsDepartureTask(context : Context) : AsyncTask<String, String, Any>() {
    override fun doInBackground(vararg params: String?): Any {
        val builder = OpenSkyRestAPIUrl.flightsDepartureUriComponentsBuilder()
        builder.queryParam("airport", params[0])
        builder.queryParam("begin", params[1])
        builder.queryParam("end", params[2])
        val uri = builder.build()
        val result: String
        try {
            result = RestTemplateProvider.provide().getForObject(uri.toUriString(), String::class.java)
        } catch (t : Throwable) {
            return t
        }
        val otp = gson.fromJson(result, Array<FlightDAO>::class.java)
        return otp
    }
}

@Deprecated("Endpoint disabled 7 months ago") class TracksTask : AsyncTask<String, String, Any>() {
    override fun doInBackground(vararg params: String?): Array<FlightTrackDAO> {
        val builder = OpenSkyRestAPIUrl.trackUriComponentsBuilder()
        builder.queryParam("icao24", params[0])
        builder.queryParam("time", params[1])
        val uri = builder.build()
        val result = RestTemplateProvider.provide().getForObject(uri.toUriString(), String::class.java)
        Log.d("resulttttt", result)
        val otp = gson.fromJson(result, Array<FlightTrackDAO>::class.java)
        return otp
    }
}

