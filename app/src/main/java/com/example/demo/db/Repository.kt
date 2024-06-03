package com.example.demo.db

import java.time.LocalDate

class Repository(private val db:TripDatabase){
    private val tripDao = db.tripDao()
    private val dayDao = db.dayDao()
    private val placeDao = db.placeDao()

    suspend fun insertTrip(trip: Trip){
        tripDao.insertTrip(trip)
    }
    suspend fun updateTrip(trip: Trip){
        tripDao.updateTrip(trip)
    }
    suspend fun deleteTrip(trip: Trip){
        tripDao.deleteTrip(trip)
    }
    suspend fun insertDay(day: Day){
        dayDao.insertDay(day)
    }
    suspend fun updateDay(day: Day){
        dayDao.updateDay(day)
    }
    suspend fun deleteDay(day: Day){
        dayDao.deleteDay(day)
    }
    suspend fun insertPlace(place: Place){
        placeDao.insertPlace(place)
    }
    suspend fun updatePlace(place: Place){
        placeDao.updatePlace(place)
    }
    suspend fun deletePlace(place: Place){
        placeDao.deletePlace(place)
    }

    fun getAllTrips() = tripDao.getAllTrips()

    suspend fun getDate(date: LocalDate): Day? {
        return dayDao.getDate(date)
    }

    fun getTripByDate(date: LocalDate) = tripDao.getTripByDate(date)

    fun getDaysByTripId(tripId: Int) = dayDao.getDaysByTripId(tripId)

    fun getPlacesByDayId(dayId: Int) = placeDao.getPlacesByDayId(dayId)
}