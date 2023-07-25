package com.example.criminalintent.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import com.example.criminalintent.Crime
import kotlinx.coroutines.flow.Flow
import java.util.UUID

/*
Dao is an interface that contains functions for each database operation to be performed.
@Query -> to pull information out of the database.
Annotation generates the implementation of classes at compile time/
 */
@Dao
interface CrimeDao {
    // Returns a list of all crimes in the database
    @Query("Select * from Crime")
    fun getCrimes(): Flow<List<Crime>>

    // Return a single crime matching a given UUID
    @Query("Select * from Crime where id=(:id)")
    suspend fun getCrime(id: UUID): Crime

    // To update the existing crime in database
    @Update
    suspend fun updateCrime(crime: Crime)
}