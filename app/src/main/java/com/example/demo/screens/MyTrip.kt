package com.example.demo.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.demo.Routes

@Composable
fun MyTrip(navController: NavHostController) {
    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "내 일정",
            fontSize = 25.sp,
            fontWeight = FontWeight.ExtraBold)
        Row(modifier = Modifier.clickable {
            navController.navigate(Routes.RestaurantList.route)
        }) {
            Icon(imageVector = Icons.Default.AddCircle,
                contentDescription = "Add",

            )
            Text(text = "새로운 일정")
        }
    }
}