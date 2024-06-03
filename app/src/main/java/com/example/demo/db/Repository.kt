package com.example.demo.db

import java.time.LocalDate

class Repository(private val db:TripDatabase){
    val tripDao = db.tripDao()
    val dayDao = db.dayDao()
    val placeDao = db.placeDao()

    suspend fun InsertTrip(trip: Trip){
        tripDao.InsertTrip(trip)
    }
    suspend fun UpdateTrip(trip: Trip){
        tripDao.UpdateTrip(trip)
    }
    suspend fun DeleteTrip(trip: Trip){
        tripDao.DeleteTrip(trip)
    }
    suspend fun InsertDay(day: Day){
        dayDao.InsertDay(day)
    }
    suspend fun UpdateDay(day: Day){
        dayDao.UpdateDay(day)
    }
    suspend fun DeleteDay(day: Day){
        dayDao.DeleteDay(day)
    }
    suspend fun InsertPlace(place: Place){
        placeDao.InsertPlace(place)
    }
    suspend fun UpdatePlace(place: Place){
        placeDao.UpdatePlace(place)
    }
    suspend fun DeletePlace(place: Place){
        placeDao.DeletePlace(place)
    }

    fun getAllTrips() = tripDao.getAllTrips()

    suspend fun getDate(date: LocalDate): Day? {
        return dayDao.getDate(date)
    }

    suspend fun getTripById(tripId: Int): Trip? {
        return tripDao.getTripById(tripId)
    }

    suspend fun getTripIdByDate(date: LocalDate): Int? {
        return tripDao.getTripIdByDate(date)
    }
}