/**
 * CrimeListViewModel storing the list of crime objects.
 */
package com.example.criminalintent

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.criminalintent.database.CrimeRepository
import kotlinx.coroutines.launch

private const val TAG = "CrimeListViewModel"
class CrimeListViewModel : ViewModel() {
    // Getting the instance of crime repository.
    private val crimeRepository = CrimeRepository.get()
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
       // Retrieving data from the database.
       return crimeRepository.getCrimes()
    }
}