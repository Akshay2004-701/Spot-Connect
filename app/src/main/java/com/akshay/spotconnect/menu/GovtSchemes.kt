package com.akshay.spotconnect.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.akshay.spotconnect.ui.theme.SpotConnectTheme

@Composable
fun GovtSchemes() {
    val cdata = "1. Pradhan Mantri Jan Dhan Yojana (PMJDY):\n" +
            "Launched in 2014, PMJDY is a financial inclusion initiative aimed at providing affordable access to\n" +
            "banking services for all. It focuses on opening bank accounts, providing insurance, and facilitating\n" +
            "credit for the unbanked population, contributing to financial empowerment.\n\n" +
            "2. Swachh Bharat Abhiyan:\n" +
            "Initiated in 2014, Swachh Bharat Abhiyan is a nationwide cleanliness campaign with the goal of\n" +
            "achieving an open-defecation-free India. The scheme involves constructing toilets, promoting\n" +
            "sanitation practices, and ensuring proper waste management, fostering a cleaner and healthier\n" +
            "environment.\n\n" +
            "3. Ayushman Bharat Pradhan Mantri Jan Arogya Yojana (PM-JAY):\n" +
            "Launched in 2018, PM-JAY is a health insurance scheme under Ayushman Bharat, providing\n" +
            "financial protection to vulnerable families. It covers hospitalization expenses, promoting accessibility\n" +
            "to quality healthcare services and addressing the financial burden of medical treatments.\n\n" +
            "4. Mahatma Gandhi National Rural Employment Guarantee Act (MGNREGA):\n" +
            "Enacted in 2005, MGNREGA guarantees 100 days of wage employment to rural households,\n" +
            "contributing to economic security. The scheme focuses on creating sustainable livelihoods by\n" +
            "engaging rural labour in public works, such as infrastructure development and natural resource\n" +
            "conservation.\n\n" +
            "5. Pradhan Mantri Fasal Bima Yojana (PMFBY):\n" +
            "Introduced in 2016, PMFBY is a crop insurance scheme aiming to provide financial support to\n" +
            "farmers in the event of crop failure. It helps mitigate the risks associated with agricultural practices,\n" +
            "ensuring the economic well-being of farmers and fostering agricultural sustainability."

    val sdata = "1. Anna Bhagya:\n" +
            "   Anna Bhagya is a flagship food security program in Karnataka, providing subsidized" +
            " food grains to eligible families. The scheme aims to ensure food availability and accessibility" +
            " to all, particularly those belonging to economically disadvantaged backgrounds, contributing to" +
            " the state's efforts to eradicate hunger.\n\n" +
            "2. Krishi Bhagya:\n" +
            "   Krishi Bhagya is an agricultural irrigation scheme that promotes efficient water " +
            "usage in farming. Through the distribution of drip and sprinkler irrigation systems, the " +
            "initiative assists farmers in optimizing water resources, increasing agricultural productivity," +
            " and promoting sustainable water management practices.\n\n" +
            "3. Mukhyamantri Santwana Harish Yojana:\n" +
            "   This health insurance scheme, known as Mukhyamantri Santwana Harish Yojana, provides " +
            "financial assistance to accident victims for immediate medical treatment. It covers expenses " +
            "such as hospitalization, surgeries, and rehabilitation, ensuring timely and quality healthcare for " +
            "accident victims in Karnataka.\n\n" +
            "4. Indira Canteen:\n" +
            "   Indira Canteen is a subsidized meal program that aims to provide nutritious and affordable meals" +
            " to urban residents, particularly daily-wage laborers and the economically vulnerable. By offering meals" +
            " at nominal prices, the initiative addresses food security concerns and helps ensure that no one goes" +
            " hungry in urban areas.\n\n" +
            "5. Karnataka Bhagya Laxmi Scheme:\n" +
            "   The Karnataka Bhagya Laxmi Scheme focuses on the empowerment of women by providing financial assistance" +
            " to economically backward families during childbirth. This scheme supports the overall well-being of the mother" +
            " and child by offering financial aid for medical expenses, ensuring a healthier start for the newborn and promoting" +
            " maternal health."
    var isCentral by remember{ mutableStateOf(true) }
    Scaffold(
        topBar = {
            GovtTopAppBar{
                isCentral = it
            }
        }
    ){padding->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color.Black)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            if (isCentral){
                Text(
                    text = "Central government schemes for you",
                    style = MaterialTheme.typography.headlineLarge,
                    color = Color.White,
                    modifier = Modifier.padding(16.dp)
                )
                Text(
                    text = cdata,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White,
                    modifier = Modifier.padding(16.dp)
                )
            }
            else{
                Text(
                    text = "State government schemes for you",
                    style = MaterialTheme.typography.headlineLarge,
                    color = Color.White,
                    modifier = Modifier.padding(16.dp)
                )
                Text(
                    text = sdata,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GovtTopAppBar(onChange:(Boolean)->Unit) {
    TopAppBar(title = {
        Row(modifier = Modifier.fillMaxWidth()){
            Text(
                text = "Central schemes",
                style = MaterialTheme.typography.titleMedium,
                color = Color.White,
                modifier = Modifier.clickable { onChange(true) }
            )
            Spacer(modifier = Modifier.width(100.dp))
            Text( text = "Local schemes",
                style = MaterialTheme.typography.titleMedium,
                color = Color.White,
                modifier = Modifier.clickable { onChange(false) }
            )
        }
    }, colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Black))
}

@Preview
@Composable
fun P() {
    SpotConnectTheme {
        GovtSchemes()
    }
}