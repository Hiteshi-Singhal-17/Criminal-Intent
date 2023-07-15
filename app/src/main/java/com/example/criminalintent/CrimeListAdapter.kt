package com.example.criminalintent

import androidx.recyclerview.widget.RecyclerView
import com.example.criminalintent.databinding.ListItemCrimeBinding

/**
 * Crime holder class extending recycler view's
 * holder class and taking binding of type ListItemCrimeBinding
 * as argument and passing root view as argument to base view holder.
 */
class CrimeHolder (
    val binding: ListItemCrimeBinding
): RecyclerView.ViewHolder(binding.root)