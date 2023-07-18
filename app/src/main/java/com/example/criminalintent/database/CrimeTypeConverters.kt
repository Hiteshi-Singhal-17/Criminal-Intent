package com.example.criminalintent.database

import androidx.room.TypeConverter
import java.util.Date

/*
Room doesn't know how to store Date data type by default, able to store primitive types.
Type converter tells Room how to convert a specific type to the format it needs to store in database
In this case, it is Date data type
 */
class CrimeTypeConverters {
    // How to convert date data type to store in database.
    // In this case, date -> long to store in database
    @TypeConverter
    fun fromDate(date: Date): Long = date.time

    // How to convert from the database representation back to the original type.
    // In this case, long -> date to pull from the database.
    @TypeConverter
    fun toDate(millisSinceEpoch: Long): Date = Date(millisSinceEpoch)
}