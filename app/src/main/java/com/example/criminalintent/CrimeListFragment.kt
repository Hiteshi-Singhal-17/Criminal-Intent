package com.example.criminalintent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.criminalintent.databinding.FragmentCrimeListBinding
import kotlinx.coroutines.launch


class CrimeListFragment : Fragment() {
    // Associating fragment with the view model
    private val crimeListViewModel: CrimeListViewModel by viewModels()
    private var _binding: FragmentCrimeListBinding? = null

    // Null out references to views in onDestroyView()
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
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
        _binding = FragmentCrimeListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /* Setting up linear layout manager for recycler view
         It's generally recommended to set up the layout manager
         and adapter of a RecyclerView in the onViewCreated() method.
         This sequence ensures that the View hierarchy is fully set up before trying to access
         and modify it
         */
        binding.crimeRecyclerView.layoutManager = LinearLayoutManager(context)

        viewLifecycleOwner.lifecycleScope.launch {
            // repeatOnLifecycle -> suspending function requires coroutine scope to be launched,
            // won't cancel coroutines, cancels the job when goes to the Destroyed state.
            // Coroutine will be cancelled by coroutine scope only, in this case
            // viewLifecycleOwner; tied to the lifecycle of fragment's view.
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                // Start collecting from the crimes StateFlow exposed by the ViewModel.
                crimeListViewModel.crimes.collect { crimes ->
                    // For each new list of crimes emitted by the StateFlow:

                    // Create a new CrimeListAdapter with the latest list of crimes,
                    // and set it as the adapter for the RecyclerView. This will cause
                    // the RecyclerView to update and display the latest list of crimes.
                    binding.crimeRecyclerView.adapter = CrimeListAdapter(crimes) {
                        findNavController().navigate(
                            R.id.show_crime_detail
                        )
                    }
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
}