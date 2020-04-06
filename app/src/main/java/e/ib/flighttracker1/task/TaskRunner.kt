package e.ib.flighttracker1.task

import android.os.Handler
import e.ib.flighttracker1.MainActivity
import e.ib.flighttracker1.task.async.*
import e.ib.flighttracker1.task.model.*
import android.widget.Toast
import android.os.Looper



object TaskRunner {

    private val handler = MainActivity.throwableHandler


    fun airportsByCity(params : Map<String, String>) : AirportsSearchResultDAO? {
        return try {
            val task = AutocompleteTask()
            task.execute(params).get()
        } catch (t : Throwable) {
            handler.handle(t)
            null
        }
    }

    fun nearbyAirports(params : Map<String, String>) : Array<AirportDAO>? {
        return try {
            val task = NearbyAirportTask()
            val otp = task.execute(params).get()
            if (otp.isEmpty()) null else otp
        } catch (t : Throwable) {
            handler.handle(t)
            null
        }
    }

    fun timetableForAirport(params : Map<String, String>) : Array<FlightScheduleDAO>? {
        return try {
            val task = TimetableTask()
            val otp = task.execute(params).get()
            if (otp.isEmpty()) null else otp
        } catch (t : Throwable) {
            handler.handle(t)
            null
        }
    }

    fun airportFromIata(params : Map<String, String>) : AirportDAO? {
        return try {
            val task = AirportFromIataTask()
            task.execute(params).get()
        } catch (t : Throwable) {
            handler.handle(t)
            null
        }
    }

    fun flightsInRange(params : Map<String, String>) : Array<FlightTrackDAO>? {
        return try {
            val task = FlightTask()
            task.execute(params).get()
        } catch (t : Throwable) {
            Handler(Looper.getMainLooper()).post(Runnable { handler.handle(t) })
            null
        }
    }


}