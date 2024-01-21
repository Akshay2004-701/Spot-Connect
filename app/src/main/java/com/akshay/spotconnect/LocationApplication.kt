package com.akshay.spotconnect

import android.app.Application
import com.akshay.spotconnect.location.DefaultLocationTracker
import com.akshay.spotconnect.location.LocationClient
import com.google.android.gms.location.LocationServices

class LocationApplication: Application() {
    lateinit var locationApplication: LocationClient
    override fun onCreate() {
        super.onCreate()
        locationApplication = DefaultLocationTracker(
            this,
            LocationServices.getFusedLocationProviderClient(this)
        )
    }
}