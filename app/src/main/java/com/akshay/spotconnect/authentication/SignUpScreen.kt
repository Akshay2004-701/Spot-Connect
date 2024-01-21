package com.akshay.spotconnect.authentication

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.akshay.spotconnect.R

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SignUpScreen(
    onSignUp:(email:String, pass:String)->Unit ,
    navigate:()->Unit
) {
    val kb = LocalSoftwareKeyboardController.current

    var email by remember{ mutableStateOf("") }
    var password by remember{ mutableStateOf("") }
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize(1f)){
        Image(
            painter = painterResource(id = R.drawable.loginbg),
            contentDescription ="background",
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Spot Connect",
                color = Color.White,
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Your guide to near by places",
                color = Color.White,
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Sign Up",
                color = Color.White,
                style = MaterialTheme.typography.displaySmall
            )


            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = {
                    Row {
                        Icon(
                            imageVector = Icons.Outlined.Email,
                            contentDescription = "e-mail",
                            tint = Color.White
                        )
                        Text(text = "e-mail", color = Color.White)
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {kb?.hide()}),
                textStyle = TextStyle(
                    color = Color.White
                )
            )

            OutlinedTextField(
                value = password,
                onValueChange = {password = it},
                label = {
                    Row {
                        Icon(
                            imageVector = Icons.Outlined.PlayArrow,
                            contentDescription = "pass",
                            tint = Color.White
                        )
                        Text(text = "password", color = Color.White)
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {kb?.hide()}),
                textStyle = TextStyle(
                    color = Color.White
                )
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedButton(onClick = {
                if (email.isBlank()){
                    Toast.makeText(context,"e-mail cannot be empty", Toast.LENGTH_SHORT).show()
                }
                if (password.isBlank()){
                    Toast.makeText(context,"password cannot be empty", Toast.LENGTH_SHORT).show()
                }
                else onSignUp(email,password)
                                     },
                modifier = Modifier.fillMaxWidth(0.8f)) {
                Text(text = "Sign Up", color = Color.White, textAlign = TextAlign.Center)
            }
            Spacer(modifier = Modifier.height(8.dp))
            TextButton(onClick = navigate) {
                Text(text = "Already have an account? Click here to login", color = Color.White, textAlign = TextAlign.Center)
            }

        }
    }
}

