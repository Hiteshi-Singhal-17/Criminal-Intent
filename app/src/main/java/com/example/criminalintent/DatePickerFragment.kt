package com.example.criminalintent

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.navArgs
import java.util.Calendar
import java.util.GregorianCalendar

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
        val dateListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            val resultDate = GregorianCalendar(year, month, dayOfMonth).time
            setFragmentResult(REQUEST_KEY_DATE, bundleOf(BUNDLE_KEY_DATE to resultDate))
        }

        val calendar = Calendar.getInstance()
        // To fetch day, month and year from date timestamp
        calendar.time = args.crimeDate
        val initialYear = calendar[Calendar.YEAR]
        val initialMonth = calendar[Calendar.MONTH]
        val initialDay = calendar[Calendar.DAY_OF_MONTH]

        // Creating an instance of DatePickerDialog.
        return DatePickerDialog(
            requireContext(), // to access necessary resources like theme and strings
            dateListener,
            initialYear,
            initialMonth,
            initialDay
        )
    }

    companion object {
        const val REQUEST_KEY_DATE = "REQUEST_KEY_DATE"
        const val BUNDLE_KEY_DATE = "BUNDLE_KEY_DATE"

    }
}