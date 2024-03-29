package com.akshay.spotconnect.location

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class DefaultLocationTracker(
    private val application: Application,
    private val locationClient: FusedLocationProviderClient

):LocationClient {

        override suspend fun getLocation(): Location? {
            //check fine location permission
            val hasAccessFineLocationPermission = ContextCompat.checkSelfPermission(
                application, Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED

            //check coarse location permission
            val hasAccessCoarseLocationPermission = ContextCompat.checkSelfPermission(
                application, Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED

            val locationManager = application.getSystemService(Context.LOCATION_SERVICE) as LocationManager

            val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                    locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

            if (!hasAccessCoarseLocationPermission||!hasAccessFineLocationPermission||!isGpsEnabled)return null


            return suspendCancellableCoroutine { cont->
                val loc = locationClient.getCurrentLocation(
                    Priority.PRIORITY_HIGH_ACCURACY,
                    CancellationTokenSource().token
                )
                loc.apply {
                    if (isComplete){
                        if (isSuccessful)cont.resume(result)
                        else cont.resume(null)
                        return@suspendCancellableCoroutine
                    }
                    addOnSuccessListener {location->
                        cont.resume(location)
                    }
                    addOnFailureListener{
                        cont.resume(null)
                    }
                    addOnCanceledListener {
                        cont.cancel()
                    }
                }
            }
        }
    }
