package br.com.brunoccbertolini.easyandfastnotes_noads.data

import androidx.room.TypeConverter
import java.util.*

class DateConverter {

    @TypeConverter
    fun fromLongToTimes(value: Long): Date {
        return Date(value)
    }

    @TypeConverter
    fun fromDateToLong(date: Date):Long{
        return date.time
    }
}