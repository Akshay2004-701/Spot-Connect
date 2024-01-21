package com.akshay.spotconnect.menu

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.akshay.spotconnect.R
import com.akshay.spotconnect.location.LocationViewModel

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun MenuScreen(
    onItemClick: (String) -> Unit,
    onLogout: () -> Unit,
    viewModel:LocationViewModel = viewModel(factory = LocationViewModel.factory)
) {
    val lat by viewModel.lat.collectAsState()
    val lon by viewModel.lon.collectAsState()
    viewModel.getAddress(lat,lon, LocalContext.current)
    val address by viewModel.address.collectAsState()
    var showDialog by remember{ mutableStateOf(false) }
    Scaffold(
        topBar = {
            MenuTopBar(onLocationClick = {
                showDialog = true
            }){
                onLogout()
            }
        }
    ){padding->
        val menuList = listOf(
            MenuItem("Chat Interface", bg = R.drawable.chatbg),
            MenuItem("Govt Schemes", bg = R.drawable.govbg),
            MenuItem("Business Hosting", bg = R.drawable.businessbg)
        )
        if (showDialog){
            Dialog(onDismissRequest = { showDialog = false }) {
                Column(modifier = Modifier.background(Color.White)){
                    Text(text = "Your current address", modifier = Modifier.padding(8.dp))
                    Text(text = address, modifier = Modifier.padding(8.dp))
                    Text(text = "latitude:$lat , longitude:$lon" , modifier = Modifier.padding(8.dp))
                }
            }
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color.Black)
        ) {
            items(menuList) {
                Item(menuItem = it) { title ->
                    onItemClick(title)
                }
            }
        }
    }
}
@Composable
fun Item(menuItem: MenuItem, onItemClick:(String)->Unit) {
    val color = if (menuItem.title == "Govt Schemes") Color.Black else Color.White
Box(
    modifier = Modifier
        .fillMaxWidth()
        .heightIn(80.dp)
        .clickable {
            onItemClick(menuItem.title)
        }
        .padding(16.dp)
){
    Image(painter = painterResource(id = menuItem.bg),
          contentDescription = "bg",
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxWidth()
    )
   Column(modifier = Modifier.padding(8.dp), verticalArrangement = Arrangement.Center,
       horizontalAlignment = Alignment.CenterHorizontally) {
       Text(
           text = menuItem.title,
           style = MaterialTheme.typography.displayMedium,
           color = color,
           textAlign = TextAlign.Center
       )
   }
}
}

data class MenuItem(
    val title:String,
    val bg:Int
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuTopBar(onLocationClick:()->Unit, onLogout:()->Unit) {
    TopAppBar(title = {
        Row(modifier = Modifier.fillMaxWidth()){
            Text(
                text = "Spot Connect",
                color = Color.White,
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.width(100.dp))
            IconButton(onClick = { onLocationClick()}, colors = IconButtonDefaults.iconButtonColors(contentColor = Color.White)) {
                Icon(
                    imageVector = Icons.Default.Place,
                contentDescription = "location")
            }
            Spacer(modifier = Modifier.width(16.dp))
            IconButton(onClick = { onLogout() }, colors = IconButtonDefaults.iconButtonColors(contentColor = Color.White)) {
                Icon(
                    imageVector = Icons.Default.Logout,
                    contentDescription = "location")
            }

        }

    }, colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Black))
}

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun Prev() {
//    SpotConnectTheme {
//        MenuScreen {
//
//        }
//    }
//}

