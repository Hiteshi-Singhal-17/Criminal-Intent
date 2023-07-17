package com.example.criminalintent

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date
import java.util.UUID

/**
 * An instance of crime will represent a single office crime
 */
// Room will create database table for the entity, here, crime
// Each row will represent a crime
// Columns will be property defined in the crime data class.
// id is primary key to uniquely identify each crime.
@Entity
data class Crime(
    @PrimaryKey val id: UUID,
    val title: String,
    val date: Date,
    val isSolved: Boolean
)