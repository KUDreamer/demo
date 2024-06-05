package com.example.demo.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface TripDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTrip(trip: Trip)

    @Update
    suspend fun updateTrip(trip: Trip)

    @Delete
    suspend fun deleteTrip(trip: Trip)

    @Query("SELECT * FROM Trips")
    fun getAllTrips(): Flow<List<Trip>>

    @Query("SELECT * FROM Trips WHERE :date BETWEEN startDate AND endDate")
    fun getTripByDate(date: LocalDate): Trip?
}

@Dao
interface DayDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertDay(day: Day)

    @Update
    suspend fun updateDay(day: Day)

    @Delete
    suspend fun deleteDay(day: Day)

    @Query("SELECT * FROM Days WHERE tripId = :tripId")
    fun getDaysByTripId(tripId: Int): Flow<List<Day>>
}

@Dao
interface PlaceDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPlace(place: Place)

    @Update
    suspend fun updatePlace(place: Place)

    @Delete
    suspend fun deletePlace(place: Place)

    @Query("SELECT * FROM Places WHERE dayId = :dayId")
    fun getPlacesByDayId(dayId: Int): Flow<List<Place>>
}