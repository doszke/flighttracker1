package e.ib.flighttracker1.task.async

import android.os.AsyncTask
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import e.ib.flighttracker1.MainActivity
import e.ib.flighttracker1.task.model.*


private val gson = Gson()

//JSON mapuję GSON'em, gdyż w spring-android, w przeciwieństwie do normalnego springa,
// nie ma ParameterizedTypeReference pozwalającego mapować na typy generyczne

class NearbyAirportTask : AsyncTask<Map<String, String>, String, Array<AirportDAO>>() {
    override fun doInBackground(vararg params: Map<String, String>?): Array<AirportDAO> {
        val uri = AviationEdgeUriFactory.nearbyURI(params[0])
        val result = RestTemplateProvider.provide().getForObject(uri, String::class.java)
        return try {
            gson.fromJson(result, Array<AirportDAO>::class.java)
        } catch (ex : JsonSyntaxException) {
            arrayOf() //gdy nie ma lotów, zwraca FlightScheduleDAO z samymi nullami
        }
    }
}


class TimetableTask : AsyncTask<Map<String, String>, String, Array<FlightScheduleDAO>>() {
    override fun doInBackground(vararg params: Map<String, String>?): Array<FlightScheduleDAO> {
        val uri = AviationEdgeUriFactory.timetablesURI(params[0])
        val result = RestTemplateProvider.provide().getForObject(uri, String::class.java)
        Log.d("XDDDDDDD", result)
        return try {
            gson.fromJson(result, Array<FlightScheduleDAO>::class.java)
        } catch (ex : JsonSyntaxException) {
            arrayOf() //gdy nie ma lotów, zwraca FlightScheduleDAO z samymi nullami
        }
    }
}

class AutocompleteTask : AsyncTask<Map<String, String>, String, AirportsSearchResultDAO>() {
    override fun doInBackground(vararg params: Map<String, String>?): AirportsSearchResultDAO {
        try {
            val uri = AviationEdgeUriFactory.autocompleteURI(params[0])
            val result = RestTemplateProvider.provide().getForObject(uri, String::class.java)
            return gson.fromJson(result, AirportsSearchResultDAO::class.java)
        } catch (t : Throwable) {
            MainActivity.throwableHandler.handle(t)
        }
        return AirportsSearchResultDAO(arrayOf())
    }
}

class AirportFromIataTask : AsyncTask<Map<String, String>, String, AirportDAO>() {
    override fun doInBackground(vararg params: Map<String, String>?): AirportDAO {
        try {
            val uri = AviationEdgeUriFactory.airportDatabaseURI(params[0])
            val result = RestTemplateProvider.provide().getForObject(uri, String::class.java)
            Log.d("XDDDDDDDDD", result)
            return gson.fromJson(result, Array<AirportDAO>::class.java)[0]
        } catch (t : Throwable) {
            MainActivity.throwableHandler.handle(t)
        }
        return AirportDAO.EMPTY
    }
}

class FlightTask : AsyncTask<Map<String, String>, String, Array<FlightTrackDAO>>() {
    override fun doInBackground(vararg params: Map<String, String>?): Array<FlightTrackDAO> {
        try {
            val uri = AviationEdgeUriFactory.flightURI(params[0])
            val result = RestTemplateProvider.provide().getForObject(uri, String::class.java)
            Log.d("XDDDDDDDDD", result)
            return gson.fromJson(result, Array<FlightTrackDAO>::class.java)
        } catch (t : Throwable) {
            Handler(Looper.getMainLooper()).post { MainActivity.throwableHandler.handle(t) }
        }
        return arrayOf()
    }
}


