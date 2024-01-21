package com.akshay.spotconnect

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import com.akshay.spotconnect.authentication.AuthenticationActivity
import com.akshay.spotconnect.chat.ChatScreen
import com.akshay.spotconnect.location.LocationViewModel
import com.akshay.spotconnect.menu.NavGraph
import com.akshay.spotconnect.ui.theme.SpotConnectTheme
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlin.math.log

class MainActivity : ComponentActivity() {
    private lateinit var auth : FirebaseAuth
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth

        val permissions = arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
        )
        ActivityCompat.requestPermissions(
            this,
            permissions,
            0
        )


        setContent {

            SpotConnectTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavGraph {
                        auth.signOut()
                        val intent = Intent(this,AuthenticationActivity::class.java)
                        startActivity(intent)
                    }
                   }
                }
            }
        }
    companion object{
        const val TAG = "Address"
    }
    }



