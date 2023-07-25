package com.example.criminalintent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.criminalintent.database.CrimeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

class CrimeDetailViewModel(crimeId: UUID) : ViewModel(){
    // Retrieving the crime repository
    private val crimeRepository = CrimeRepository.get()
    // Initializing mutable stateflow crime object
    private val _crime: MutableStateFlow<Crime?> = MutableStateFlow(null)
    // Initializing immutable crime object by mutable crime object
    val crime: StateFlow<Crime?> = _crime.asStateFlow()


    // Querying database to fetch the crime data on the basis of crimeID
    init {
        // Coroutines is used to not block the main thread to perform time-consuming operations
        viewModelScope.launch {
            // Initializing crime with data fetched from the database.
            _crime.value = crimeRepository.getCrime(crimeId)
        }
    }

    fun updateCrime(onUpdate: (Crime) -> Crime) {
        _crime.update { oldCrime ->
            oldCrime?.let { onUpdate(it) }
        }
    }

    // To save changes to the database
    override fun onCleared() {
        super.onCleared()
        crime.value?.let { crimeRepository.updateCrime(it) }
    }

    /*
    FYI, the ViewModel instances are created by a factory class.
    By default, ViewModel uses a no-argument constructor.
    This means if your ViewModel needs a parameter in its constructor
    (in this case, CrimeDetailViewModel requires a crimeId),
     the default factory can't be used.

    That's where ViewModelProvider.Factory comes in.
    You can create your own factory, and override the create method to construct a ViewModel
    with the specific parameters that you need.
    CrimeDetailViewModelFactory in your code does exactly that.
     */

    /**
     * CrimeDetailViewModelFactory accepts a crimeId as a parameter, and provides instances of
     * CrimeDetailViewModel with that crimeId.
     */
    class CrimeDetailViewModelFactory (
        private val crimeId: UUID
        ): ViewModelProvider.Factory {
            override fun <T: ViewModel> create (modelClass: Class<T>): T {
                return CrimeDetailViewModel(crimeId) as T
            }
        }
}