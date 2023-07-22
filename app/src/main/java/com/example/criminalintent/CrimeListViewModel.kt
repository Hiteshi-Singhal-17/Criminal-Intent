/**
 * CrimeListViewModel storing the list of crime objects.
 */
package com.example.criminalintent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.criminalintent.database.CrimeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private const val TAG = "CrimeListViewModel"
// Define the ViewModel
class CrimeListViewModel : ViewModel() {

    // Get the singleton instance of the CrimeRepository.
    private val crimeRepository = CrimeRepository.get()

    // Define a private MutableStateFlow to hold the list of crimes. Initialize it with an empty list.
    private val _crimes: MutableStateFlow<List<Crime>> = MutableStateFlow(emptyList())

    // Expose an immutable version of _crimes StateFlow to the outside (public API).
    // This will allow observers to collect values but not modify them.
    val crimes: StateFlow<List<Crime>>
        get() = _crimes.asStateFlow()

    // Initialize the ViewModel.
    init {
        // Launch a coroutine in the ViewModelScope. The ViewModelScope ensures that
        // if the ViewModel is cleared, any running coroutine will also be cancelled.
        viewModelScope.launch {
            // Collect the values from the crimeRepository's Flow.
            // For each collected list of crimes, update the _crimes MutableStateFlow.
            crimeRepository.getCrimes().collect {
                _crimes.value = it
            }
        }
    }
}
