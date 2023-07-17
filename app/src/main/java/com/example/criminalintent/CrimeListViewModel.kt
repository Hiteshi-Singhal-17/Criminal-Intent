/**
 * CrimeListViewModel storing the list of crime objects.
 */
package com.example.criminalintent

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Date
import java.util.UUID

private const val TAG = "CrimeListViewModel"
class CrimeListViewModel : ViewModel() {
    // crimes list holding 100 Crime objects.
    val crimes = mutableListOf<Crime>()

    init {
        Log.d(TAG,"init starting")
        // Launching coroutine inside view model scope.
        viewModelScope.launch {
            Log.d(TAG,"coroutine launched")
            // Suspending function returning list of crimes.
            crimes += loadCrimes()
            Log.d(TAG,"Loading crimes finished.")
        }
    }

     suspend fun loadCrimes() : List<Crime>{
        // Doesn't block the thread, only delays the coroutine
        // It is a suspend function.
        val result = mutableListOf<Crime>()
        delay(5000)
        for (i in 0 until 100) {
            val crime = Crime(
                id = UUID.randomUUID(),
                title = "Crime #$i",
                date = Date(),
                isSolved = i % 2 == 0
            )
            result += crime
        }
        return  result
    }
}