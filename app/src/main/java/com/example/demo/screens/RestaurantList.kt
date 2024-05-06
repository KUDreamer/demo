package com.example.demo.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.navigation.NavHostController
import com.example.demo.Routes

@Composable
fun RestaurantList(navController: NavHostController) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "RestaurantList")
        Button(onClick = {
            navController.navigate(Routes.DetailedRestaurant.route)
        }){
            Text(text = "DetailedRestaurant")
        }
    }
}