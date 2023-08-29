package com.example.criminalintent

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.navArgs
import java.util.Calendar

/*
If datePickerDialog is wrapped in DialogFragment,
then the dialog will be created and put back on screen
after rotation.

DialogFragment handles the lifecycle of Dialog and all
the necessary interaction with android system and rest of the app

Dialog focus solely on rendering UI and handling user input.
Note: Separation of concern and modularity.
 */
class DatePickerFragment : DialogFragment() {
    private val args: DatePickerFragmentArgs by navArgs()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        // To fetch day, month and year from date timestamp
        calendar.time = args.crimeDate
        val initialYear = calendar[Calendar.YEAR]
        val initialMonth = calendar[Calendar.MONTH]
        val initialDay = calendar[Calendar.DAY_OF_MONTH]

        // Creating an instance of DatePickerDialog.
        return DatePickerDialog(
            requireContext(), // to access necessary resources like theme and strings
            null,      // DateListener
            initialYear,
            initialMonth,
            initialDay
        )

    }
}