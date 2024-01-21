package com.akshay.spotconnect.location

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.akshay.spotconnect.LocationApplication
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
class LocationViewModel(
    private val locationClient: LocationClient
):ViewModel() {

    private var _lat = MutableStateFlow(0.0)
    val lat = _lat.asStateFlow()

    private var _lon = MutableStateFlow(0.0)
    val lon = _lon.asStateFlow()

    private var _address = MutableStateFlow("")
    val address = _address.asStateFlow()



    init {
        loadWeatherData()
    }
    private fun loadWeatherData(){
        viewModelScope.launch {
            locationClient.getLocation()?.let {location->
                _lat.value = location.latitude
                _lon.value = location.longitude
            }
        }
    }



    fun getAddress(lat:Double,lon:Double,context:Context) {
        val geocoder = Geocoder(context, Locale.getDefault())
        val addresses: MutableList<Address>? =
            geocoder.getFromLocation(lat, lon,1)
        return if(addresses?.isNotEmpty()==true) {
           _address.value =  addresses[0].getAddressLine(0)
        }else{
            _address.value = "Mysuru"
        }
    }




    companion object{
        val factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as LocationApplication)
                LocationViewModel(
                    application.locationApplication
                )
            }
        }
    }

}
