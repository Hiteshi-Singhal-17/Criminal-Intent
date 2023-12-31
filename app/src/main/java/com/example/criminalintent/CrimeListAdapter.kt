package com.example.criminalintent

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.criminalintent.databinding.ListItemCrimeBinding

/**
 * Crime holder class extending recycler view's
 * holder class and taking binding of type ListItemCrimeBinding
 * as argument and passing root view as argument to base view holder.
 */
class CrimeHolder (
    private val binding: ListItemCrimeBinding
): RecyclerView.ViewHolder(binding.root) {
    // Populating crime holder with the crime object data
    fun bind(crime:Crime){
        binding.crimeDate.text = crime.date.toString()
        binding.crimeTitle.text = crime.title
    }
}

class CrimeListAdapter(
    private val crimes: List<Crime>
) : RecyclerView.Adapter<CrimeHolder>() {

    /**
     * Creating the view holder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrimeHolder {
        // Declaring and initializing the inflator
        val inflater = LayoutInflater.from(parent.context)
        // Inflating the item view layout
        val binding = ListItemCrimeBinding.inflate(inflater,parent,false)
        // Returning the crime holder i.e. view holder
        return CrimeHolder(binding)
    }

    override fun getItemCount(): Int {
        // Returning the size of crime list
        return crimes.size
    }

    /**
     * To populate view holder with data
     */
    override fun onBindViewHolder(holder: CrimeHolder, position: Int) {
        // Fetching crime details at the specific position
        val crime = crimes[position]
        // Invoking bind to populate holder with crime object
        holder.bind(crime)
    }
}
