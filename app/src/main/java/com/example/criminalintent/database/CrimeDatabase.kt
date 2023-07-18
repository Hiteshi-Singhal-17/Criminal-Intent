package com.example.criminalintent.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.criminalintent.Crime

/*
Database annotation -> This class represents database in application
First parameter  -> list of entities classes
Second parameter -> version of database
TypeConverters -> Tell database to use the functions in class when converting date data type.
 */
@Database(entities = [Crime::class], version = 1)
@TypeConverters(CrimeTypeConverters::class)
abstract class CrimeDatabase : RoomDatabase() {
    abstract fun crimeDao(): CrimeDao
}