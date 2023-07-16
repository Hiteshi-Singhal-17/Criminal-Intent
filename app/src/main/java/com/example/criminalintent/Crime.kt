package com.example.criminalintent

import java.util.UUID

/**
 * An instance of crime will represent a single office crime
 */
data class Crime(
    val id: UUID,
    val title: String,
    val date: String,
    val isSolved: Boolean
)