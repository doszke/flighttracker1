package e.ib.flighttracker1.task.async

import android.os.AsyncTask
import android.util.Log
import com.google.gson.Gson
import e.ib.flighttracker1.task.model.*


private val gson = Gson()

//JSON mapuję GSON'em, gdyż w spring-android, w przeciwieństwie do normalnego springa,
// nie ma ParameterizedTypeReference pozwalającego mapować na typy generyczne

class NearbyAirportTask : AsyncTask<Map<String, String>, String, Array<AirportDAO>>() {
    override fun doInBackground(vararg params: Map<String, String>?): Array<AirportDAO> {
        val uri = AviationEdgeUriFactory.nearbyURI(params[0])
        val result = RestTemplateProvider.provide().getForObject(uri, String::class.java)
        return gson.fromJson(result, Array<AirportDAO>::class.java)
    }
}


class TimetableTask : AsyncTask<Map<String, String>, String, Array<FlightScheduleDAO>>() {
    override fun doInBackground(vararg params: Map<String, String>?): Array<FlightScheduleDAO> {
        val uri = AviationEdgeUriFactory.timetablesURI(params[0])
        val result = RestTemplateProvider.provide().getForObject(uri, String::class.java)
        return gson.fromJson(result, Array<FlightScheduleDAO>::class.java)
    }
}

class AutocompleteTask : AsyncTask<Map<String, String>, String, AirportsSearchResultDAO>() {
    override fun doInBackground(vararg params: Map<String, String>?): AirportsSearchResultDAO {
        val uri = AviationEdgeUriFactory.autocompleteURI(params[0])
        val result = RestTemplateProvider.provide().getForObject(uri, String::class.java)
        return gson.fromJson(result, AirportsSearchResultDAO::class.java)
    }
}

class AirportFromIataTask : AsyncTask<Map<String, String>, String, AirportDAO>() {
    override fun doInBackground(vararg params: Map<String, String>?): AirportDAO {
        val uri = AviationEdgeUriFactory.airportDatabaseURI(params[0])
        val result = RestTemplateProvider.provide().getForObject(uri, String::class.java)
        Log.d("XDDDDDDDDD", result)
        return gson.fromJson(result, Array<AirportDAO>::class.java)[0]
    }
}

