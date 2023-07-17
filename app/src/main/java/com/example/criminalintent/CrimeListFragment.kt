package com.example.criminalintent

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.criminalintent.databinding.FragmentCrimeListBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

private const val TAG = "CrimeListFragment"

class CrimeListFragment : Fragment() {
    // Associating fragment with the view model
    private val crimeListViewModel: CrimeListViewModel by viewModels()
    private var _binding: FragmentCrimeListBinding? = null
    private var job: Job? = null

    // Null out references to views in onDestroyView()
    private val binding
        get() = checkNotNull(_binding){
            "Cannot access binding because it is null. Is the view visible?"
        }

    /**
     * Logs the number of crimes.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "Total crimes: ${crimeListViewModel.crimes.size}")
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
        _binding = FragmentCrimeListBinding.inflate(inflater,container,false)
        // Setting up linear layout manager for recycler view
        binding.crimeRecyclerView.layoutManager = LinearLayoutManager(context)
        // Retrieving the list of crimes from the ViewModel
        val crimes = crimeListViewModel.crimes
        // Set the adapter for the recycler view
        binding.crimeRecyclerView.adapter = CrimeListAdapter(crimes)

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        job = viewLifecycleOwner.lifecycleScope.launch {
            val crimes = crimeListViewModel.loadCrimes()
            binding.crimeRecyclerView.adapter = CrimeListAdapter(crimes)
        }
    }

    override fun onStop() {
        super.onStop()
        job?.cancel()
    }
    /**
     * Destroy the view hierarchy and set null to binding object.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}