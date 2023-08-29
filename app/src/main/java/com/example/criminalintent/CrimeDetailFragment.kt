package com.example.criminalintent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.criminalintent.databinding.FragmentCrimeDetailBinding
import kotlinx.coroutines.launch

/**
 * CrimeDetailFragment is responsible for displaying the details of a specific crime.
 */
class CrimeDetailFragment : Fragment() {
    private val args: CrimeDetailFragmentArgs by navArgs()
    private var _binding: FragmentCrimeDetailBinding? = null

    // Creating the instance of CrimeDetailViewModel
    private val crimeDetailViewModel: CrimeDetailViewModel by viewModels {
        // Returns an instance of CrimeDetailViewModelFactory with a specific crimeId as the argument
        // Custom factory is used because viewModel requires parameter.
        CrimeDetailViewModel.CrimeDetailViewModelFactory(args.crimeId)
    }

    // Null out references to views in onDestroyView()
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible ?"
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
                crimeDetailViewModel.updateCrime { oldCrime ->
                    oldCrime.copy(title = text.toString())
                }
            }

            // Setting listener to crime solved checkbox
            crimeSolved.setOnCheckedChangeListener { _, isChecked ->
                crimeDetailViewModel.updateCrime { oldCrime ->
                    oldCrime.copy(isSolved = isChecked)
                }
            }
        }
        // Coroutine is launched in the lifecycle scope of the viewLifeCycleOwner
        // coroutine will be cancelled when the viewLifeCycleOwner is destroyed.
        viewLifecycleOwner.lifecycleScope.launch {
            // The block of code will run and re-run everytime the lifecycle of
            // viewLifeCycleOwner reaches the 'Started' state
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                // The code is collecting values from the crime StateFlow in the crimeDetailViewModel.
                // Each time the crime object changes, the code block inside
                // 'collect {}' will run
                crimeDetailViewModel.crime.collect { crime ->
                    // For each collected crime, if crime is not null,
                    // the updateUi(it) function is called with crime as the parameter.
                    crime?.let { updateUi(it) }
                }
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

    private fun updateUi(crime: Crime) {
        binding.apply {
            // To prevent placing the cursor at the beginning of the text
            // after every keystroke. The below statement will only be executed
            // if there is any change from the source i.e database not from the user.
            if (crimeTitle.text.toString() != crime.title)
                crimeTitle.setText(crime.title)

            crimeDate.text = crime.date.toString()
            // Click listener on crime date to navigate
            // to DatePickerDialogFragment.
            crimeDate.setOnClickListener {
                findNavController().navigate(
                    CrimeDetailFragmentDirections.selectDate()
                )
            }
            crimeSolved.isChecked = crime.isSolved
        }
    }
}