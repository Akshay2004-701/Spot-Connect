package com.akshay.spotconnect.location

import android.location.Location

interface LocationClient {
    suspend fun getLocation(): Location?
}