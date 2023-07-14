package com.example.criminalintent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.example.criminalintent.databinding.FragmentCrimeDetailBinding
import java.util.Date
import java.util.UUID

/**
 * CrimeDetailFragment is responsible for displaying the details of a specific crime.
 */
class CrimeDetailFragment : Fragment() {
    lateinit var crime: Crime
    private var _binding: FragmentCrimeDetailBinding? = null
    // Null out references to views in onDestroyView()
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible ?"
        }
    /**
     * Initializes the crime object.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        crime = Crime(
            id = UUID.randomUUID(),
            title = "",
            date = Date(),
            isSolved = false
        )
    }

    /**
     * Inflates the fragment's layout which includes all the necessary views
     * to display the crime details.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Android system manages time to add fragment's view to container.
        // Activity provides the place. So passed false, to let android system manage it.
        _binding = FragmentCrimeDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * Once the view is created, this function wires up the views
     * with data provided by the user.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            // Setting listeners to crime title box
            crimeTitle.doOnTextChanged { text, _, _, _ ->
                crime = crime.copy(title = text.toString())
            }

            // Setting text to date button
            crimeDate.apply {
                text = crime.date.toString()
                isEnabled = false
            }

            // Setting listener to crime solved checkbox
            crimeSolved.setOnCheckedChangeListener { _, isChecked ->
                crime = crime.copy(isSolved = isChecked)
            }
        }
    }

    /**
     * Destroy the view hierarchy and set null to binding object.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}