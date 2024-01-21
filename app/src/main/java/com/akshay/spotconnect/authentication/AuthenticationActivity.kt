package com.akshay.spotconnect.authentication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.akshay.spotconnect.MainActivity
import com.akshay.spotconnect.ui.theme.SpotConnectTheme
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class AuthenticationActivity: ComponentActivity() {
    private lateinit var auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth

        setContent {
            SpotConnectTheme {
                var isLoginPage by remember{ mutableStateOf(false) }
                if (isLoginPage){
                   LoginScreen(onLoginClick ={email, pass ->
                       signIn(email, pass)
                   } )
                }
                else{
                    SignUpScreen(
                    onSignUp = {email, pass ->
                        signUp(email, pass)
                        isLoginPage = true
                    },
                        navigate = {
                            isLoginPage = true
                        }
                    )
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val user = auth.currentUser
        if (user!=null){
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }

    // sign up function
    private fun signUp(email:String, pass:String){
        auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener{
            if (it.isSuccessful){
                Toast.makeText(
                   this,
                    "You have successfully signed up",
                    Toast.LENGTH_LONG
                ).show()
            }
            else{
                val error = it.exception?.localizedMessage
                Toast.makeText(
                    this,
                    error,
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
    //sign in function
    private fun signIn(email: String, pass: String){
        auth.signInWithEmailAndPassword(email,pass).addOnCompleteListener{
            if (it.isSuccessful){
                Toast.makeText(
                    this,
                    "You have successfully logged in",
                    Toast.LENGTH_LONG
                ).show()

                val intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
            }
            else{
                val error = it.exception?.localizedMessage
                Toast.makeText(
                    this,
                    error,
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}