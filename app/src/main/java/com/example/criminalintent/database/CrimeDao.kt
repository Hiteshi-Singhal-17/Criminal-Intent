package com.example.criminalintent.database

import androidx.room.Dao
import androidx.room.Query
import com.example.criminalintent.Crime
import java.util.UUID

/*
Dao is an interface that contains functions for each database operation to be performed.
@Query -> to pull information out of the database.
 */
@Dao
interface CrimeDao {
    // Returns a list of all crimes in the database
    @Query("Select * from Crime")
    suspend fun getCrimes(): List<Crime>

    // Return a single crime matching a given UUID
    @Query("Select * from Crime where id=(:id)")
    suspend fun getCrime(id: UUID): Crime
}