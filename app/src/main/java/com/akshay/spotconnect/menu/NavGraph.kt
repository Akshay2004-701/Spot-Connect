package com.akshay.spotconnect.menu

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.akshay.spotconnect.chat.ChatScreen

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun NavGraph(
    onLogout:()->Unit
) {
val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "menu"){
        composable("menu"){
            MenuScreen(onItemClick = { navController.navigate(it) }, onLogout = {onLogout()})
        }
        composable("Govt Schemes"){
            GovtSchemes()
        }
        composable("Chat Interface"){
            ChatScreen()

        }
        composable("Business Hosting"){
            Hosting()
        }
    }
}
