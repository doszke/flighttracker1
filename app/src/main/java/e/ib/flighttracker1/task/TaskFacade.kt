package e.ib.flighttracker1.task

import e.ib.flighttracker1.task.async.AutocompleteTask
import e.ib.flighttracker1.MainActivity
import e.ib.flighttracker1.task.async.AirportFromIataTask
import e.ib.flighttracker1.task.async.NearbyAirportTask
import e.ib.flighttracker1.task.model.AirportDAO
import e.ib.flighttracker1.task.model.AirportsSearchResultDAO
import e.ib.flighttracker1.task.model.FlightScheduleDAO
import e.ib.flighttracker1.task.async.TimetableTask

object TaskFacade {

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
            task.execute(params).get()
        } catch (t : Throwable) {
            handler.handle(t)
            null
        }
    }

    fun timetableForAirport(params : Map<String, String>) : Array<FlightScheduleDAO>? {
        return try {
            val task = TimetableTask()
            task.execute(params).get()
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


}