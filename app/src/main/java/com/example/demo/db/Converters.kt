package com.example.demo.db

import androidx.room.TypeConverter
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object Converters {

    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE

    @TypeConverter
    @JvmStatic
    fun fromLocalDate(date: LocalDate?): String? {
        return date?.format(formatter)
    }

    @TypeConverter
    @JvmStatic
    fun toLocalDate(dateString: String?): LocalDate? {
        return dateString?.let {
            LocalDate.parse(it, formatter)
        }
    }

    @TypeConverter
    @JvmStatic
    fun fromDayOfWeek(dayOfWeek: DayOfWeek?): Int? {
        return dayOfWeek?.value
    }

    @TypeConverter
    @JvmStatic
    fun toDayOfWeek(dayOfWeekInt: Int?): DayOfWeek? {
        return dayOfWeekInt?.let {
            DayOfWeek.of(it)
        }
    }
}