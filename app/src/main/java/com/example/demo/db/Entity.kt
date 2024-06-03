package com.example.demo.db

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.time.DayOfWeek
import java.time.LocalDate

@Entity(tableName = "Trips")
data class Trip(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val startDate: LocalDate,
    val endDate: LocalDate
)

@Entity(
    tableName = "Days",
    foreignKeys = [ForeignKey(
        entity = Trip::class,
        parentColumns = ["id"],
        childColumns = ["tripId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Day(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val tripId: Int,
    val date: LocalDate,
    val dayOfWeek: DayOfWeek
)

@Entity(
    tableName = "Places",
    foreignKeys = [ForeignKey(
        entity = Day::class,
        parentColumns = ["id"],
        childColumns = ["dayId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Place(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val dayId: Int,
    val name: String,
    val time: String? = null
)