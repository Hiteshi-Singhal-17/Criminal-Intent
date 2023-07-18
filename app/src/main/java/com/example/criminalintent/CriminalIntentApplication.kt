package com.example.criminalintent

import android.app.Application
import com.example.criminalintent.database.CrimeRepository

class CriminalIntentApplication: Application()
{
    /*
    Application.onCreate() is invoked when app launches and
    destroyed when app is destroyed.
    Launched by system when application is first loaded into
    memory.
     */
    override fun onCreate() {
        super.onCreate()
        // Application context would stay in memory as long as
        // process is in memory.
        CrimeRepository.initialize(this)
    }
}