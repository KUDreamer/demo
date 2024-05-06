package com.example.demo.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.navigation.NavHostController
import com.example.demo.Routes

@Composable
fun DetailedRestaurant(navController: NavHostController) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "DetailedRestaurant")
        Button(onClick = {
            navController.navigate(Routes.Date.route)
        }){
            Text(text = "음식점 선택")
            Icon(imageVector = Icons.Default.Check,
                contentDescription = "Check")
        }
    }
}