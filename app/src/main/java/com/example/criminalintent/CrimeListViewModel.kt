/**
 * CrimeListViewModel storing the list of crime objects.
 */
package com.example.criminalintent

import android.text.format.DateFormat
import androidx.lifecycle.ViewModel
import java.util.Date
import java.util.UUID

class CrimeListViewModel : ViewModel() {
    // crimes list holding 100 Crime objects.
    val crimes = mutableListOf<Crime>()

    init {
        // Retrieving date in human readable format
        val formattedDate = timeStampToDate()
        // Populating the crimes list
        for (i in 0 until 100) {
            val crime = Crime(
                id = UUID.randomUUID(),
                title = "Crime #$i",
                date = formattedDate,
                isSolved = i % 2 == 0
            )
            crimes += crime
        }
    }

    /**
     * Converting date in human readable format from Date object
     * Date object or timestamp to Wednesday, May 11, 2022
     */
    private fun timeStampToDate(): String {
        // Get the current date
        val currentDate = Date()
        // Format of the date i.e. Wednesday, May 11, 2022
        val inFormat: CharSequence = "EEEE, MMMM dd, yyyy"
        // Format the current date into a string
        val formattedDate = DateFormat.format(inFormat, currentDate)
        return formattedDate.toString()
    }
}