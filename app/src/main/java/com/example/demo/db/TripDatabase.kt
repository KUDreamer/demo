package com.example.demo.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [Trip::class, Day::class, Place::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class TripDatabase : RoomDatabase() {
    abstract fun tripDao(): TripDao
    abstract fun dayDao(): DayDao
    abstract fun placeDao(): PlaceDao

    companion object {
        private var database: TripDatabase? = null
        fun getDatabase(context: Context) = database ?: Room.databaseBuilder(
            context,
            TripDatabase::class.java, "tripdb"
        ).build().also {
            database = it
        }
    }
}