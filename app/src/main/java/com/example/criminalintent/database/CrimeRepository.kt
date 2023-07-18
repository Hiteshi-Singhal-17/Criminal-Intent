package com.example.criminalintent.database

import android.content.Context
import androidx.room.Room
import com.example.criminalintent.Crime
import java.util.UUID

/*
Repository -> encapsulates the logic for accessing the data from a single
source or set of sources. It determines how to fetch and store a particular
set of data, whether locally in a database or from a repository server.
UI code will request all the data from the repository, because UI doesn't care
how the data is actually stored or fetched.

Here, crimeRepository is a singleton, there will only be a single instance of it in app process.
It exists as long as application stays in memory.
 */
private const val DATABASE_NAME = "crime-database"

class CrimeRepository private constructor(context: Context) {
    // Storing the reference of database.
    // Room.databaseBuilder(...) creates the concrete implementation of abstract CrimeDatabase class.
    private val database: CrimeDatabase = Room
        .databaseBuilder(
            context.applicationContext,
            CrimeDatabase::class.java,
            DATABASE_NAME
        )
        // Initialize database with pre-populated data.
        // One time operation, if database already exists on device, won't be overwritten
        .createFromAsset(DATABASE_NAME)
        .build()

    suspend fun getCrimes(): List<Crime> = database.crimeDao().getCrimes()
    suspend fun getCrime(id:UUID) : Crime = database.crimeDao().getCrime(id)


    companion object {
        private var INSTANCE: CrimeRepository? = null
        // Initializing the crimeRepository
        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = CrimeRepository(context)
            }
        }

        // Get the instance of crimeRepository
        fun get(): CrimeRepository {
            return INSTANCE ?: throw IllegalStateException("CrimeRepository must be initialized.")
        }
    }
}