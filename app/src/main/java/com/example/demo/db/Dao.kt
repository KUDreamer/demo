package com.example.demo.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface TripDao {
    @Insert
    suspend fun InsertTrip(trip: Trip)

    @Update
    suspend fun UpdateTrip(trip: Trip)

    @Delete
    suspend fun DeleteTrip(trip: Trip)

    @Transaction
    @Query("SELECT * FROM Trips")
    fun getAllTrips(): Flow<List<Trip>>

    @Query("SELECT * FROM Trips WHERE id = :tripId")
    suspend fun getTripById(tripId: Int): Trip?

    @Query("SELECT id FROM Trips WHERE startDate <= :date AND endDate >= :date LIMIT 1")
    suspend fun getTripIdByDate(date: LocalDate): Int?
}

@Dao
interface DayDao {
    @Insert
    suspend fun InsertDay(day: Day)

    @Update
    suspend fun UpdateDay(day: Day)

    @Delete
    suspend fun DeleteDay(day: Day)

    @Query("SELECT * FROM Days WHERE date = :date LIMIT 1")
    suspend fun getDate(date: LocalDate): Day?
}

@Dao
interface PlaceDao {
    @Insert
    suspend fun InsertPlace(place: Place)

    @Update
    suspend fun UpdatePlace(place: Place)

    @Delete
    suspend fun DeletePlace(place: Place)

//    @Transaction
//    @Query("SELECT * FROM Places WHERE dayId = :dayId")
//    suspend fun getPlacesByDayId(dayId: Int): List<Place>
}